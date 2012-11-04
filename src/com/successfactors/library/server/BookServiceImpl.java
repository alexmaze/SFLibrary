package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BookService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

@SuppressWarnings("serial")
public class BookServiceImpl extends RemoteServiceServlet implements BookService {

    private SLBookDao dao=new SLBookDao();
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
	  if(dao.insertBook(newBook)){
	    return newBook;
	  }else{
	    return null;
	  }
	}

	@Override
	public boolean deleteBook(String bookISBN) {
		// TODO Auto-generated method stub
	  SLBook book=new SLBook();
	  book.setBookISBN(bookISBN);
	  return dao.deleteBook(book);
	}

	@Override
	public boolean updateBook(SLBook updateBook) {
		// TODO Auto-generated method stub
		return dao.updateBook(updateBook);
	}

	@Override
	public SLBook getBookByISBN(String bookISBN) {
		return dao.queryByISBN(bookISBN);
	}

	@Override
	public BookPage searchBookList(BookSearchType searchType,
			String searchValue, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		ArrayList<SLBook> listBooks=(ArrayList<SLBook>)dao.queryByCustomField(searchType,searchValue,itemsPerPage,pageNum);
		BookPage bookPage=new BookPage(itemsPerPage,pageNum);
		bookPage.setTheBooks(listBooks);
		long totalNum=dao.getCountByCustomField(searchType, searchValue);
		if(totalNum%itemsPerPage==0){
		  bookPage.setTotalPageNum((int)totalNum/itemsPerPage);
		}else{
		  bookPage.setTotalPageNum((int)totalNum/itemsPerPage+1);
		}
		return bookPage;
	}

	@Override
	public BookPage getAllBookList(int itemsPerPage, int pageNum) {
	  ArrayList<SLBook> listBooks=(ArrayList<SLBook>)dao.queryAll(itemsPerPage, pageNum);
      BookPage bookPage=new BookPage(itemsPerPage,pageNum);
      bookPage.setTheBooks(listBooks);
      long totalNum=dao.getCountAll();
      if(totalNum%itemsPerPage==0){
        bookPage.setTotalPageNum((int)totalNum/itemsPerPage);
      }else{
        bookPage.setTotalPageNum((int)totalNum/itemsPerPage+1);
      }
      return bookPage;
	}

	@Override
	public BookPage getNewBookList(int num) {

		BookPage page = new BookPage(1, num);
		ArrayList<SLBook> ret = (ArrayList<SLBook>)dao.getLatestBooks(num);
		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		return page;
	}

	@Override
	public BookPage getHotBookList(int num) {
		// TODO Auto-generated method stub

		BookPage page = new BookPage(1, num);
		ArrayList<SLBook> ret = new ArrayList<SLBook>();
		
		ArrayList<String> listISBN=(ArrayList<String>)dao.getHotBooks(num);
		Iterator<String> it=listISBN.iterator();
		while(it.hasNext()){
			ret.add(dao.queryByISBN((String)it.next()));
		}

		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		return page;
	}

}
