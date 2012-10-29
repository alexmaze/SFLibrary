package com.successfactors.library.shared;

/**
 * 
 * @author haimingli
 * 
 */
public enum OrderStatusType {

	/** Just ordered the book, but has not borrowed yet. 排队中 */
	ORDER_INQUEUE,

	/** The order is canceled. 已取消 */
	ORDER_CANCElED,

	/** The book has been borrowed successfully. 已借到 */
	ORDER_BORROWED,

	/**
	 * 历史记录
	 * */
	HISTORY,

	/**
	 * 当前记录
	 * */
	NOW;

	public static OrderStatusType parse(String strType) {

		if (strType.equalsIgnoreCase("排队中")) {
			return OrderStatusType.ORDER_INQUEUE;
		} else if (strType.equalsIgnoreCase("已取消")) {
			return OrderStatusType.ORDER_CANCElED;
		} else if (strType.equalsIgnoreCase("已借到")) {
			return OrderStatusType.ORDER_BORROWED;
		} else if (strType.equalsIgnoreCase("历史记录")) {
			return OrderStatusType.HISTORY;
		} else if (strType.equalsIgnoreCase("当前记录")) {
			return OrderStatusType.NOW;
		}

		return null;
	}

}
