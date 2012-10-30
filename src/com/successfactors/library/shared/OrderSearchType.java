package com.successfactors.library.shared;

public enum OrderSearchType {

	/**
	 * 按预定号查询
	 * */
	ORDER_ID,

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

	public static OrderSearchType parse(String strType) {

		if (strType.equalsIgnoreCase("预定编号")) {
			return OrderSearchType.ORDER_ID;
		} else if (strType.equalsIgnoreCase("用户邮箱")) {
			return OrderSearchType.USER_EMAIL;
		} else if (strType.equalsIgnoreCase("书籍ISBN")) {
			return OrderSearchType.BOOK_ISBN;
		} else if (strType.equalsIgnoreCase("预定状态")) {
			return OrderSearchType.STATUS;
		}

		return null;
	}
}
