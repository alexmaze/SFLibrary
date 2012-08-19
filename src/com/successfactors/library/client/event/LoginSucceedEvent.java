package com.successfactors.library.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.successfactors.library.shared.model.SLUser;

public class LoginSucceedEvent extends GwtEvent<LoginSucceedEventHandler> {
	
	public static Type<LoginSucceedEventHandler> TYPE = new Type<LoginSucceedEventHandler>();
	
	private SLUser userInfo;
	
	public LoginSucceedEvent(SLUser info) {
		userInfo = info;
	}
	
	public SLUser getUserInfo() {
		return userInfo;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginSucceedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginSucceedEventHandler handler) {
		handler.onLoginSucceed(this);
	}

}
