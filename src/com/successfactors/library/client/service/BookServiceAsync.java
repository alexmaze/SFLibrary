package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.SLBook;

public interface BookServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void addBook(SLBook newBook, AsyncCallback<SLBook> callback);

	void deleteBook(String bookISBN, AsyncCallback<Boolean> callback);

	void getAllBookList(int iStart, int iEnd,
			AsyncCallback<ArrayList<SLBook>> callback);

	void getBookByISBN(String bookISBN, AsyncCallback<SLBook> callback);

	void searchBookList(String searchType, String searchValue, int iStart,
			int iEnd, AsyncCallback<ArrayList<SLBook>> callback);

	void updateBook(SLBook updateBook, AsyncCallback<Boolean> callback);

	void getNewBookList(int num, AsyncCallback<ArrayList<SLBook>> callback);

	void getHotBookList(int num, AsyncCallback<ArrayList<SLBook>> callback);

}
