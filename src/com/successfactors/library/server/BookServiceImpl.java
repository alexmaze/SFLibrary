package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BookService;
import com.successfactors.library.shared.model.SLBook;

@SuppressWarnings("serial")
public class BookServiceImpl extends RemoteServiceServlet implements BookService {

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public SLBook addBook(SLBook newBook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteBook(String bookISBN) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBook(SLBook updateBook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SLBook getBookByISBN(String bookISBN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SLBook> getAllBookList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SLBook> searchBookList(String searchType,
			String searchValue, int iStart, int iEnd) {
		// TODO Auto-generated method stub
		return null;
	}

}
