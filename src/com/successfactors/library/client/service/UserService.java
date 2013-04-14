package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.SLUser;
import com.successfactors.library.shared.model.UserPage;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {

	// 测试服务联通
	String helloServer(String strHello);
	
	// 用户登录
	SLUser login(String strEmail, String strPassword);
	
	// 用户注销
	boolean logout();
	
	// 新用户注册
	SLUser register(SLUser newUser);
	
	// 保存用户信息
	boolean saveUserInfo(SLUser slUser);
	
	// 通过Email获取用户
	SLUser getUserByEmail(String userEmail);
	
	// 通过Email删除用户
	boolean deleteUserByEmail(String userEmail);

	// 搜索用户，filter都可为Null
	UserPage searchUserList(
			String userName,
			String userEmail,
			String userType,
			String userDepartment,
			String usetFloor,
			String usetPosition,
			int numberPerPage,
			int pageNumber);
}
