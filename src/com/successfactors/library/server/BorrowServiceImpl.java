package com.successfactors.library.server;


import java.util.Calendar;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.server.dao.MysqlBorrowDao;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLUser;

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
//			private int borrowId;
//			private String userEmail;
//			private String bookISBN;
//			private Date borrowDate;
//			private Date shouldReturnDate;
//			private Date returnDate;
//			private boolean inStore;
//			private boolean overdue;
//			private String status;
//
//			//关联实体
//			private SLBook theBook;
//			private SLUser theUser;
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
	}

	@Override
	public BorrowPage getBorrowList(String strType, String userEmail, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorrowPage getBorrowList(String strType, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
