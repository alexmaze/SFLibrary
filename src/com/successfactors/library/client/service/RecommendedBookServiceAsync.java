package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.RecommendedBookStatusType;
import com.successfactors.library.shared.model.SLRecommendedBook;

public interface RecommendedBookServiceAsync {

	void getRecommendedBook(String bookISBN,
			AsyncCallback<SLRecommendedBook> callback);

	void updateRecommendedBookStatus(String bookISBN,
			RecommendedBookStatusType statusType,
			AsyncCallback<Boolean> callback);

	void recommendBook(SLRecommendedBook recommendedBook,
			AsyncCallback<Boolean> callback);

}
