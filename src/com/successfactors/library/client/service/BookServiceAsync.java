package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

public interface BookServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void addBook(SLBook newBook, AsyncCallback<SLBook> callback);

	void deleteBook(String bookISBN, AsyncCallback<Boolean> callback);

	void getAllBookList(int itemsPerPage, int pageNum,
			AsyncCallback<BookPage> callback);

	void getBookByISBN(String bookISBN, AsyncCallback<SLBook> callback);

	void searchBookList(BookSearchType searchType, String searchValue, int itemsPerPage, int pageNum, AsyncCallback<BookPage> callback);

	void updateBook(SLBook updateBook, AsyncCallback<Boolean> callback);

	void getNewBookList(int num, AsyncCallback<BookPage> callback);

	void getHotBookList(int num, AsyncCallback<BookPage> callback);

}
