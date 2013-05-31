package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class SLStatisticalData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -788750740608873088L;
	
	private Date timestamp;
	
	// 图书馆整体数据
	private long totalBookNumber;
	private long inStoreBookNumber;
	private long avaliableBookNumber;
	private long remainingRecommendNumber;

	private long nowOverdueBorrowNumber;
	private long totalLostBookNumber;

	private double nowOverdueRate;
	private double totalLostBookRate;
	
	
	// 各部门借阅、预订统计（近一年）
	private HashMap<String, Long> borrowNumberEachTeam;
	private HashMap<String, Long> OrderNumberEachTeam;

	// 每月借阅、预订统计（近一年）
	private HashMap<Integer, Long> borrowNumberEachMonth;
	private HashMap<Integer, Long> orderNumberEachMonth;

	public SLStatisticalData() {
		timestamp = new Date();
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getTotalBookNumber() {
		return totalBookNumber;
	}

	public void setTotalBookNumber(long totalBookNumber) {
		this.totalBookNumber = totalBookNumber;
	}

	public long getInStoreBookNumber() {
		return inStoreBookNumber;
	}

	public void setInStoreBookNumber(long inStoreBookNumber) {
		this.inStoreBookNumber = inStoreBookNumber;
	}

	public long getAvaliableBookNumber() {
		return avaliableBookNumber;
	}

	public void setAvaliableBookNumber(long avaliableBookNumber) {
		this.avaliableBookNumber = avaliableBookNumber;
	}

	public long getRemainingRecommendNumber() {
		return remainingRecommendNumber;
	}

	public void setRemainingRecommendNumber(long remainingRecommendNumber) {
		this.remainingRecommendNumber = remainingRecommendNumber;
	}

	public long getNowOverdueBorrowNumber() {
		return nowOverdueBorrowNumber;
	}

	public void setNowOverdueBorrowNumber(long nowOverdueBorrowNumber) {
		this.nowOverdueBorrowNumber = nowOverdueBorrowNumber;
	}

	public long getTotalLostBookNumber() {
		return totalLostBookNumber;
	}

	public void setTotalLostBookNumber(long totalLostBookNumber) {
		this.totalLostBookNumber = totalLostBookNumber;
	}

	public double getNowOverdueRate() {
		return nowOverdueRate;
	}

	public void setNowOverdueRate(double nowOverdueRate) {
		this.nowOverdueRate = nowOverdueRate;
	}

	public double getTotalLostBookRate() {
		return totalLostBookRate;
	}

	public void setTotalLostBookRate(double totalLostBookRate) {
		this.totalLostBookRate = totalLostBookRate;
	}
	
	public HashMap<Integer, Long> getBorrowNumberEachMonth() {
		return borrowNumberEachMonth;
	}

	public void setBorrowNumberEachMonth(
			HashMap<Integer, Long> borrowNumberEachMonth) {
		this.borrowNumberEachMonth = borrowNumberEachMonth;
	}

	public HashMap<Integer, Long> getOrderNumberEachMonth() {
		return orderNumberEachMonth;
	}

	public void setOrderNumberEachMonth(HashMap<Integer, Long> orderNumberEachMonth) {
		this.orderNumberEachMonth = orderNumberEachMonth;
	}
	public HashMap<String, Long> getBorrowNumberEachTeam() {
		return borrowNumberEachTeam;
	}

	public void setBorrowNumberEachTeam(HashMap<String, Long> borrowNumberEachTeam) {
		this.borrowNumberEachTeam = borrowNumberEachTeam;
	}

	public HashMap<String, Long> getOrderNumberEachTeam() {
		return OrderNumberEachTeam;
	}

	public void setOrderNumberEachTeam(HashMap<String, Long> orderNumberEachTeam) {
		OrderNumberEachTeam = orderNumberEachTeam;
	}
	
}
