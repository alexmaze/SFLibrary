package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendedBook;

public interface RecommendedBookServiceAsync {

	void getRecommendedBook(String bookISBN,
			AsyncCallback<SLRecommendedBook> callback);

	void updateRecommendedBookStatus(SLRecommendedBook recommendedBook,
			AsyncCallback<Boolean> callback);

	void recommendBook(SLRecommendedBook recommendedBook,
			AsyncCallback<Boolean> callback);

	void getAllRecBookList(int itemsPerPage, int pageNum,
			AsyncCallback<RecommendedBookPage> callback);

	void buyBookList(ArrayList<String> bookISBNList,
			AsyncCallback<Boolean> callback);

	void getNewRecBookList(int num, AsyncCallback<RecommendedBookPage> callback);

}
