package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

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
	private Map<String, Long> borrowNumberEachTeam;
	private Map<String, Long> OrderNumberEachTeam;
	
	// 每月活跃度统计（近一年）
	private Map<Integer, Long> activinessEachMonth;

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

	public Map<Integer, Long> getActivinessEachMonth() {
		return activinessEachMonth;
	}

	public void setActivinessEachMonth(Map<Integer, Long> activinessEachMonth) {
		this.activinessEachMonth = activinessEachMonth;
	}
	public Map<String, Long> getBorrowNumberEachTeam() {
		return borrowNumberEachTeam;
	}

	public void setBorrowNumberEachTeam(Map<String, Long> borrowNumberEachTeam) {
		this.borrowNumberEachTeam = borrowNumberEachTeam;
	}

	public Map<String, Long> getOrderNumberEachTeam() {
		return OrderNumberEachTeam;
	}

	public void setOrderNumberEachTeam(Map<String, Long> orderNumberEachTeam) {
		OrderNumberEachTeam = orderNumberEachTeam;
	}
	
}
