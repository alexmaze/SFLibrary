package com.successfactors.library.server;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.server.dao.MysqlBookDao;
import com.successfactors.library.server.dao.MysqlBorrowDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLUser;


@SuppressWarnings("serial")
public class BorrowServiceImpl extends RemoteServiceServlet implements BorrowService {

	private MysqlBorrowDao borrowDao= new MysqlBorrowDao();
	
	private MysqlBookDao bookDao = new MysqlBookDao();
	
	private SLUserDao userDao = new SLUserDao();
	
	private final static String USER_SESSION_KEY = "SF_LIB_USER"; 
	
	public MysqlBorrowDao getMysqlBorrowDao(){
		return this.borrowDao;
	}
	
	public MysqlBookDao getMysqlBookDao(){
		return this.bookDao;
	}
	
	public SLUserDao getMysqlUserDao(){
		return this.userDao;
	}
	
	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public boolean borrowBook(String bookISBN) {
		HttpServletRequest request = null;
		HttpSession session = null;
		SLUser slUser = null;
		request = getThreadLocalRequest();
		if(request != null){
			session = request.getSession();
		}
		if(session != null){
			slUser = (SLUser) session.getAttribute(USER_SESSION_KEY);
		}
		SLBook slBook = bookDao.queryByISBN(bookISBN);
		if(slBook.getBookAvailableQuantity() > 0){
			slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
			SLBorrow slBorrow = new SLBorrow();
			
			Calendar c = Calendar.getInstance(); 
			Date borrowDate = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 15); 
			Date shouldReturnDate = c.getTime();
			if(slUser != null){
				slBorrow.setUserEmail(slUser.getUserEmail());
			}
			slBorrow.setBookISBN(bookISBN);
			slBorrow.setBorrowDate(borrowDate);
			slBorrow.setShouldReturnDate(shouldReturnDate);
			slBorrow.setInStore(false);
			slBorrow.setOverdue(false);
			slBorrow.setStatus("未归还");
			borrowDao.save(slBorrow);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean returnBook(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		//
		slBorrow.setStatus("已归还");
		borrowDao.update(slBorrow);
		//
		SLBook slBook = slBorrow.getTheBook();
		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity()+1);
		new BookServiceImpl().updateBook(slBook);
		return true;
	}

	@Override
	public boolean lostBook(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setStatus("已丢失");
		SLBook slBook = new BookServiceImpl().getBookByISBN(slBorrow.getBookISBN());
		slBook.setBookTotalQuantity(slBook.getBookAvailableQuantity() - 1);
		return true;
	}

	@Override
	public SLBorrow getBorrowInfo(int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		SLBook slBook =bookDao.queryByISBN(slBorrow.getBookISBN());
		SLUser slUser = userDao.getSLUserByEmail(slBorrow.getUserEmail());
		slBorrow.setTheBook(slBook);
		slBorrow.setTheUser(slUser);
		return slBorrow;
	}

	@Override
	public BorrowPage getBorrowList(String strType, String userEmail, int itemsPerPage, int pageNum) {


//		StringBuffer sb = new StringBuffer();
//		sb.append("from SLBorrow as model");
//		String status = "";
//		if(!strType.equals("all")){
//			
//		}else{
//			if(strType.equals("history")){
//				status = "已归还";
//			}else if(strType.equals("now")){
//				status = "未归还";
//			}
//			else if(strType.equals("overDue")){
//				status = "已超期";
//			}
//			sb.append("where model.status=");
//			sb.append(status);
//		}
//	
//		if(userEmail != null){
//			sb.append("and model.userEmail=");
//			sb.append(userEmail);
//		}
//		
//		ArrayList<SLBorrow> ret = borrowDao.executeQuery(sb.toString(), itemsPerPage, pageNum);
//		
//		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
//		
//		for (int i = 0;i < 10;i++) {
//			SLBorrow slBorrow = ret.get(i);
//			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
//			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow.getUserEmail()));
//			ret.set(i, slBorrow);
//		}
//		page.setTheBorrows(ret);
//		page.setTotalPageNum(pageNum);
//		return page;
		List<SLBorrow> result = borrowDao.searchBorrowList(strType,userEmail,itemsPerPage, pageNum);
		
		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
		
		for (SLBorrow slBorrow : result) {
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow.getUserEmail()));
		}
		page.setTheBorrows((ArrayList<SLBorrow>)result);
		page.setTotalPageNum(pageNum);
		return page;
	}

	@Override
	public BorrowPage getBorrowList(String strType, int itemsPerPage, int pageNum) {


//		StringBuffer sb = new StringBuffer();
//		sb.append("from SLBorrow as model");
//		String status = "";
//		if(!strType.equals("all")){
//			
//		}else{
//			if(strType.equals("history")){
//				status = "已归还";
//			}else if(strType.equals("now")){
//				status = "未归还";
//			}
//			else if(strType.equals("overDue")){
//				status = "已超期";
//			}
//			sb.append("where model.status=");
//			sb.append(status);
//		}
	
		
		List<SLBorrow> result = borrowDao.searchBorrowList(strType, itemsPerPage, pageNum);
		
		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
		
		for (SLBorrow slBorrow : result) {
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow.getUserEmail()));
		}
		page.setTheBorrows((ArrayList<SLBorrow>)result);
		page.setTotalPageNum(pageNum);
		return page;
	}

	@Override
	public BorrowPage searchBorrowList(String borrowType, String searchType,
			String searchValue, int itemsPerPage, int pageNum) {
		List<SLBorrow> result = borrowDao.searchBorrowList(borrowType,searchType,
				searchValue,itemsPerPage,pageNum);

		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
		
		for (SLBorrow slBorrow : result) {
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow.getUserEmail()));
		}
		page.setTheBorrows((ArrayList<SLBorrow>)result);
		page.setTotalPageNum(pageNum);
		return page;
	}

}
