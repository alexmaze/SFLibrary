package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BookBorrowOrderListInfo implements Serializable {
	
	private ArrayList<SLBorrow> theBorrows;
	private ArrayList<SLOrder> theOrders;
	
	public BookBorrowOrderListInfo() {
		
	}
	
	public ArrayList<SLBorrow> getTheBorrows() {
		return theBorrows;
	}

	public void setTheBorrows(ArrayList<SLBorrow> theBorrows) {
		this.theBorrows = theBorrows;
	}

	public ArrayList<SLOrder> getTheOrders() {
		return theOrders;
	}

	public void setTheOrders(ArrayList<SLOrder> theOrders) {
		this.theOrders = theOrders;
	}
	
}
