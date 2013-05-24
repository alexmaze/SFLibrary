package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLBorrowDS;

@SuppressWarnings("serial")
public class BorrowPage implements Serializable {
	
	private ArrayList<SLBorrow> theBorrows;
	private int itemsNumPerPage;
	private int pageNum;
	private int totalPageNum;
	
	public BorrowPage() {
		
	}
	
	public BorrowPage(int itemsNumPerPage, int pageNum) {
		this.itemsNumPerPage = itemsNumPerPage;
		this.pageNum = pageNum;
	}
	
	public ArrayList<SLBorrow> getTheBorrows() {
		return theBorrows;
	}

	public void setTheBorrows(ArrayList<SLBorrow> theBorrows) {
		this.theBorrows = theBorrows;
	}
	public int getItemsNumPerPage() {
		return itemsNumPerPage;
	}

	public void setItemsNumPerPage(int itemsNumPerPage) {
		this.itemsNumPerPage = itemsNumPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	
	public SLBorrowDS toDataSource() {
		SLBorrowDS theDs = new SLBorrowDS();
		for (SLBorrow borrow : theBorrows) {
			theDs.addData(borrow.toRecord());
		}
		return theDs;
	}
	
}
