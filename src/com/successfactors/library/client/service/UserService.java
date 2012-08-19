package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {

	// 测试服务联通性
	String helloServer(String strHello);
		
}
