package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;

public interface BorrowServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void borrowBook(String bookISBN, AsyncCallback<Boolean> callback);

	void getBorrowInfo(int borrowId, AsyncCallback<SLBorrow> callback);

	void lostBook(int borrowId, AsyncCallback<Boolean> callback);

	void returnBook(int borrowId, AsyncCallback<Boolean> callback);

//	void getBorrowList(String strType, String userEmail, int itemsPerPage, int pageNum,
//			AsyncCallback<BorrowPage> callback);
//
//	void getBorrowList(String strType, int itemsPerPage, int pageNum,
//			AsyncCallback<BorrowPage> callback);
//
//	void searchBorrowList(String borrowType, String searchType, String searchValue,
//			int itemsPerPage, int pageNum, AsyncCallback<BorrowPage> callback);

	void getBorrowList(BorrowStatusType statusType, String userEmail,
			int itemsPerPage, int pageNum, AsyncCallback<BorrowPage> callback);

	void searchBorrowList(BorrowStatusType statusType,
			BorrowSearchType searchType, String searchValue, int itemsPerPage,
			int pageNum, AsyncCallback<BorrowPage> callback);

	void getOverdueBorrowList(AsyncCallback<ArrayList<SLBorrow>> callback);

	void outStoreBook(int borrowId, AsyncCallback<Boolean> callback);

}
