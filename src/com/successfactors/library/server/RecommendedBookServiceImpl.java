package com.successfactors.library.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.RecommendedBookService;
import com.successfactors.library.shared.RecommendedBookStatusType;
import com.successfactors.library.shared.model.SLRecommendedBook;

@SuppressWarnings("serial")
public class RecommendedBookServiceImpl extends RemoteServiceServlet implements RecommendedBookService {

	@Override
	public boolean recommendBook(SLRecommendedBook recommendedBook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SLRecommendedBook getRecommendedBook(String bookISBN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateRecommendedBookStatus(String bookISBN,
			RecommendedBookStatusType statusType) {
		// TODO Auto-generated method stub
		return false;
	}

}
