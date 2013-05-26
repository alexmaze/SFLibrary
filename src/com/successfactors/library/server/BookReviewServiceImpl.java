package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.BookReviewService;
import com.successfactors.library.server.dao.SLBookReviewDao;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;
import com.successfactors.library.shared.model.DataWrapper;
import com.successfactors.library.shared.model.DataWrapper.CallStatusEnum;
import com.successfactors.library.shared.model.PageDataWrapper;
import com.successfactors.library.shared.model.SLBookReview;

public class BookReviewServiceImpl extends RemoteServiceServlet  implements BookReviewService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5766884021987315040L;

	private SLBookReviewDao dao = new SLBookReviewDao();
	
	@Override
	public DataWrapper<SLBookReview> getBookReviewById(Long reivewId) {
		DataWrapper<SLBookReview> ret = new DataWrapper<SLBookReview>();
		SLBookReview bookReview = dao.getById(reivewId);
		if (bookReview == null) {
			ret.setCallStatus(CallStatusEnum.FAILED);
			ret.setErrorCode(RestCallErrorCode.can_not_find_this_review);
		} else {
			ret.setCallStatus(CallStatusEnum.SUCCEED);
			ret.setErrorCode(RestCallErrorCode.no_error);
			ret.setData(bookReview);
		}
		return ret;
	}

	@Override
	public DataWrapper<SLBookReview> addBookReview(SLBookReview bookReview) {
		DataWrapper<SLBookReview> ret = new DataWrapper<SLBookReview>();
		if (dao.save(bookReview)) {
			ret.setData(bookReview);
			ret.setCallStatus(CallStatusEnum.SUCCEED);
			ret.setErrorCode(RestCallErrorCode.no_error);
		} else {
			ret.setCallStatus(CallStatusEnum.FAILED);
			ret.setErrorCode(RestCallErrorCode.db_operate_error);
		}
		return ret;
	}

	@Override
	public DataWrapper<SLBookReview> updateBookReview(SLBookReview bookReview) {
		DataWrapper<SLBookReview> ret = new DataWrapper<SLBookReview>();
		if (dao.update(bookReview)) {
			ret.setData(bookReview);
			ret.setCallStatus(CallStatusEnum.SUCCEED);
			ret.setErrorCode(RestCallErrorCode.no_error);
		} else {
			ret.setCallStatus(CallStatusEnum.FAILED);
			ret.setErrorCode(RestCallErrorCode.db_operate_error);
		}
		return ret;
	}

	@Override
	public DataWrapper<Boolean> deleteBookReviewById(Long reviewId) {
		DataWrapper<Boolean> ret = new DataWrapper<Boolean>();
		SLBookReview delReview = dao.getById(reviewId);

		if (delReview == null) {
			ret.setData(false);
			ret.setCallStatus(CallStatusEnum.FAILED);
			ret.setErrorCode(RestCallErrorCode.can_not_find_this_review);
		} else if (dao.remove(delReview)) {
			ret.setData(true);
			ret.setCallStatus(CallStatusEnum.SUCCEED);
			ret.setErrorCode(RestCallErrorCode.no_error);
		} else {
			ret.setData(false);
			ret.setCallStatus(CallStatusEnum.FAILED);
			ret.setErrorCode(RestCallErrorCode.db_operate_error);
		}

		return ret;
	}

	@Override
	public PageDataWrapper<ArrayList<SLBookReview>> getBookReviewListByBookISBN(
			String bookISBN, int numberPerPage, int pageNumber) {
		PageDataWrapper<ArrayList<SLBookReview>> ret = dao.getReviewListByBookISBN(bookISBN, numberPerPage, pageNumber);
		ret.setCallStatus(CallStatusEnum.SUCCEED);
		ret.setErrorCode(RestCallErrorCode.no_error);
		return ret;
	}

	@Override
	public PageDataWrapper<ArrayList<SLBookReview>> getBookReviewListByUserEmail(
			String userEmail, int numberPerPage, int pageNumber) {
		PageDataWrapper<ArrayList<SLBookReview>> ret = dao.getReviewListByUserEmail(userEmail, numberPerPage, pageNumber);
		ret.setCallStatus(CallStatusEnum.SUCCEED);
		ret.setErrorCode(RestCallErrorCode.no_error);
		return ret;
	}

}
