package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.OrderSearchType;
import com.successfactors.library.shared.OrderStatusType;
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
	
	// 获取某预定信息
	SLOrder getOrderInfo(int orderId);
	
	/**
	 * 获取某种状态下的，某用户的所有预定信息
	 * @param statusType 预定状态
	 * @param userEmail 用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	OrderPage getOrderList(OrderStatusType statusType, String userEmail, int itemsPerPage, int pageNum);
	
	/**
	 * 在某种状态下的预定信息中，所有用户中，搜索
	 * @param statusType 预定状态
	 * @param searchType 搜索类型
	 * @param searchValue 关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	OrderPage searchOrderList(OrderStatusType statusType, OrderSearchType searchType, String searchValue, int itemsPerPage, int pageNum);
}
