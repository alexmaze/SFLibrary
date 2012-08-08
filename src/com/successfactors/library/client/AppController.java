package com.successfactors.library.client;

import com.google.gwt.event.shared.SimpleEventBus;

public class AppController {
	
	private final SimpleEventBus eventBus;
	
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
		// TODO Auto-generated method stub
		
	}

	public static void doSignUp() {
		// TODO Auto-generated method stub
		
	}
}
