package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.SLOrder;

public interface OrderServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void cancelOrder(int orderId, AsyncCallback<Boolean> callback);

	void getOrderInfo(int orderId, AsyncCallback<SLOrder> callback);

	void orderBook(String bookISBN, AsyncCallback<Boolean> callback);

	void getOrderList(String strType, String userEmail, int iStart, int iEnd,
			AsyncCallback<ArrayList<SLOrder>> callback);

	void getOrderList(String strType, int iStart, int iEnd,
			AsyncCallback<ArrayList<SLOrder>> callback);

}
