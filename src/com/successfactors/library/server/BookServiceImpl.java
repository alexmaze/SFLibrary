package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BookService;
import com.successfactors.library.server.dao.MysqlBookDao;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

@SuppressWarnings("serial")
public class BookServiceImpl extends RemoteServiceServlet implements BookService {

    private MysqlBookDao dao=new MysqlBookDao();
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub

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
		
		for (int i = 0;i < num;i++) {
			
			SLBook temp = new SLBook();
			temp.setBookName("冰与火之歌 "+i);
			temp.setBookAuthor("[美] 乔治·R. R. 马丁");
			temp.setBookISBN("9787536671256");
			temp.setBookClass("小说");
			temp.setBookAvailableQuantity(1);
			temp.setBookContributor("SF");
			temp.setBookInStoreQuantity(1);
			temp.setBookIntro("《冰与火之歌》由美国著名科幻奇幻小说家乔治·R·R·马丁所著，是当代奇幻文学一部影响深远的里程碑式的作品。它于1996年刚一问世，便以别具一格的结构，浩瀚辽阔的视野，错落有致的情节和生动活泼的语言，迅速征服了欧美文坛。迄今，本书已被译为数十种文字，并在各个国家迭获大奖。");
			temp.setBookLanguage("中文");
			temp.setBookPicUrl("temppic.jpg");
			temp.setBookPrice(68.00);
			temp.setBookPublishDate(new Date());
			temp.setBookPublisher("重庆出版社");
			temp.setBookTotalQuantity(1);
			
			ret.add(temp);
		}

		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		return page;
	}

}
