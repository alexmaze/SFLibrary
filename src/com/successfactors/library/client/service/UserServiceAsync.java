package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.SLUser;
import com.successfactors.library.shared.model.UserPage;

public interface UserServiceAsync {

	void helloServer(String strHello, AsyncCallback<String> callback);

	void login(String strName, String strPassword, AsyncCallback<SLUser> callback);

	void logout(AsyncCallback<Boolean> callback);

	void deleteUserByEmail(String userEmail, AsyncCallback<Boolean> callback);

	void getUserByEmail(String userEmail, AsyncCallback<SLUser> callback);

	void register(SLUser newUser, AsyncCallback<SLUser> callback);

	void saveUserInfo(SLUser slUser, AsyncCallback<Boolean> callback);

	void searchUserList(String userName, String userEmail, String userType,
			String userDepartment, String usetFloor, String usetPosition,
			int numberPerPage, int pageNumber, AsyncCallback<UserPage> callback);

}
