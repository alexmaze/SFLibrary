package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

@RemoteServiceRelativePath("orderService")
public interface OrderService extends RemoteService {

	// 测试服务联通性
	String helloServer(String strHello);
		
	// 预定图书
	boolean orderBook(String bookISBN);
	
	// 取消预定
	boolean cancelOrder(int orderId);
	
	// 获取某用户预定列表
	// strType = "history","now","all"
	OrderPage getOrderList(String strType, String userEmail, int iStart, int iEnd);

	// 获取所有用户借阅列表
	// strType = "history","now","all","overDue"
	OrderPage getOrderList(String strType, int iStart, int iEnd);
	
	// 获取某预定信息
	SLOrder getOrderInfo(int orderId);
}
