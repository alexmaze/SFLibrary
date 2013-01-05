package com.successfactors.library.shared;

public enum BorrowStatusType {
	
	BORROW_NOT_RETURNED,

	BORROW_RETURNED,

	BORROW_OVERDUE,
	
	BORROW_NEED_TAKE,

	/**
	 * 历史记录
	 * */
	HISTORY,

	/**
	 * 当前记录
	 * */
	NOW;

	public static BorrowStatusType parse(String strType) {

		if (strType.equalsIgnoreCase("未归还")) {
			return BorrowStatusType.BORROW_NOT_RETURNED;
		} else if (strType.equalsIgnoreCase("已归还")) {
			return BorrowStatusType.BORROW_RETURNED;
		} else if (strType.equalsIgnoreCase("已超期")) {
			return BorrowStatusType.BORROW_OVERDUE;
		} else if (strType.equalsIgnoreCase("历史记录")) {
			return BorrowStatusType.HISTORY;
		} else if (strType.equalsIgnoreCase("当前记录")) {
			return BorrowStatusType.NOW;
		} else if (strType.equalsIgnoreCase("已借未取")) {
			return BorrowStatusType.NOW;
		}

		return null;
	}
	
	
	public static String parse(BorrowStatusType statusType){
		if(statusType.equals(BORROW_NOT_RETURNED)){
			return "未归还";
		} else if(statusType.equals(BORROW_RETURNED)){
			return "已归还";
		} else if(statusType.equals(BORROW_OVERDUE)){
			return "已超期";
		}
		
		return null;
	}
}
