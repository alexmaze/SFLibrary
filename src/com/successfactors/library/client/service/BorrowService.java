package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
	
	// 续借图书
	SLBorrow renewBorrow(int borrowId);
	
	// 通过ID获取借阅信息
	SLBorrow getBorrowInfo(int borrowId);
	
	// 获取某用户借阅列表
	// strType = "history","now","all","overDue"
	ArrayList<SLBorrow> getBorrowList(String strType, String userEmail, int iStart, int iEnd);
	
	// 获取所有用户借阅列表
	// strType = "history","now","all","overDue"
	ArrayList<SLBorrow> getBorrowList(String strType, int iStart, int iEnd);
	
}
