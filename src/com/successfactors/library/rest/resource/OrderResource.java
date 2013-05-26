package com.successfactors.library.rest.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
import com.successfactors.library.shared.OrderSearchType;
import com.successfactors.library.shared.OrderStatusType;
import com.successfactors.library.shared.RestCallInfo;
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;
import com.successfactors.library.shared.RestCallInfo.RestCallStatus;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

@SuppressWarnings({"rawtypes", "unchecked"})
@Path("order")
public class OrderResource {

	public static final String ORDER_INQUEUE = "排队中";
	public static final String ORDER_CANCElED = "已取消";
	public static final String ORDER_BORROWED = "已借到";

	protected SLOrderDao orderDao = SLOrderDao.getDao();
	protected SLBookDao bookDao = SLBookDao.getDao();
	protected SLUserDao userDao = SLUserDao.getDao();
	protected SLBorrowDao borrowDao = SLBorrowDao.getDao();
	
	private SLEmailUtil emailUtil = new SLEmailUtil();
	
	
	/**
	 * 预订图书
	 * */
	@PUT
	@Path("orderbook")
	@Produces("application/json")
	public Representation orderBook(Representation entity) {
		
		
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
		
		// 如果还有的借，不能预订
		if (bookDao.queryByISBN(bookISBN).getBookAvailableQuantity() > 0) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.can_not_order_while_you_can_borrow);
			return new JsonRepresentation(returnInfo);
		}
		
		// 先检查是否已借！
		if (borrowDao.isUserBookBorrowed(slUser.getUserEmail(), bookISBN)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.already_borrowed);
			return new JsonRepresentation(returnInfo);
		}
		// 再检查是否已预订！
		if (orderDao.isUserBookOrdered(slUser.getUserEmail(), bookISBN)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.already_ordered);
			return new JsonRepresentation(returnInfo);
		}
		
		// 插入记录
		SLOrder slOrder = new SLOrder();
		Calendar c = Calendar.getInstance();
		Date orderDate = c.getTime();
		slOrder.setUserEmail(slUser.getUserEmail());
		slOrder.setBookISBN(bookISBN);
		slOrder.setOrderDate(orderDate);
		slOrder.setStatus(ORDER_INQUEUE);
		
		if (!orderDao.orderBook(slOrder)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}
		
		// 发送邮件
		slOrder.setTheBook(bookDao.queryByISBN(bookISBN));
		slOrder.setTheUser(userDao.getSLUserByEmail(slOrder.getUserEmail()));
		emailUtil.sendOrderSuccessEmail(slOrder);

		returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.success);
		returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		return new JsonRepresentation(returnInfo);
	}


	/**
	 * 取消预订
	 * */
	@PUT
	@Path("cancelorder")
	@Produces("application/json")
	public Representation cancelOrder(Representation entity) {
		
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
		
		if (result == null || !result.containsKey("sessionKey") || !result.containsKey("orderId")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.json_format_error);
			return new JsonRepresentation(returnInfo);
		}
		String sessionKey = result.get("sessionKey").toString();
		int orderId = Integer.parseInt(result.get("orderId").toString());
		
		SLUser slUser = SLSessionManager.getSession(sessionKey);
		if (slUser == null) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.need_login);
			return new JsonRepresentation(returnInfo);
		}
		
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		
		if (!slOrder.getUserEmail().equals(slUser.getUserEmail()) && !slUser.getUserType().equals("管理员")) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.can_not_modify_other_person);
			return new JsonRepresentation(returnInfo);
		}
		
		slOrder.setStatus(ORDER_CANCElED);
		
		if (!orderDao.updateOrder(slOrder)) {
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.db_operate_error);
			return new JsonRepresentation(returnInfo);
		}

		returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.success);
		returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		return new JsonRepresentation(returnInfo);
	}
	
	/**
	 * 获取预订信息
	 * */
	@GET
	@Path("getorderinfo/{orderId}")
	@Produces("application/json")
	public Representation getOrderInfo(@PathParam("orderId") int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		if (slOrder == null) {
			HashMap returnInfo = new HashMap();
			returnInfo.put(RestCallInfo.REST_STATUS, RestCallStatus.fail);
			returnInfo.put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_such_order);
			return new JsonRepresentation(returnInfo);
		}
		
		slOrder.setTheBook(bookDao.queryByISBN(slOrder.getBookISBN()));
		slOrder.setTheUser(userDao.getSLUserByEmail(slOrder.getUserEmail()));
		
		JsonRepresentation ret = new JsonRepresentation(slOrder);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 获取某种状态下的，某用户的所有预定信息
	 * 
	 * @param statusType
	 *            预定状态
	 * @param userEmail
	 *            用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	@GET
	@Path("getorderlistpage/{statusType}/{userEmail}/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation getOrderList(
			@PathParam("statusType") String statusType,
			@PathParam("userEmail") String userEmail,
			@PathParam("itemsPerPage") int itemsPerPage,
			@PathParam("pageNum") int pageNum) {
		
		return searchOrderList("status", statusType,
				"userEmail", userEmail, itemsPerPage, pageNum, false);
		
	}

	/**
	 * 在某种状态下的预定信息中，所有用户中，搜索
	 * 
	 * @param statusType
	 *            预定状态
	 * @param searchType
	 *            搜索类型
	 * @param searchValue
	 *            关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	@GET
	@Path("searchorderlistpage/{statusType}/{searchType}/{searchValue}/{itemsPerPage}/{pageNum}")
	@Produces("application/json")
	public Representation searchOrderList(
			@PathParam("statusType") OrderStatusType statusType,
			@PathParam("searchType") OrderSearchType searchType,
			@PathParam("searchValue") String searchValue,
			@PathParam("itemsPerPage") int itemsPerPage,
			@PathParam("pageNum") int pageNum) {
		
		return searchOrderList("status", OrderStatusType.toString(statusType),
				OrderSearchType.toString(searchType), searchValue,
				itemsPerPage, pageNum, true);
	}
	
	// ----------------------------------------- Waiting -----------------------------------------------
	/**
	 * （内部）搜索
	 * */
	private Representation searchOrderList(String firstType, String firstValue,
			String secondType, String secondValue, int itemsPerPage,
			int pageNum, boolean isLike) {
		
		ArrayList<SLOrder> listOrders = (ArrayList<SLOrder>) orderDao
				.searchOrderList(firstType, firstValue, secondType,
						secondValue, itemsPerPage, pageNum, isLike);
		OrderPage orderPage = new OrderPage(itemsPerPage, pageNum);
		orderPage.setTheOrders(listOrders);
		long totalNum = orderDao.selectCount(firstType, firstValue, secondType,
				secondValue, isLike);
		if (totalNum % itemsPerPage == 0) {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		JsonRepresentation ret = new JsonRepresentation(orderPage);
		try {
			ret.getJsonObject().put(RestCallInfo.REST_STATUS, RestCallStatus.success);
			ret.getJsonObject().put(RestCallInfo.REST_ERROR_CODE, RestCallErrorCode.no_error);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;	
	}

	
}
