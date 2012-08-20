package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.SLBook;

@RemoteServiceRelativePath("bookService")
public interface BookService extends RemoteService {

	// 测试服务联通性
	String helloServer(String strHello);
	
	// 添加图书
	SLBook addBook(SLBook newBook);
	
	// 删除图书
	boolean deleteBook(String bookISBN);
	
	// 更新图书
	boolean updateBook(SLBook updateBook);
		
	// 通过ISBN获取图书
	SLBook getBookByISBN(String bookISBN);
	
	// 获取所有图书列表
	ArrayList<SLBook> getAllBookList(int iStart, int iEnd);
	
	// 搜索图书
	// searchType = "bookName","bookAuthor",
	//				"bookPublisher","bookIntro",
	//				"bookContributor","bookClass","bookLanguage"
	ArrayList<SLBook> searchBookList(String searchType, String searchValue, int iStart, int iEnd);
}
