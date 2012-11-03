package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.OrderService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLOrderDao;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.OrderSearchType;
import com.successfactors.library.shared.OrderStatusType;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

/**
 * 
 * @author haimingli
 *
 */
@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {

	private static final String ORDER_INQUEUE = "排队中";
	private static final String ORDER_CANCElED = "已取消";
	private static final String ORDER_BORROWED = "已借到";
	private final static String USER_SESSION_KEY = "SF_LIB_USER"; 
	
	private SLOrderDao orderDao = new SLOrderDao();
	private SLBookDao bookDao = new SLBookDao();
	private SLUserDao userDao = new SLUserDao();

	/**
	 * Test the connection of the server
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public boolean orderBook(String bookISBN) {
		
		if (bookDao.queryByISBN(bookISBN).getBookAvailableQuantity() > 0) {
			return false;
		}
		
		SLOrder slOrder = new SLOrder();
		Calendar c = Calendar.getInstance();
		Date orderDate = c.getTime();
		HttpServletRequest request = null;
		HttpSession session = null;
		SLUser slUser = null;
		request = getThreadLocalRequest();
		if(request != null){
			session = request.getSession();
		}
		if(session != null){
			slUser = (SLUser) session.getAttribute(USER_SESSION_KEY);
		}else{
			return false;//if the session is null, this order can not be successful
		}
		slOrder.setUserEmail(slUser.getUserEmail());
		slOrder.setBookISBN(bookISBN);
		slOrder.setOrderDate(orderDate);
		slOrder.setStatus(ORDER_INQUEUE);
		
		return orderDao.orderBook(slOrder);
	}

	@Override
	public boolean cancelOrder(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setStatus(ORDER_CANCElED);
		return orderDao.updateOrder(slOrder);
	}
	
	@Override
	public boolean updateStatusAfterBorrowBook(int orderId){
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setStatus(ORDER_BORROWED);
		return orderDao.updateOrder(slOrder);
	}

	@Override
	public SLOrder getOrderInfo(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setTheBook(bookDao.queryByISBN(slOrder.getBookISBN()));
		slOrder.setTheUser(userDao.getSLUserByEmail(slOrder.getUserEmail()));
		return slOrder;
	}
	
	private OrderPage searchOrderList(String firstType, String firstValue,
			String secondType, String secondValue, int itemsPerPage, int pageNum,boolean isLike) {
		ArrayList<SLOrder> listOrders = (ArrayList<SLOrder>) orderDao.searchOrderList(firstType, firstValue,
				secondType,secondValue, itemsPerPage, pageNum, isLike);
		OrderPage orderPage = new OrderPage(itemsPerPage, pageNum);
		orderPage.setTheOrders(listOrders);
		long totalNum = orderDao.selectCount(firstType, firstValue, secondType, secondValue, isLike);
		if (totalNum % itemsPerPage == 0) {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		return orderPage;
	}
//	
//	@Override
//	public OrderPage searchOrderList(String searchType, String searchValue,
//			int itemsPerPage, int pageNum){
//		return searchOrderList(searchType, searchValue, 
//				QueryType.ALL, null, itemsPerPage, pageNum);
//	}
//	
//	@Override
//	public OrderPage searchAllOrderList(int itemsPerPage, int pageNum){
//		return searchOrderList(QueryType.ALL, null, 
//				QueryType.ALL, null, itemsPerPage, pageNum);
//	}
	
	/**
	 * 获取某种状态下的，某用户的所有预定信息
	 * @param statusType 预定状态
	 * @param userEmail 用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	@Override
	public OrderPage getOrderList(OrderStatusType statusType, String userEmail,
			int itemsPerPage, int pageNum) {
		return searchOrderList("status", OrderStatusType.toString(statusType),
				"userEmail", userEmail, itemsPerPage, pageNum, false);
	}

	/**
	 * 在某种状态下的预定信息中，所有用户中，搜索
	 * @param statusType 预定状态
	 * @param searchType 搜索类型
	 * @param searchValue 关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	@Override
	public OrderPage searchOrderList(OrderStatusType statusType,
			OrderSearchType searchType, String searchValue, int itemsPerPage,
			int pageNum) {
		return searchOrderList("status", OrderStatusType.toString(statusType),
				OrderSearchType.toString(searchType), searchValue, itemsPerPage, pageNum, true);
	}
	 
}
