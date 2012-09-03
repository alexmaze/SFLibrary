package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.shared.model.SLUser;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	@Override
	public SLUser login(String strEmail, String strPassword) {
		// TODO Auto-generated method stub
		
		SLUser test = new SLUser();
		test.setUserName("AlexYan");
		test.setUserEmail(strEmail);
		test.setUserPassword(strPassword);
		test.setUserType("管理员");
		test.setUserDepartment("Rules Engine");
		
		return test;
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public SLUser register(SLUser newUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveUserInfo(SLUser slUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SLUser getUserById(String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUserById(String userEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<SLUser> getUserList(int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
