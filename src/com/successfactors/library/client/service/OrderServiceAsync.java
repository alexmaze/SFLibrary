package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

public interface OrderServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void cancelOrder(int orderId, AsyncCallback<Boolean> callback);

	void getOrderInfo(int orderId, AsyncCallback<SLOrder> callback);

	void orderBook(String bookISBN, AsyncCallback<Boolean> callback);

	void searchOrderList(String firstType, String firstValue,
			String secondType, String secondValue, int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);
	
	void searchOrderList(String searchType, String searchValue, int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);

	void searchAllOrderList(int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);

	void updateStatusAfterBorrowBook(int orderId,
			AsyncCallback<Boolean> callback);

}
