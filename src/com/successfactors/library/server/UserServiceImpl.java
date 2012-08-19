package com.successfactors.library.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.UserService;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

}
