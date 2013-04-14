package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLUserDS;

public class UserPage implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3002659622146946134L;

	private ArrayList<SLUser> theUsers;
	private int itemsNumPerPage;
	private int pageNum;
	private int totalPageNum;
	
	public UserPage() {
		
	}
	
	public UserPage(int itemsNumPerPage, int pageNum) {
		this.itemsNumPerPage = itemsNumPerPage;
		this.pageNum = pageNum;
	}

	public ArrayList<SLUser> getTheUsers() {
		return theUsers;
	}

	public void setTheUsers(ArrayList<SLUser> theUsers) {
		this.theUsers = theUsers;
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
	
	public SLUserDS getDataSource() {
		SLUserDS theDs = new SLUserDS();
		for (SLUser user : theUsers) {
			theDs.addData(user.getRecord());
		}
		return theDs;
	}
	
}
