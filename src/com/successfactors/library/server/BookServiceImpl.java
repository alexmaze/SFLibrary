package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Date;

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
		SLBook temp = new SLBook();
		temp.setBookName("冰与火之歌");
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
		return temp;
	}

	@Override
	public ArrayList<SLBook> searchBookList(String searchType,
			String searchValue, int iStart, int iEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SLBook> getAllBookList(int iStart, int iEnd) {
		// TODO Auto-generated method stub
		
		ArrayList<SLBook> ret = new ArrayList<SLBook>();
		
		for (int i = 0;i < 10;i++) {
			
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
		
		return ret;
	}

	@Override
	public ArrayList<SLBook> getNewBookList(int num) {
		// TODO Auto-generated method stub
		
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
		
		return ret;
	}

	@Override
	public ArrayList<SLBook> getHotBookList(int num) {
		// TODO Auto-generated method stub
		
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
		
		return ret;
	}

}
