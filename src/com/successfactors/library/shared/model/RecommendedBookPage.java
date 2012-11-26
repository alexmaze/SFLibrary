package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLBookDS;

@SuppressWarnings("serial")
public class RecommendedBookPage implements Serializable {
	
	private ArrayList<SLRecommendedBook> theBooks;
	private int itemsNumPerPage;
	private int pageNum;
	private int totalPageNum;
	
	public RecommendedBookPage() {
		
	}
	
	public RecommendedBookPage(int itemsNumPerPage, int pageNum) {
		this.itemsNumPerPage = itemsNumPerPage;
		this.pageNum = pageNum;
	}
	
	public ArrayList<SLRecommendedBook> getTheBooks() {
		return theBooks;
	}
	public void setTheBooks(ArrayList<SLRecommendedBook> theBooks) {
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

	public SLBookDS getDataSource() {
		SLBookDS theDs = new SLBookDS();
		for (SLRecommendedBook book : theBooks) {
			theDs.addData(book.getRecord());
		}
		return theDs;
	}
	
}
