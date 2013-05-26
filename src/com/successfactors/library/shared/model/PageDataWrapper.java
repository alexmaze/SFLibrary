package com.successfactors.library.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;
import com.successfactors.library.shared.model.DataWrapper.CallStatusEnum;

public class PageDataWrapper<T> implements IsSerializable {

	protected CallStatusEnum callStatus;
	protected RestCallErrorCode errorCode;
	protected T data;
	protected String sessionKey;
	
	private int numberPerPage;
	private int pageNumber;
	
	private int totalItemNumber;
	private int totalPageNumber;
	
	public PageDataWrapper() {
		callStatus = CallStatusEnum.SUCCEED;
		errorCode = RestCallErrorCode.no_error;
	}
	
	public CallStatusEnum getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(CallStatusEnum callStatus) {
		this.callStatus = callStatus;
	}

	public RestCallErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(RestCallErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public int getNumberPerPage() {
		return numberPerPage;
	}
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotalPageNumber() {
		return totalPageNumber;
	}
	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}
	public int getTotalItemNumber() {
		return totalItemNumber;
	}
	public void setTotalItemNumber(int totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	
	@Override
	public String toString() {
		return this.callStatus + "\n" +
				this.errorCode+ "\n" +
				"Page Num:" + this.pageNumber + "\n" +
				"Total Page Num:" + this.totalPageNumber + "\n" +
				"Item Num per Page:" + this.numberPerPage + "\n" +
				"Total Item Num:" + this.totalItemNumber;
	}
	
}
