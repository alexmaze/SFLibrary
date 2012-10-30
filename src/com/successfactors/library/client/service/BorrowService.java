package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;

@RemoteServiceRelativePath("borrowService")
public interface BorrowService extends RemoteService {

	// 测试服务联通性
	String helloServer(String strHello);
	
	// 借阅图书
	boolean borrowBook(String bookISBN);
		
	// 归还图书
	boolean returnBook(int borrowId);
	
	// 图书遗失登记
	boolean lostBook(int borrowId);
	
	// 通过ID获取借阅信息
	SLBorrow getBorrowInfo(int borrowId);
	
//	BorrowPage getBorrowList(String strType, String userEmail,
//			int itemsPerPage, int pageNum);
//	
//	// 获取所有用户借阅列表
//	// strType = "history","now","all","overDue"
//	//"未归还"，"已归还","已超期"
//	BorrowPage getBorrowList(String strType, int itemsPerPage, int pageNum);
//
//	// borrowType = "history","now","all","overDue", "inStore"
//	BorrowPage searchBorrowList(String borrowType, String searchType,
//			String searchValue, int itemsPerPage, int pageNum);
	
	/**
	 * 获取某种状态下的，某用户的所有借阅信息
	 * @param statusType 借阅状态
	 * @param userEmail 用户邮箱地址，注：当userEmail==null时，对所有用户
	 * @param itemsPerPage
	 * @param pageNum
	 */
	BorrowPage getBorrowList(BorrowStatusType statusType, String userEmail, int itemsPerPage, int pageNum);
	
	/**
	 * 在某种状态下的借阅信息中，所有用户中，搜索
	 * @param statusType 借阅状态
	 * @param searchType 搜索类型
	 * @param searchValue 关键词
	 * @param itemsPerPage
	 * @param pageNum
	 * */
	BorrowPage searchBorrowList(BorrowStatusType statusType, BorrowSearchType searchType, String searchValue, int itemsPerPage, int pageNum);

	/**
	 * 获取所有超期借阅记录
	 * */
	ArrayList<SLBorrow> getOverdueBorrowList();
}
