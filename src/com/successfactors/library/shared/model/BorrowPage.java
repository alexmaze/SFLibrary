package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLBorrowDS;

@SuppressWarnings("serial")
public class BorrowPage implements Serializable {
	
	private ArrayList<SLBorrow> theBorrows;
	private int startNum;
	private int endNum;
	private int totalNum;
	
	public BorrowPage() {
		
	}
	
	public BorrowPage(int start, int end) {
		startNum = start;
		endNum = end;
	}
	
	public ArrayList<SLBorrow> getTheBorrows() {
		return theBorrows;
	}

	public void setTheBorrows(ArrayList<SLBorrow> theBorrows) {
		this.theBorrows = theBorrows;
	}

	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	public SLBorrowDS getDataSource() {
		SLBorrowDS theDs = new SLBorrowDS();
		for (SLBorrow borrow : theBorrows) {
			theDs.addData(borrow.getRecord());
		}
		return theDs;
	}
	
}
