package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.SearchType;
import com.successfactors.library.shared.model.BookPage;
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
	
	// 获取所有图书列表 例如：10,2 每页10项，取第二页
	BookPage getAllBookList(int itemsPerPage, int pageNum);
	
	// 获取新添加图书列表 添加日期降序
	BookPage getNewBookList(int num);
	
	// 获取最热图书列表 热度降序
	BookPage getHotBookList(int num);
	
	// 搜索图书
	// searchType = "bookName","bookAuthor",
	//				"bookPublisher","bookIntro",
	//				"bookContributor","bookClass","bookLanguage"
	BookPage searchBookList(SearchType searchType, String searchValue, int itemsPerPage, int pageNum);
}
