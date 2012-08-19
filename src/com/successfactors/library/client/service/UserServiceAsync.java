package com.successfactors.library.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.SLUser;

public interface UserServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void login(String strName, String strPassword, AsyncCallback<SLUser> callback);

	void logout(AsyncCallback<Boolean> callback);

	void deleteUserById(String userEmail, AsyncCallback<Boolean> callback);

	void getUserById(String userEmail, AsyncCallback<SLUser> callback);

	void register(SLUser newUser, AsyncCallback<Boolean> callback);

	void saveUserInfo(SLUser slUser, AsyncCallback<Boolean> callback);

	void getUserList(int iStart, int iEnd,
			AsyncCallback<ArrayList<SLUser>> callback);

}
