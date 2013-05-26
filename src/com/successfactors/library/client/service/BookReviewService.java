package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.DataWrapper;
import com.successfactors.library.shared.model.PageDataWrapper;
import com.successfactors.library.shared.model.SLBookReview;

@RemoteServiceRelativePath("bookReviewService")
public interface BookReviewService extends RemoteService {
	
	/**
	 * 通过ID获取书评信息
	 * @param reivewId
	 * @return
	 */
	DataWrapper<SLBookReview> getBookReviewById(Long reivewId);
	
	/**
	 * 添加书评
	 * @param bookReview
	 * @return
	 */
	DataWrapper<SLBookReview> addBookReview(SLBookReview bookReview);
	
	/**
	 * 更新书评
	 * @param bookReview
	 * @return
	 */
	DataWrapper<SLBookReview> updateBookReview(SLBookReview bookReview);
	
	/**
	 * 删除书评
	 * @param reviewId
	 * @return
	 */
	DataWrapper<Boolean> deleteBookReviewById(Long reviewId);

	/**
	 * 通过书籍ISBN获取书评列表
	 * @param bookISBN
	 * @param numberPerPage
	 * @param pageNumber
	 * @return
	 */
	PageDataWrapper<ArrayList<SLBookReview>> getBookReviewListByBookISBN(String bookISBN, int numberPerPage, int pageNumber);
	
	/**
	 * 通过用户Email获取书评列表
	 * @param userEmail
	 * @param numberPerPage
	 * @param pageNumber
	 * @return
	 */
	PageDataWrapper<ArrayList<SLBookReview>> getBookReviewListByUserEmail(String userEmail, int numberPerPage, int pageNumber);
	
}
