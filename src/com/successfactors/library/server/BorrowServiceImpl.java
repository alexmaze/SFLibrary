package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;

@SuppressWarnings("serial")
public class BorrowServiceImpl extends RemoteServiceServlet implements BorrowService {

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public boolean borrowBook(String bookISBN) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean returnBook(int borrowId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lostBook(int borrowId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SLBorrow getBorrowInfo(int borrowId) {
		// TODO Auto-generated method stub
		return null;
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
