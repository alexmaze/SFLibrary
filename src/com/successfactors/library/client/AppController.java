package com.successfactors.library.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.client.service.UserServiceAsync;
import com.successfactors.library.shared.FieldVerifier;

public class AppController {
	
	private final SimpleEventBus eventBus;
	final static UserServiceAsync userService = GWT.create(UserService.class);
	
	public AppController(SimpleEventBus eBus) {
		eventBus = eBus;
		bind();
	}

	/**
	 * 绑定事件
	 * */
	private void bind() {
//		eventBus.addHandler(LoginSucceedEvent.TYPE, new LoginSucceedEventHandler() {
//			@Override
//			public void onLoginSucceed(LoginSucceedEvent event) {
//				// 处理登录成功之后的事情
//				SMS.get().setNowUser(event.getUserInfo());
//				SMS.get().showNavigationPane();
//				
//				SMS.get().getUserLoginInfoBar().setLeftInfo(MyToolsInClient.getTimePeriod(new Date())+"好： "+ event.getUserInfo().getUserRealName() );
//				SMS.get().getUserLoginInfoBar().setRightInfo("注销");
//				SMS.get().getUserLoginInfoBar().addRightClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						//  退出登陆
//						doLogout();
//					}
//				});
//				SMS.get().getUserLoginInfoBar().setCenterInfo("选择工作项目");
//				SMS.get().getUserLoginInfoBar().addCenterClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						doSelectWorkingProject();
//					}
//				});
//			}
//		});
	}

	public static void doLogin(String strName, String strPwd) {
		// 初步验证用户名密码
		if (!FieldVerifier.isUserNameValid(strName)
				|| !FieldVerifier.isPasswordValid(strPwd)) {
			SC.say("用户名或密码错误！");
			return;
		}
		// TODO 联系服务器进行登录验证
		new RPCCall<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say("sorry!");
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				SC.say(result);
			}

			@Override
			protected void callService(AsyncCallback<String> cb) {
				// TODO Auto-generated method stub
				userService.helloServer("Alex", cb);
			}
			
		}.retry(3);
	}

	public static void doSignUp() {
		// TODO Auto-generated method stub
		
	}
}
