package com.successfactors.library.client.view;

import static com.successfactors.library.client.SFLibrary.userService;
import static com.successfactors.library.shared.FieldVerifier.isEmailValid;
import static com.successfactors.library.shared.FieldVerifier.isNotEmptyValid;
import static com.successfactors.library.shared.FieldVerifier.isPasswordValid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.AppController;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.event.LoginSucceedEvent;
import com.successfactors.library.client.helper.CipherUtil;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLUser;

/**
 *  用户注册页面
 * */
public class RegisterView  extends VLayout {

	private static final String DESCRIPTION = "新用户注册";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	private SLUser newUser;
	private DynamicForm form;

	public RegisterView() {
		super();

		GWT.log("初始化: RegisterView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
        form = new DynamicForm();

        //----必填----
        HeaderItem headerMust = new HeaderItem();
        headerMust.setDefaultValue("基本信息");
        
        TextItem userRealNameItem = new TextItem();
        userRealNameItem.setTitle("姓名");
        userRealNameItem.setName("userRealName");
        userRealNameItem.setHint("");

        TextItem userEmailItem = new TextItem();
        userEmailItem.setTitle("E-Mail");
        userEmailItem.setName("userEmail");
        userEmailItem.setHint("用作登录");
        
        PasswordItem userPasswordItem = new PasswordItem();
        userPasswordItem.setTitle("密码");
        userPasswordItem.setName("userPassword");
        userPasswordItem.setHint("必须为6-16位字符组合");
        
        PasswordItem reUserPasswordItem = new PasswordItem();
        reUserPasswordItem.setTitle("确认密码");
        reUserPasswordItem.setName("reUserPassword");
        reUserPasswordItem.setHint("再输入一遍密码");
        
        // TODO 团队列表
        SelectItem userTeamItem = new SelectItem();  
        userTeamItem.setTitle("所属团队");
        userTeamItem.setType("select");
        userTeamItem.setName("userDepartment");
        userTeamItem.setValueMap("EC", "PM", "..");
        userTeamItem.setDefaultToFirstOption(true);
        
        form.setFields(headerMust, userRealNameItem, userEmailItem, userPasswordItem, reUserPasswordItem, userTeamItem);
        
		form.setStyleName("alex_myDecoratorPanel");
		form.setMargin(10);
		form.setPadding(10);
        
		HLayout butLayout = new HLayout();
		
        IButton signUpBut = new IButton("立即注册");
        signUpBut.setWidth(100);
        signUpBut.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(!inputValidation()) {
					return;
				}
				doRegist();
			}
		});
        IButton backBut = new IButton("返回首页");
        backBut.setWidth(100);
        backBut.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SFLibrary.get().reLoad();
			}
		});
        
        butLayout.setMembers(signUpBut, backBut);
        butLayout.setMargin(10);
        butLayout.setMembersMargin(10);
        this.setMembers(form, butLayout);
	}
	
	private void doRegist() {
		
		final String orginPW = newUser.getUserPassword();
		newUser.setUserPassword( CipherUtil.generatePassword(orginPW));
		
		new RPCCall<SLUser>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(SLUser result) {
				if (result == null) {
					SC.say("注册失败，请稍候重试！");
					return;
				}
				SC.say("注册成功，点击确定自动登陆");
				
				AppController.doLogin(newUser.getUserEmail(), orginPW);
				
				SFLibrary.get().getEventBus().fireEvent(new LoginSucceedEvent(result));
			}

			@Override
			protected void callService(AsyncCallback<SLUser> cb) {
				userService.register(newUser, cb);
			}
			
		}.retry(3);
		
	}

	private boolean inputValidation() {
		
		this.newUser = new SLUser();
		
		this.newUser.setUserName(form.getValueAsString("userRealName"));
		this.newUser.setUserPassword(form.getValueAsString("userPassword"));
		this.newUser.setUserEmail(form.getValueAsString("userEmail"));
		this.newUser.setUserDepartment(form.getValueAsString("userDepartment"));
		this.newUser.setUserType("读者");
		
		if (!isNotEmptyValid(this.newUser.getUserName())) {
			SC.say("请输入用户姓名");
			return false;
		} else if (!isPasswordValid(this.newUser.getUserPassword())) {
			SC.say("请输入合法密码");
			return false;
		} else if (!this.newUser.getUserPassword().equals(form.getValueAsString("reUserPassword"))) {
			SC.say("两次输入密码不一致");
			return false;
		} else if (!isEmailValid(this.newUser.getUserEmail())) {
			SC.say("请输入正确Email地址");
			return false;
		}
		
		return true;
	}

	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			RegisterView view = new RegisterView();
			id = view.getID();
			return view;
		}

		public String getID() {
			return id;
		}

		public String getDescription() {
			return DESCRIPTION;
		}
	}
}