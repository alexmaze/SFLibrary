package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.DataWrapper;
import com.successfactors.library.shared.model.PageDataWrapper;
import com.successfactors.library.shared.model.SLBookReview;


public interface BookReviewServiceAsync {

	void getBookReviewById(Long reivewId,
			AsyncCallback<DataWrapper<SLBookReview>> callback);

	void addBookReview(SLBookReview bookReview,
			AsyncCallback<DataWrapper<SLBookReview>> callback);

	void updateBookReview(SLBookReview bookReview,
			AsyncCallback<DataWrapper<SLBookReview>> callback);

	void deleteBookReviewById(Long reviewId,
			AsyncCallback<DataWrapper<Boolean>> callback);

	void getBookReviewListByBookISBN(String bookISBN, int numberPerPage,
			int pageNumber,
			AsyncCallback<PageDataWrapper<ArrayList<SLBookReview>>> callback);

	void getBookReviewListByUserEmail(String userEmail, int numberPerPage,
			int pageNumber,
			AsyncCallback<PageDataWrapper<ArrayList<SLBookReview>>> callback);

}
