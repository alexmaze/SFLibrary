package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.OrderService;
import com.successfactors.library.server.dao.MysqlOrderDao;
import com.successfactors.library.shared.QueryType;
import com.successfactors.library.shared.StatusType;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

/**
 * 
 * @author haimingli
 *
 */
@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {
	
	private MysqlOrderDao orderDao = new MysqlOrderDao();

	/**
	 * Test the connection of the server
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public boolean orderBook(String bookISBN) {
		SLOrder slOrder = new SLOrder();
		Calendar c = Calendar.getInstance();
		Date orderDate = c.getTime();
		
		//slOrder.setUserEmail(userEmail);//Has not finished yet
		slOrder.setBookISBN(bookISBN);
		slOrder.setOrderDate(orderDate);
		slOrder.setStatus(StatusType.ORDER_INQUEUE);
		
		return orderDao.orderBook(slOrder);
	}

	@Override
	public boolean cancelOrder(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setStatus(StatusType.ORDER_CANCElED);
		return orderDao.updateOrder(slOrder);
	}
	
	@Override
	public boolean updateStatusAfterBorrowBook(int orderId){
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		slOrder.setStatus(StatusType.ORDER_BORROWED);
		return orderDao.updateOrder(slOrder);
	}

	@Override
	public SLOrder getOrderInfo(int orderId) {
		SLOrder slOrder = orderDao.getSLOrderByOrderId(orderId);
		return slOrder;
	}
	
	@Override
	public OrderPage searchOrderList(String firstType, String firstValue,
			String secondType, String secondValue, int itemsPerPage, int pageNum) {
		ArrayList<SLOrder> listOrders = (ArrayList<SLOrder>) orderDao.searchOrderList(firstType, firstValue,
				secondType,secondValue, itemsPerPage, pageNum);
		OrderPage orderPage = new OrderPage(itemsPerPage, pageNum);
		orderPage.setTheOrders(listOrders);
		long totalNum = orderDao.selectCount(firstType, firstValue, secondType, secondValue);
		if (totalNum % itemsPerPage == 0) {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			orderPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		return orderPage;
	}
	
	@Override
	public OrderPage searchOrderList(String searchType, String searchValue,
			int itemsPerPage, int pageNum){
		return searchOrderList(searchType, searchValue, 
				QueryType.ALL, null, itemsPerPage, pageNum);
	}
	
	@Override
	public OrderPage searchAllOrderList(int itemsPerPage, int pageNum){
		return searchOrderList(QueryType.ALL, null, 
				QueryType.ALL, null, itemsPerPage, pageNum);
	}
	 

}
