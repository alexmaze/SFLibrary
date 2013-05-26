package com.successfactors.library.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BookService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLBorrowDao;
import com.successfactors.library.server.dao.SLOrderDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.BookBorrowOrderListInfo;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;

@SuppressWarnings("serial")
public class BookServiceImpl extends RemoteServiceServlet implements
		BookService {

	private SLBookDao dao = SLBookDao.getDao();
	private SLBorrowDao borrowDao = SLBorrowDao.getDao();
	private SLOrderDao orderDao = SLOrderDao.getDao();
	private SLUserDao userDao = SLUserDao.getDao();
	private static final String DOUBAN_API_URL = "https://api.douban.com/v2/book/isbn/";
	private static final String DOUBAN_API_KEY = "";
	//private static final String DOUBAN_API_KEY = "?apikey={0b71b06d4ed8d8a722551147ec8a89f5}";

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public SLBook addBook(SLBook newBook) {
		if (dao.insertBook(newBook)) {
			return newBook;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteBook(String bookISBN) {
		SLBook book = new SLBook();
		book.setBookISBN(bookISBN);
		return dao.deleteBook(book);
	}

	@Override
	public boolean updateBook(SLBook updateBook) {
		return dao.updateBook(updateBook);
	}

	@Override
	public SLBook getBookByISBN(String bookISBN) {
		return dao.queryByISBN(bookISBN);
	}

	@Override
	public BookPage searchBookList(BookSearchType searchType,
			String searchValue, int itemsPerPage, int pageNum) {
		ArrayList<SLBook> listBooks = (ArrayList<SLBook>) dao
				.queryByCustomField(searchType, searchValue, itemsPerPage,
						pageNum);
		BookPage bookPage = new BookPage(itemsPerPage, pageNum);
		bookPage.setTheBooks(listBooks);
		long totalNum = dao.getCountByCustomField(searchType, searchValue);
		if (totalNum % itemsPerPage == 0) {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		return bookPage;
	}

	@Override
	public BookPage getAllBookList(int itemsPerPage, int pageNum) {
		ArrayList<SLBook> listBooks = (ArrayList<SLBook>) dao.queryAll(
				itemsPerPage, pageNum);
		BookPage bookPage = new BookPage(itemsPerPage, pageNum);
		bookPage.setTheBooks(listBooks);
		long totalNum = dao.getCountAll();
		if (totalNum % itemsPerPage == 0) {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		return bookPage;
	}

	@Override
	public BookPage getNewBookList(int num) {

		BookPage page = new BookPage(num, 1);
		ArrayList<SLBook> ret = (ArrayList<SLBook>) dao.getLatestBooks(num);
		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		return page;
	}

	@Override
	public BookPage getHotBookList(int num) {

		BookPage page = new BookPage(num, 1);

		ArrayList<SLBook> listISBN = dao.getHotBooks(num);

		page.setTheBooks(listISBN);
		page.setTotalPageNum(1);
		return page;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SLBook getBookByDoubanAPI(String bookISBN) {
		String strUrl = DOUBAN_API_URL + bookISBN + DOUBAN_API_KEY;
		BufferedReader in = null;
		
		SLBook retBook = null;
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setDoInput(true);
			in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), "UTF-8"));
			String line;
			String jsonResult = "";
			while ((line = in.readLine()) != null) {
				jsonResult += line;
			}
			in.close();
			if (jsonResult.length() == 0) {
				return null;
			}

			// 解析Json信息
			JSONReader reader = new JSONValidatingReader();
			HashMap result = null;
			result = (HashMap) reader.read(jsonResult);

			if (result == null || result.isEmpty()) {
				return null;
			}
			
			retBook = new SLBook();
			retBook.setBookName(result.containsKey("title")?result.get("title").toString():null);
			retBook.setBookAuthor(result.containsKey("author")?getStringWithoutBrcket(result.get("author").toString()):null);
			retBook.setBookISBN(bookISBN);
			retBook.setBookPublisher(result.containsKey("publisher")?result.get("publisher").toString():null);
			retBook.setBookPublishDate(result.containsKey("pubdate")?getDateFormString(result.get("pubdate").toString()):null);
			retBook.setBookClass("");
			retBook.setBookLanguage("");
			retBook.setBookContributor("公司采购");
			retBook.setBookPrice(result.containsKey("price")?getDouble(result.get("price").toString()):null);
			retBook.setBookIntro(result.containsKey("summary")?result.get("summary").toString():null);

			retBook.setBookTotalQuantity(1);
			retBook.setBookInStoreQuantity(1);
			retBook.setBookAvailableQuantity(1);

			if (result.containsKey("images")) {
				HashMap imagesMap = (HashMap) result.get("images");
				retBook.setBookPicUrl(imagesMap.containsKey("large")?imagesMap.get("large").toString():
					(imagesMap.containsKey("medium")?imagesMap.get("medium").toString():
						(imagesMap.containsKey("medium")?imagesMap.get("medium").toString():"./images/upload/nopic.jpg")));
			} else {
				retBook.setBookPicUrl("./images/upload/nopic.jpg");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retBook;
	}
	
	/**
	 * 功能：将字符串 "yy-MM-dd" 转换为日期
	 * @param strDate "yy-MM-dd"
	 * @return Date 日期 
	 * */
	@SuppressWarnings("deprecation")
	public static Date getDateFormString(String strDate)
	{
		Date ret = null;
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			}
			
			ret = new Date();
			
			String[] arrDate = strDate.split("-");
			if(arrDate.length == 1) {
				ret.setYear(Integer.parseInt(arrDate[0])-1900);
				ret.setMonth(1);
				ret.setDate(1);
			} else if(arrDate.length == 2) {
				ret.setYear(Integer.parseInt(arrDate[0])-1900);
				ret.setMonth(Integer.parseInt(arrDate[1])-1);
				ret.setDate(1);
			} else if(arrDate.length >= 3){
				ret.setYear(Integer.parseInt(arrDate[0])-1900);
				ret.setMonth(Integer.parseInt(arrDate[1])-1);
				ret.setDate(Integer.parseInt(arrDate[2]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret = null;
		}
		
		
		return ret;
	}
	
	/**
	 * 过滤字符串后转换为Double
	 */
	public static Double getDouble(String str) {
		if (str == null || str.equals("")) {
			return 0.0;
		}
		// 只允数字
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		String cleanString = m.replaceAll("").trim();
		if (cleanString.equals("")) {
			return 0.0;
		}
		return Double.parseDouble(cleanString);
	}

	/**
	 * 过滤字符串中的[]
	 */
	public static String getStringWithoutBrcket(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String regEx="[\\[\\]]";  
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
	}

	/**
	 * 获取某本书籍的当前借阅队列和预订队列
	 * 两个队列都按时间顺序升序排列
	 */
	@Override
	public BookBorrowOrderListInfo getBookNowBorrowOrderListByISBN(String bookISBN) {
		BookBorrowOrderListInfo ret = new BookBorrowOrderListInfo();

		ret.setTheBorrows(borrowDao.getNowBorrowListByISBN(bookISBN));
		ret.setTheOrders(orderDao.getNowOrderListByISBN(bookISBN));
		
		for (SLBorrow borrow : ret.getTheBorrows()) {
			borrow.setTheUser(userDao.getSLUserByEmail(borrow.getUserEmail()));
		}
		for (SLOrder order : ret.getTheOrders()) {
			order.setTheUser(userDao.getSLUserByEmail(order.getUserEmail()));
		}
		
		return ret;
	}
	
}
