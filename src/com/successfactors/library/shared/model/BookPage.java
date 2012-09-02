package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLBookDS;

@SuppressWarnings("serial")
public class BookPage implements Serializable {
	
	private ArrayList<SLBook> theBooks;
	private int startNum;
	private int endNum;
	private int totalNum;
	
	public BookPage(int start, int end) {
		startNum = start;
		endNum = end;
	}
	
	public ArrayList<SLBook> getTheBooks() {
		return theBooks;
	}
	public void setTheBooks(ArrayList<SLBook> theBooks) {
		this.theBooks = theBooks;
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
	
	public SLBookDS getDataSource() {
		SLBookDS theDs = new SLBookDS();
		for (SLBook book : theBooks) {
			theDs.addData(book.getRecord());
		}
		return theDs;
	}
	
}
