package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLBookDS;

@SuppressWarnings("serial")
public class BookPage implements Serializable {
	
	private ArrayList<SLBook> theBooks;
	private int itemsNumPerPage;
	private int pageNum;
	private int totalPageNum;
	
	public BookPage() {
		
	}
	
	public BookPage(int itemsNumPerPage, int pageNum) {
		this.itemsNumPerPage = itemsNumPerPage;
		this.pageNum = pageNum;
	}
	
	public ArrayList<SLBook> getTheBooks() {
		return theBooks;
	}
	public void setTheBooks(ArrayList<SLBook> theBooks) {
		this.theBooks = theBooks;
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

	public SLBookDS toDataSource() {
		SLBookDS theDs = new SLBookDS();
		for (SLBook book : theBooks) {
			theDs.addData(book.toRecord());
		}
		return theDs;
	}
	
}
