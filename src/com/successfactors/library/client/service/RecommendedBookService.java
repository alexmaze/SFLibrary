package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendedBook;

@RemoteServiceRelativePath("recommendedBookService")
public interface RecommendedBookService extends RemoteService {
	
	// 推荐图书
	public boolean recommendBook(SLRecommendedBook recommendedBook);
	
	// 获取推荐图书的信息
	public SLRecommendedBook getRecommendedBook(String bookISBN);
	
	// 修改推荐图书状态
	public boolean updateRecommendedBookStatus(SLRecommendedBook recommendedBook);

	// 获取所有推荐图书列表
	public RecommendedBookPage getAllRecBookList(int itemsPerPage, int pageNum);
	
	// 购买书单
	public boolean buyBookList(ArrayList<String> bookISBNList);
	
	// 获取最新推荐
	public RecommendedBookPage getNewRecBookList(int num);
	
}
