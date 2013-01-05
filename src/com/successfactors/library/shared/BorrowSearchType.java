package com.successfactors.library.shared;

public enum BorrowSearchType {
	
	/**
	 * 按书名查询
	 * */
	BOOK_NAME,
	
	/**
	 * 按借阅编号查询
	 * */
	BORROW_ID,

	/**
	 * 按用户邮件地址查询
	 * */
	USER_EMAIL,

	/**
	 * 按书籍ISBN编号查询
	 * */
	BOOK_ISBN,

	/**
	 * 按预定状态查询
	 * */
	STATUS;

	public static BorrowSearchType parse(String strType) {

		if (strType.equalsIgnoreCase("书籍名称")) {
			return BorrowSearchType.BOOK_NAME;
		} else if (strType.equalsIgnoreCase("借阅编号")) {
			return BorrowSearchType.BORROW_ID;
		} else if (strType.equalsIgnoreCase("用户邮箱")) {
			return BorrowSearchType.USER_EMAIL;
		} else if (strType.equalsIgnoreCase("书籍ISBN")) {
			return BorrowSearchType.BOOK_ISBN;
		} else if (strType.equalsIgnoreCase("预定状态")) {
			return BorrowSearchType.STATUS;
		}

		return null;
	}
	
	public static String parse(BorrowSearchType searchType){

		if(searchType.equals(BOOK_NAME)) {
			return "bookName";
		} else if(searchType.equals(BORROW_ID)) {
			return "borrowId";
		} else if(searchType.equals(USER_EMAIL)) {
			return "userEmail";
		} else if(searchType.equals(BOOK_ISBN)) {
			return "bookISBN";
		} else if(searchType.equals(STATUS)) {
			return "status";
		}
		
		return null;
	}
	
}
