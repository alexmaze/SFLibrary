package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.OrderService;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public boolean orderBook(String bookISBN) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelOrder(int orderId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderPage getOrderList(String strType, String userEmail, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderPage getOrderList(String strType, int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SLOrder getOrderInfo(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
