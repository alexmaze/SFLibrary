package com.successfactors.library.rest.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;

import com.successfactors.library.rest.utils.SLSessionManager;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLBorrowDao;
import com.successfactors.library.server.dao.SLOrderDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.RestCallInfo;
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;
import com.successfactors.library.shared.RestCallInfo.RestCallStatus;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

@SuppressWarnings({"rawtypes", "unchecked"})
@Path("borrow")
public class BorrowResource {

	protected SLBorrowDao borrowDao = SLBorrowDao.getDao();
	protected SLOrderDao orderDao = SLOrderDao.getDao();
	protected SLBookDao bookDao = SLBookDao.getDao();
	protected SLUserDao userDao = SLUserDao.getDao();
	
	private SLEmailUtil emailUtil = new SLEmailUtil();
	
	/**
	 * 借阅图书
	 * */
	@PUT
	@Path("borrowbook")
	@Produces("application/json")
	public Representation borrowBook(Representation entity) {
		
		JSONReader reader = new JSONValidatingReader();
		HashMap result = null;
		
		HashMap returnInfo = new HashMap();
		
		try {
			result = (HashMap) reader.read(entity.getText());
		} catch (IOException e) {
			e.printStackTrace();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		
		if (result == null || !result.containsKey("sessionKey") || !result.containsKey("bookISBN")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		String sessionKey = result.get("sessionKey").toString();
		String bookISBN = result.get("bookISBN").toString();
		
		SLUser slUser = SLSessionManager.getSession(sessionKey);
		if (slUser == null) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.need_login);
			return new JsonRepresentation(returnInfo);
		}
		
		switch (borrowBook(slUser.getUserEmail(), bookISBN)) {
			case already_borrowed:
				returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
				returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.already_borrowed);
				break;
			case no_available_left:
				returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
				returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_available_left);
				break;
			case no_error:
				returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.success);
				returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
				break;
			default: 
				returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
				returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.unknown_error);
		}
		
		return new JsonRepresentation(returnInfo);
	}

	/**
	 * 还书, 只有管理员才有权限！
	 * */
	@PUT
	@Path("returnbook")
	@Produces("application/json")
	public Representation returnBook(Representation entity) {
		
		JSONReader reader = new JSONValidatingReader();
		HashMap result = null;
		
		HashMap returnInfo = new HashMap();
		
		try {
			result = (HashMap) reader.read(entity.getText());
		} catch (IOException e) {
			e.printStackTrace();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		
		if (result == null || !result.containsKey("sessionKey") || !result.containsKey("borrowId")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		String sessionKey = result.get("sessionKey").toString();
		int borrowId = Integer.parseInt(result.get("borrowId").toString());
		
		SLUser user = SLSessionManager.getSession(sessionKey);
		if (!user.getUserType().equals("管理员")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.need_admin_authority);
			return new JsonRepresentation(returnInfo);
		} 
		
		
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setStatus("已归还");
		slBorrow.setReturnDate(new Date());
		
		if (!borrowDao.update(slBorrow)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}
		
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() + 1);
		if (!slBorrow.isInStore()) {
			slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() + 1);
		}
		if (!bookDao.updateBook(slBook)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}
		SLOrder slOrder = orderDao.getEarlistOrder(slBook.getBookISBN());
		if (slOrder != null) {
			// 自动为最早预订的用户借阅
			if (borrowBook(slOrder.getUserEmail(), slBook.getBookISBN()) == RestCallErrorCode.no_error) {
				slOrder.setStatus("已借到");
				orderDao.updateOrder(slOrder);
			}
		}

		returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.success);
		returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		return new JsonRepresentation(returnInfo);
	}
	
	/**
	 * 获取借阅信息
	 * */
	@GET
	@Path("getborrowinfo/{borrowId}")
	@Produces("application/json")
	public Representation getBorrowInfo(@PathParam("borrowId") int borrowId) {
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		if (slBorrow == null) {
			HashMap returnInfo = new HashMap();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_such_borrow);
			return new JsonRepresentation(returnInfo);
		}
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		SLUser slUser = userDao.getSLUserByEmail(slBorrow.getUserEmail());
		slBorrow.setTheBook(slBook);
		slBorrow.setTheUser(slUser);
		
		JsonRepresentation ret = new JsonRepresentation(slBorrow);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 图书出库, 只有管理员由此权限
	 * */
	@PUT
	@Path("outstorebook")
	@Produces("application/json")
	public Representation outStoreBook(Representation entity) {
		
		JSONReader reader = new JSONValidatingReader();
		HashMap result = null;
		
		HashMap returnInfo = new HashMap();
		
		try {
			result = (HashMap) reader.read(entity.getText());
		} catch (IOException e) {
			e.printStackTrace();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		
		if (result == null || !result.containsKey("sessionKey") || !result.containsKey("borrowId")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		String sessionKey = result.get("sessionKey").toString();
		int borrowId = Integer.parseInt(result.get("borrowId").toString());
		
		SLUser user = SLSessionManager.getSession(sessionKey);
		if (!user.getUserType().equals("管理员")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.need_admin_authority);
			return new JsonRepresentation(returnInfo);
		} 
		
		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
		slBorrow.setInStore(false);
		if (!borrowDao.update(slBorrow)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}
		SLBook slBook = bookDao.queryByISBN(slBorrow.getBookISBN());
		slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() - 1);
		if (!bookDao.updateBook(slBook)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}
		
		returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.success);
		returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		return new JsonRepresentation(returnInfo);
	}

	/**
	 * 获取某种状态下的，某用户的所有借阅信息
	 * 
	 * @param statusType
	 *            借阅状态
	 * @param userEmail
	 *            用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	@GET
	@Path("getborrowlistpage/{statusType}/{userEmail}/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation getBorrowList(
//			@PathParam("statusType") BorrowStatusType statusType,
			@PathParam("statusType") String statusType,
			@PathParam("userEmail") String userEmail,
			@PathParam("itemsPerPage") int itemsPerPage,
			@PathParam("pageNum") int pageNum) {
		
		BorrowPage page = null;
		List<SLBorrow> result = null;
		if (userEmail == null) {
			page = borrowDao.searchBorrowList(BorrowStatusType.parse(statusType), itemsPerPage, pageNum);
		} else {
			page = borrowDao.searchBorrowList(BorrowStatusType.parse(statusType), userEmail,
					itemsPerPage, pageNum);
		}
		result = page.getTheBorrows();
		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		
		page.setTheBorrows((ArrayList<SLBorrow>) result);
		page.setItemsNumPerPage(itemsPerPage);
		page.setPageNum(pageNum);
		
		JsonRepresentation ret = new JsonRepresentation(page);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;	
	}
	
	/**
	 * 在某种状态下的借阅信息中，所有用户中，搜索
	 * 
	 * @param statusType
	 *            借阅状态
	 * @param searchType
	 *            搜索类型
	 * @param searchValue
	 *            关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	@GET
	@Path("searchborrowlistpage/{statusType}/{searchType}/{searchValue}/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation searchBorrowList(
			@PathParam("statusType") BorrowStatusType statusType,
			@PathParam("searchType") BorrowSearchType searchType,
			@PathParam("searchValue") String searchValue,
			@PathParam("itemsPerPage") int itemsPerPage,
			@PathParam("pageNum") int pageNum) {
		
		BorrowPage page = borrowDao.searchBorrowList(statusType,
				searchType, searchValue, itemsPerPage, pageNum);
		List<SLBorrow> result = page.getTheBorrows();
		
		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		page.setTheBorrows((ArrayList<SLBorrow>) result);
		page.setItemsNumPerPage(itemsPerPage);
		page.setPageNum(pageNum);
		
		JsonRepresentation ret = new JsonRepresentation(page);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;	
	}

	/**
	 * 获取所有超期借阅记录
	 * */
	@GET
	@Path("getoverdueborrowlistpage")
	@Produces("application/json")
	public Representation getOverdueBorrowList() {
		
		BorrowStatusType statusType = BorrowStatusType.parse("已超期");
		ArrayList<SLBorrow> result = (ArrayList<SLBorrow>) borrowDao.searchBorrowList(statusType);

		for (int i = 0; i < result.size(); i++) {
			SLBorrow slBorrow = result.get(i);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			result.set(i, slBorrow);
		}
		
		BorrowPage page = new BorrowPage();
		page.setTheBorrows(result);
		
		JsonRepresentation ret = new JsonRepresentation(page);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;	
	}
	
	// ----------------------------------------- Waiting -----------------------------------------------
	/**
	 * （内部）借阅
	 * */
	private RestCallErrorCode borrowBook(String userEmail, String bookISBN) {

		// 先检查是否已借！
		if (borrowDao.isUserBookBorrowed(userEmail, bookISBN)) {
			return RestCallErrorCode.already_borrowed;
		}
		
		SLBook slBook = bookDao.queryByISBN(bookISBN);
		if (slBook.getBookAvailableQuantity() > 0) {
			slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
			SLBorrow slBorrow = initBorrow(userEmail,
					slBook.getBookISBN());
			borrowDao.save(slBorrow);
			bookDao.updateBook(slBook);
			slBorrow.setTheBook(bookDao.queryByISBN(slBorrow.getBookISBN()));
			slBorrow.setTheUser(userDao.getSLUserByEmail(slBorrow
					.getUserEmail()));
			emailUtil.sendBorrowSuccessEmail(slBorrow);

			return RestCallErrorCode.no_error;
		} else {
			return RestCallErrorCode.no_available_left;
		}
	}

	/**
	 * 初始化借阅信息
	 * */
	private SLBorrow initBorrow(String userEmail, String ISBN) {
		SLBorrow newBorrow = new SLBorrow();
		Calendar c = Calendar.getInstance();
		Date borrowDate = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 15);
		Date shouldReturnDate = c.getTime();
		newBorrow.setUserEmail(userEmail);
		newBorrow.setBookISBN(ISBN);
		newBorrow.setBorrowDate(borrowDate);
		newBorrow.setShouldReturnDate(shouldReturnDate);
		newBorrow.setInStore(true);
		newBorrow.setOverdue(false);
		newBorrow.setStatus("未归还");
		return newBorrow;
	}

//	/**
//	 * 借阅图书丢失登记
//	 * */
//	public boolean lostBook(int borrowId) {
//		SLBorrow slBorrow = borrowDao.getBorrowById(borrowId);
//		slBorrow.setStatus("已丢失");
//		borrowDao.save(slBorrow);
//		SLBook slBook = bookDao.queryByISBN(slBorrow
//				.getBookISBN());
//		slBook.setBookTotalQuantity(slBook.getBookTotalQuantity() - 1);
//		slBook.setBookAvailableQuantity(slBook.getBookAvailableQuantity() - 1);
//		slBook.setBookInStoreQuantity(slBook.getBookInStoreQuantity() - 1);
//		bookDao.updateBook(slBook);
//		
//		return true;
//	}
	
}
