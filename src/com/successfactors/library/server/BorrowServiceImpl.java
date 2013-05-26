package com.successfactors.library.server;

import static com.successfactors.library.server.UserServiceImpl.USER_SESSION_KEY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLBorrowDao;
import com.successfactors.library.server.dao.SLOrderDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

public class BorrowServiceImpl extends RemoteServiceServlet implements
		BorrowService {
	private static final long serialVersionUID = 1L;
	
	protected SLBorrowDao borrowDao = SLBorrowDao.getDao();
	protected SLOrderDao orderDao = SLOrderDao.getDao();
	protected SLBookDao bookDao = SLBookDao.getDao();
	protected SLUserDao userDao = SLUserDao.getDao();
	
	private SLEmailUtil emailUtil = new SLEmailUtil();

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	/**
	 * 借阅图书
	 * */
	@Override
	public boolean borrowBook(String bookISBN) {
		SLUser slUser = getCurrentUser();
		if (slUser == null) {
			return false;
		}
		return borrowBook(slUser.getUserEmail(), bookISBN);
	}
	
	/**
	 * （内部）借阅
	 * */
	private boolean borrowBook(String userEmail, String bookISBN) {

		// 先检查是否已借！
		if (borrowDao.isUserBookBorrowed(userEmail, bookISBN)) {
			return false;
		}
		
		SLBook slBook = bookDao.queryByISBN(bookISBN);
		if (slBook.getBookAvailableQuantity() > 0) {
			slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
			slBook.setBorrowOrderTimes(slBook.getBorrowOrderTimes()==null?0:slBook.getBorrowOrderTimes() + 1);
			SLBorrow slBorrow = initBorrow(userEmail,
					slBook.getBookISBN());
			borrowDao.save(slBorrow);
			bookDao.updateBook(slBook);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			emailUtil.sendBorrowSuccessEmail(slBorrow);
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 还书
	 * */
	@Override
	public boolean returnBook(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setStatus("已归还");
		slBorrow.setReturnDate(new Date());
		borrowDao.update(slBorrow);
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() + 1);
		if (!slBorrow.isInStore()) {
			slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() + 1);
		}
		bookDao.updateBook(slBook);
		SLOrder slOrder = orderDao.getEarlistOrder(slBook.getBookISBN());
		if (slOrder != null) {
			// 自动为最早预订的用户借阅
			if (borrowBook(slOrder.getUserEmail(), slBook.getBookISBN())) {
				slOrder.setStatus("已借到");
				orderDao.updateOrder(slOrder);
			}
		}
		
		return true;
	}

	/**
	 * 借阅图书丢失登记
	 * */
	@Override
	public boolean lostBook(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setStatus("已丢失");
		borrowDao.save(slBorrow);
		SLBook slBook = bookDao.queryByISBN(slBorrow
				.getBookISBN());
		slBook.setBookTotalQuantity(slBook.getBookTotalQuantity() - 1);
		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
		slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() - 1);
		bookDao.updateBook(slBook);
		
		return true;
	}

	/**
	 * 获取借阅信息
	 * */
	@Override
	public SLBorrow getBorrowInfo(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		SLUser slUser = userDao.getSLUserByEmail(slBorrow.getUserEmail());
		slBorrow.setTheBook(slBook);
		slBorrow.setTheUser(slUser);
		return slBorrow;
	}

	/**
	 * 获取某种状态下的，某用户的所有借阅信息
	 * 
	 * @param statusType
	 *            借阅状态
	 * @param userEmail
	 *            用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	@Override
	public BorrowPage getBorrowList(BorrowStatusType statusType,
			String userEmail, int itemsPerPage, int pageNum) {
		BorrowPage page = null;
		
		List<SLBorrow> result = null;
		if (userEmail == null) {
			page = borrowDao.searchBorrowList(statusType, itemsPerPage, pageNum);
		} else {
			page = borrowDao.searchBorrowList(statusType, userEmail,
					itemsPerPage, pageNum);
		}
		result = page.getTheBorrows();
		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		
		page.setTheBorrows((ArrayList<SLBorrow>) result);
		page.setItemsNumPerPage(itemsPerPage);
		page.setPageNum(pageNum);
		
		return page;
	}

	/**
	 * 在某种状态下的借阅信息中，所有用户中，搜索
	 * 
	 * @param statusType
	 *            借阅状态
	 * @param searchType
	 *            搜索类型
	 * @param searchValue
	 *            关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	@Override
	public BorrowPage searchBorrowList(BorrowStatusType statusType,
			BorrowSearchType searchType, String searchValue, int itemsPerPage,
			int pageNum) {
		BorrowPage page = borrowDao.searchBorrowList(statusType,
				searchType, searchValue, itemsPerPage, pageNum);
		List<SLBorrow> result = page.getTheBorrows();
		
		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		page.setTheBorrows((ArrayList<SLBorrow>) result);
		page.setItemsNumPerPage(itemsPerPage);
		page.setPageNum(pageNum);
		
		return page;
	}

	/**
	 * 获取所有超期借阅记录
	 * */
	@Override
	public ArrayList<SLBorrow> getOverdueBorrowList() {
		BorrowStatusType statusType = BorrowStatusType.parse("已超期");
		List<SLBorrow> result = borrowDao.searchBorrowList(statusType);

		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		return (ArrayList<SLBorrow>) result;
	}

	/**
	 * 图书出库
	 * */
	@Override
	public boolean outStoreBook(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setInStore(false);
		borrowDao.update(slBorrow);
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() - 1);
		bookDao.updateBook(slBook);
		
		return true;
	}

	/**
	 * 初始化借阅信息
	 * */
	private SLBorrow initBorrow(String userEmail, String ISBN) {
		SLBorrow newBorrow = new SLBorrow();
		Calendar c = Calendar.getInstance();
		Date borrowDate = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 15);
		Date shouldReturnDate = c.getTime();
		newBorrow.setUserEmail(userEmail);
		newBorrow.setBookISBN(ISBN);
		newBorrow.setBorrowDate(borrowDate);
		newBorrow.setShouldReturnDate(shouldReturnDate);
		newBorrow.setInStore(true);
		newBorrow.setOverdue(false);
		newBorrow.setStatus("未归还");
		return newBorrow;
	}

	/**
	 * 获取当前登录用户信息
	 * */
	protected SLUser getCurrentUser() {
		HttpServletRequest request = null;
		HttpSession session = null;
		SLUser slUser = null;
		request = getThreadLocalRequest();
		if (request != null) {
			session = request.getSession();
		}
		if (session != null) {
			slUser = (SLUser) session.getAttribute(USER_SESSION_KEY);
		}
		return slUser;
	}
	
}
