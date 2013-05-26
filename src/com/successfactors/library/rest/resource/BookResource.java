package com.successfactors.library.rest.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;

import com.successfactors.library.rest.utils.MyTools;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLBorrowDao;
import com.successfactors.library.server.dao.SLOrderDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.RestCallInfo;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;
import com.successfactors.library.shared.RestCallInfo.RestCallStatus;
import com.successfactors.library.shared.model.BookBorrowOrderListInfo;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;

@SuppressWarnings({"rawtypes", "unchecked"})
@Path("book")
public class BookResource {
	
	private SLBookDao dao = SLBookDao.getDao();
	private SLBorrowDao borrowDao = SLBorrowDao.getDao();
	private SLOrderDao orderDao = SLOrderDao.getDao();
	private SLUserDao userDao = SLUserDao.getDao();
	private static final String DOUBAN_API_URL = "https://api.douban.com/v2/book/isbn/";
	private static final String DOUBAN_API_KEY = "?apikey={0b71b06d4ed8d8a722551147ec8a89f5}";
	
	/**
	 * 获取某本书籍的详细信息，by ISBN
	 */
	@GET
	@Path("getbookbyisbn/{bookISBN}")
	@Produces("application/json")
	public Representation getBookByISBN(@PathParam("bookISBN") String bookISBN) {
		
		SLBook book = dao.queryByISBN(bookISBN);
		if (book == null) {
			HashMap returnInfo = new HashMap();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_such_book);
			return new JsonRepresentation(returnInfo);
		}
		
		JsonRepresentation ret = new JsonRepresentation(book);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 获取所有图书列表
	 */
	@GET
	@Path("getallbooklistpage/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation getAllBookList(@PathParam("itemsPerPage") int itemsPerPage, @PathParam("pageNum") int pageNum) {
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
		JsonRepresentation ret = new JsonRepresentation(bookPage);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 获取最新图书
	 */
	@GET
	@Path("getnewbooklistpage/{num}")
	@Produces("application/json")
	public Representation getNewBookList(@PathParam("num") int num) {

		BookPage page = new BookPage(num, 1);
		ArrayList<SLBook> ret = (ArrayList<SLBook>) dao.getLatestBooks(num);
		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		
		JsonRepresentation retRep = new JsonRepresentation(page);
		try {
			retRep.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			retRep.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retRep;
	}
	
	/**
	 * 获取最热门图书
	 */
	@GET
	@Path("gethotbooklistpage/{num}")
	@Produces("application/json")
	public Representation getHotBookList(@PathParam("num") int num) {

		BookPage page = new BookPage(num, 1);

		ArrayList<SLBook> listISBN =  dao.getHotBooks(num);

		page.setTheBooks(listISBN);
		page.setTotalPageNum(1);
		
		JsonRepresentation retRep = new JsonRepresentation(page);
		try {
			retRep.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			retRep.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retRep;
	}
	
	/**
	 * 搜索图书
	 */
	@GET
	@Path("searchbooklistpage/{searchType}/{searchValue}/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation searchBookList(@PathParam("searchType") String searchType,
			@PathParam("searchValue") String searchValue, @PathParam("itemsPerPage") int itemsPerPage, @PathParam("pageNum") int pageNum) {
		ArrayList<SLBook> listBooks = (ArrayList<SLBook>) dao
				.queryByCustomField(BookSearchType.parse(searchType), searchValue, itemsPerPage,
						pageNum);
		BookPage bookPage = new BookPage(itemsPerPage, pageNum);
		bookPage.setTheBooks(listBooks);
		long totalNum = dao.getCountByCustomField(BookSearchType.parse(searchType), searchValue);
		if (totalNum % itemsPerPage == 0) {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		JsonRepresentation ret = new JsonRepresentation(bookPage);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 获取某本书籍的豆瓣详细信息，by ISBN
	 */
	@GET
	@Path("getdoubanbookbyisbn/{bookISBN}")
	@Produces("application/json")
	public Representation getBookByDoubanAPI(@PathParam("bookISBN") String bookISBN) {
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
			retBook.setBookAuthor(result.containsKey("author")?MyTools.getStringWithoutBrcket(result.get("author").toString()):null);
			retBook.setBookISBN(bookISBN);
			retBook.setBookPublisher(result.containsKey("publisher")?result.get("publisher").toString():null);
			retBook.setBookPublishDate(result.containsKey("pubdate")?MyTools.getDateFormString(result.get("pubdate").toString()):null);
			retBook.setBookClass("");
			retBook.setBookLanguage("");
			retBook.setBookContributor("公司采购");
			retBook.setBookPrice(result.containsKey("price")?MyTools.getDouble(result.get("price").toString()):null);
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
		
		JsonRepresentation ret = new JsonRepresentation(retBook);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 获取某本书籍的当前借阅队列和预订队列
	 * 两个队列都按时间顺序升序排列
	 */
	@GET
	@Path("getbookborroworderlistbyisbn/{bookISBN}")
	@Produces("application/json")
	public Representation getBookNowBorrowOrderListByISBN(@PathParam("bookISBN") String bookISBN) {
		BookBorrowOrderListInfo ret = new BookBorrowOrderListInfo();

		ret.setTheBorrows(borrowDao.getNowBorrowListByISBN(bookISBN));
		ret.setTheOrders(orderDao.getNowOrderListByISBN(bookISBN));
		
		for (SLBorrow borrow : ret.getTheBorrows()) {
			borrow.setTheUser(userDao.getSLUserByEmail(borrow.getUserEmail()));
		}
		for (SLOrder order : ret.getTheOrders()) {
			order.setTheUser(userDao.getSLUserByEmail(order.getUserEmail()));
		}

		JsonRepresentation retRep = new JsonRepresentation(ret);
		try {
			retRep.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			retRep.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return retRep;
	}
	// ----------------------------------------- Waiting -----------------------------------------------

//	public SLBook addBook(SLBook newBook) {
//		if (dao.insertBook(newBook)) {
//			return newBook;
//		} else {
//			return null;
//		}
//	}
//
//	public boolean deleteBook(String bookISBN) {
//		SLBook book = new SLBook();
//		book.setBookISBN(bookISBN);
//		return dao.deleteBook(book);
//	}
//
//	public boolean updateBook(SLBook updateBook) {
//		return dao.updateBook(updateBook);
//	}
}
