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
	
	// 借取某本书以后更新order的status
	boolean updateStatusAfterBorrowBook(int orderId);
	
	// 获取某预定信息
	SLOrder getOrderInfo(int orderId);

	/**
	 * Search the orderList (Two search type)
	 * @param firstType first search type 
	 * Important!!! if you want to use ALL in type
	 * you can just use the method with one search type
	 * @param firstValue value of first search type
	 * @param secondType second search type
	 * @param secondValue value of second search type
	 * @param itemsPerPage 
	 * @param pageNum
	 * @return
	 */
	OrderPage searchOrderList(String firstType, String firstValue,
			String secondType, String secondValue, int itemsPerPage, int pageNum);
	
	/**
	 * Search the orderList (One search type)
	 * @param searchType
	 * Important!!! if you want to use ALL in type
	 * you can just use searchAllOrderList
	 * @param searchValue
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	OrderPage searchOrderList(String searchType, String searchValue, int itemsPerPage, int pageNum);
	
	/**
	 * Search all the order in dataBase
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	OrderPage searchAllOrderList(int itemsPerPage, int pageNum);
}
