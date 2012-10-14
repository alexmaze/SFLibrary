package com.successfactors.library.server;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.server.dao.MysqlBorrowDao;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLUser;
import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

@SuppressWarnings("serial")
public class BorrowServiceImpl extends RemoteServiceServlet implements BorrowService {

	private MysqlBorrowDao mysqlBorrowDao= new MysqlBorrowDao();
	
	public MysqlBorrowDao getMysqlBorrowDao(){
		return this.mysqlBorrowDao;
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
		SLBook slBook = new BookServiceImpl().getBookByISBN(bookISBN);
		if(slBook.getBookAvailableQuantity() > 0){
			slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
			SLBorrow slBorrow = new SLBorrow();
			
			Calendar c = Calendar.getInstance(); 
			Date borrowDate = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 15); 
			Date shouldReturnDate = c.getTime();
			//slBorrow.setUserEmail(userEmail);
			slBorrow.setBookISBN(bookISBN);
			slBorrow.setBorrowDate(borrowDate);
			slBorrow.setShouldReturnDate(shouldReturnDate);
			slBorrow.setInStore(false);
			slBorrow.setOverdue(false);
			slBorrow.setStatus("未归还");
			mysqlBorrowDao.save(slBorrow);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean returnBook(int borrowId) {
		SLBorrow slBorrow = mysqlBorrowDao.getBorrowById(borrowId);
		//
		slBorrow.setStatus("已归还");
		mysqlBorrowDao.update(slBorrow);
		//
		SLBook slBook = slBorrow.getTheBook();
		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity()+1);
		new BookServiceImpl().updateBook(slBook);
		return true;
	}

	@Override
	public boolean lostBook(int borrowId) {
		SLBorrow slBorrow = mysqlBorrowDao.getBorrowById(borrowId);
		slBorrow.setStatus("已丢失");
		SLBook slBook = new BookServiceImpl().getBookByISBN(slBorrow.getBookISBN());
		slBook.setBookTotalQuantity(slBook.getBookAvailableQuantity() - 1);
		return true;
	}

	@Override
	public SLBorrow getBorrowInfo(int borrowId) {
		SLBorrow slBorrow = mysqlBorrowDao.getBorrowById(borrowId);
		SLBook slBook = new BookServiceImpl().getBookByISBN(slBorrow.getBookISBN());
		SLUser slUser = new UserServiceImpl().getUserById(slBorrow.getUserEmail());
		slBorrow.setTheBook(slBook);
		slBorrow.setTheUser(slUser);
		return slBorrow;
//		SLBook temp = new SLBook();
//		temp.setBookName("冰与火之歌（卷一）");
//		temp.setBookAuthor("[美] 乔治·R. R. 马丁");
//		temp.setBookISBN("9787536671256");
//		temp.setBookClass("小说/文学");
//		temp.setBookAvailableQuantity(1);
//		temp.setBookContributor("SF");
//		temp.setBookInStoreQuantity(1);
//		temp.setBookIntro("《冰与火之歌》由美国著名科幻奇幻小说家乔治·R·R·马丁所著，是当代奇幻文学一部影响深远的里程碑式的作品。它于1996年刚一问世，便以别具一格的结构，浩瀚辽阔的视野，错落有致的情节和生动活泼的语言，迅速征服了欧美文坛。迄今，本书已被译为数十种文字，并在各个国家迭获大奖。");
//		temp.setBookLanguage("中文");
//		temp.setBookPicUrl("temppic.jpg");
//		temp.setBookPrice(68.00);
//		temp.setBookPublishDate(new Date());
//		temp.setBookPublisher("重庆出版社");
//		temp.setBookTotalQuantity(1);
//		
//		SLUser user = new SLUser();
//		user.setUserName("Alex Yan");
//		user.setUserEmail("ayan@successfactors.com");
//		
//		SLBorrow borrow = new SLBorrow();
//		borrow.setBookISBN("9787536671256");
//		borrow.setBorrowId(100);
//		borrow.setBorrowDate(new Date());
//		borrow.setUserEmail("ayan@successfactors.com");
//		borrow.setShouldReturnDate(new Date());
//		borrow.setReturnDate(null);
//		borrow.setInStore(true);
//		borrow.setOverdue(false);
//		borrow.setStatus("已借出");
//		borrow.setTheBook(temp);
//		borrow.setTheUser(user);
//		return borrow;
	}

	@Override
	public BorrowPage getBorrowList(String strType, String userEmail, int itemsPerPage, int pageNum) {


		StringBuffer sb = new StringBuffer();
		sb.append("from SLBorrow as model");
		String status = "";
		if(!strType.equals("all")){
			
		}else{
			if(strType.equals("history")){
				status = "已归还";
			}else if(strType.equals("now")){
				status = "未归还";
			}
			else if(strType.equals("overDue")){
				status = "已超期";
			}
			sb.append("where model.status=");
			sb.append(status);
		}
	
		if(userEmail != null){
			sb.append("and model.userEmail=");
			sb.append(userEmail);
		}
		
		ArrayList<SLBorrow> ret = mysqlBorrowDao.executeQuery(sb.toString(), itemsPerPage, pageNum);
		
		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
		
		for (int i = 0;i < 10;i++) {
			SLBorrow slBorrow = ret.get(i);
			slBorrow.setTheBook(new BookServiceImpl()
				.getBookByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(new UserServiceImpl().
					getUserById(slBorrow.getUserEmail()));
			ret.set(i, slBorrow);
		}
		page.setTheBorrows(ret);
		page.setTotalPageNum(pageNum);
		return page;
	}

	@Override
	public BorrowPage getBorrowList(String strType, int itemsPerPage, int pageNum) {


		StringBuffer sb = new StringBuffer();
		sb.append("from SLBorrow as model");
		String status = "";
		if(!strType.equals("all")){
			
		}else{
			if(strType.equals("history")){
				status = "已归还";
			}else if(strType.equals("now")){
				status = "未归还";
			}
			else if(strType.equals("overDue")){
				status = "已超期";
			}
			sb.append("where model.status=");
			sb.append(status);
		}
	
		
		ArrayList<SLBorrow> ret = mysqlBorrowDao.executeQuery(sb.toString(), itemsPerPage, pageNum);
		
		BorrowPage page = new BorrowPage(itemsPerPage, pageNum);
		
		for (int i = 0;i < 10;i++) {
			SLBorrow slBorrow = ret.get(i);
			slBorrow.setTheBook(new BookServiceImpl()
				.getBookByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(new UserServiceImpl().
					getUserById(slBorrow.getUserEmail()));
			ret.set(i, slBorrow);
		}
		page.setTheBorrows(ret);
		page.setTotalPageNum(pageNum);
		return page;
	}

	@Override
	public BorrowPage searchBorrowList(String borrowType, String searchType,
			String searchValue, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return getBorrowList(null, itemsPerPage, pageNum);
	}

}
