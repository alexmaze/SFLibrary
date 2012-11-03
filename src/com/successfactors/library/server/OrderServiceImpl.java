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
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

import static com.successfactors.library.server.UserServiceImpl.USER_SESSION_KEY;

/**
 * 
 * @author haimingli
 * 
 */
@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements
		OrderService {

	public static final String ORDER_INQUEUE = "排队中";
	public static final String ORDER_CANCElED = "已取消";
	public static final String ORDER_BORROWED = "已借到";

	protected SLOrderDao orderDao = new SLOrderDao();
	protected SLBookDao bookDao = new SLBookDao();
	protected SLUserDao userDao = new SLUserDao();
	
	private SLEmailUtil emailUtil = new SLEmailUtil();

	/**
	 * Test the connection of the server
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	/**
	 * 预订图书
	 * */
	@Override
	public boolean orderBook(String bookISBN) {

		// TODO 先检查是否已借！
		// TODO 再检查是否已预订！
		
		// 如果还有的借，不能预订
		if (bookDao.queryByISBN(bookISBN).getBookAvailableQuantity() > 0) {
			return false;
		}
		
		// 获取当前登录用户信息
		SLUser slUser = getCurrentUser();
		if (slUser == null) {
			return false;
		}

		// 插入记录
		SLOrder slOrder = new SLOrder();
		Calendar c = Calendar.getInstance();
		Date orderDate = c.getTime();
		slOrder.setUserEmail(slUser.getUserEmail());
		slOrder.setBookISBN(bookISBN);
		slOrder.setOrderDate(orderDate);
		slOrder.setStatus(ORDER_INQUEUE);
		orderDao.orderBook(slOrder);
		
		// 发送邮件
		slOrder.setTheBook(bookDao.queryByISBN(bookISBN));
		slOrder.setTheUser(userDao.getSLUserByEmail(slOrder.getUserEmail()));
		emailUtil.sendOrderSuccessEmail(slOrder);
		
		return true;
	}

	/**
	 * 取消预订
	 * */
	@Override
	public boolean cancelOrder(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setStatus(ORDER_CANCElED);
		return orderDao.updateOrder(slOrder);
	}

	/**
	 * 获取预订信息
	 * */
	@Override
	public SLOrder getOrderInfo(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setTheBook(bookDao.queryByISBN(slOrder.getBookISBN()));
		slOrder.setTheUser(userDao.getSLUserByEmail(slOrder.getUserEmail()));
		return slOrder;
	}

	/**
	 * （内部）搜索
	 * */
	private OrderPage searchOrderList(String firstType, String firstValue,
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
		return orderPage;
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
	@Override
	public OrderPage getOrderList(OrderStatusType statusType, String userEmail,
			int itemsPerPage, int pageNum) {
		return searchOrderList("status", OrderStatusType.toString(statusType),
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
	@Override
	public OrderPage searchOrderList(OrderStatusType statusType,
			OrderSearchType searchType, String searchValue, int itemsPerPage,
			int pageNum) {
		return searchOrderList("status", OrderStatusType.toString(statusType),
				OrderSearchType.toString(searchType), searchValue,
				itemsPerPage, pageNum, true);
	}

	/**
	 * 获取当前登录用户信息
	 * */
	protected SLUser getCurrentUser() {

		HttpServletRequest request = null;
		HttpSession session = null;
		SLUser slUser = null;
		request = getThreadLocalRequest();
		if (request != null) {
			session = request.getSession();
		}
		if (session != null) {
			slUser = (SLUser) session.getAttribute(USER_SESSION_KEY);
		}

		return slUser;
	}

}
