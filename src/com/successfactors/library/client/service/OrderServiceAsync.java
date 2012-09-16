package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

public interface OrderServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void cancelOrder(int orderId, AsyncCallback<Boolean> callback);

	void getOrderInfo(int orderId, AsyncCallback<SLOrder> callback);

	void orderBook(String bookISBN, AsyncCallback<Boolean> callback);

	void getOrderList(String strType, String userEmail, int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);

	void getOrderList(String strType, int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);

	void searchOrderList(String orderType, String searchType,
			String searchValue, int itemsPerPage, int pageNum,
			AsyncCallback<OrderPage> callback);

}
