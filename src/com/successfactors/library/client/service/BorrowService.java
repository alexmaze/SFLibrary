package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
	
	BorrowPage getBorrowList(String strType, String userEmail,
			int itemsPerPage, int pageNum);
	
	// 获取所有用户借阅列表
	// strType = "history","now","all","overDue"
	BorrowPage getBorrowList(String strType, int itemsPerPage, int pageNum);

	// borrowType = "history","now","all","overDue", "inStore"
	BorrowPage searchBorrowList(String borrowType, String searchType,
			String searchValue, int itemsPerPage, int pageNum);
}
