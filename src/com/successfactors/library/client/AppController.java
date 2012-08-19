package com.successfactors.library.client;

import static com.successfactors.library.client.SFLibrary.userService;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.successfactors.library.client.event.LoginSucceedEvent;
import com.successfactors.library.client.event.LoginSucceedEventHandler;
import com.successfactors.library.client.helper.MyToolsInClient;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.FieldVerifier;
import com.successfactors.library.shared.model.SLUser;

public class AppController {
	
	public AppController() {
		bind();
	}

	private void bind() {
		SFLibrary.get().getEventBus().addHandler(LoginSucceedEvent.TYPE, new LoginSucceedEventHandler() {
			@Override
			public void onLoginSucceed(LoginSucceedEvent event) {
				// 处理登录成功之后的事情
				SFLibrary.get().setNowUser(event.getUserInfo());
				SFLibrary.get().showNavigationPane();
				
				SFLibrary.get().getStatusInfoBar().setLeftInfo(MyToolsInClient.getTimePeriod(new Date())+"好： "+ event.getUserInfo().getUserName() );
				SFLibrary.get().getStatusInfoBar().setRightInfo("注销");
				SFLibrary.get().getStatusInfoBar().addRightClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						//  退出登陆
						doLogout();
					}
				});
			}
		});
	}

	public static void doLogin(final String strName, final String strPwd) {
		// 初步验证用户名密码
		if (!FieldVerifier.isUserNameValid(strName)
				|| !FieldVerifier.isPasswordValid(strPwd)) {
			SC.say("用户名或密码错误！");
			return;
		}
		// 联系服务器进行登录验证
		new RPCCall<SLUser>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(SLUser result) {
				if (result == null) {
					SC.say("用户名或密码错误！");
					return;
				}
				SFLibrary.get().getEventBus().fireEvent(new LoginSucceedEvent(result));
			}

			@Override
			protected void callService(AsyncCallback<SLUser> cb) {
				userService.login(strName, strPwd, cb);
			}
			
		}.retry(3);
	}

	private static void doLogout() {
		new RPCCall<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(Boolean result) {
				SFLibrary.get().reLoad();
			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				userService.logout(cb);
			}
			
		}.retry(3);
	}

	public static void doSignUp() {
		SFLibrary.get().showRegisterView();
	}
	
}
