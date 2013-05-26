package com.successfactors.library.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.successfactors.library.shared.RestCallInfo.RestCallErrorCode;

public class DataWrapper<T> implements IsSerializable {
	
	protected CallStatusEnum callStatus;
	protected RestCallErrorCode errorCode;
	protected T data;
	protected String sessionKey;
	
	public DataWrapper() {
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

	public enum CallStatusEnum {
		SUCCEED,
		FAILED,;
	}
	
	@Override
	public String toString() {
		return this.callStatus + "\n" +
				this.errorCode;
	}
	
}
