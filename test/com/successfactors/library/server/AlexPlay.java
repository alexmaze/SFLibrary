package com.successfactors.library.server;

import com.successfactors.library.server.dao.SLBorrowDao;

public class AlexPlay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SLBorrowDao dao = new SLBorrowDao();
		System.out.println((dao.isUserBookBorrowed("ayan@successfactors.com", "9787500679240"))?"true":"false");
	}

}
