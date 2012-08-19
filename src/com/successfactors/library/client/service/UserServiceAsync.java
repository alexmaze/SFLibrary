package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

}
