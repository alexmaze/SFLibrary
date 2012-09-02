package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.successfactors.library.client.datasource.SLOrderDS;

@SuppressWarnings("serial")
public class OrderPage implements Serializable {
	
	private ArrayList<SLOrder> theOrders;
	private int startNum;
	private int endNum;
	private int totalNum;
	
	public OrderPage() {
		
	}
	
	public OrderPage(int start, int end) {
		startNum = start;
		endNum = end;
	}
	
	public ArrayList<SLOrder> getTheOrders() {
		return theOrders;
	}

	public void setTheOrders(ArrayList<SLOrder> theOrders) {
		this.theOrders = theOrders;
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
	
	public SLOrderDS getDataSource() {
		SLOrderDS theDs = new SLOrderDS();
		for (SLOrder order : theOrders) {
			theDs.addData(order.getRecord());
		}
		return theDs;
	}
	
}
