package com.successfactors.library.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginSucceedEventHandler extends EventHandler {
	void onLoginSucceed(LoginSucceedEvent event);
}
