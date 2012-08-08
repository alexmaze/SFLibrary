package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *  用户注册页面
 * */
public class RegisterView  extends VLayout {

	private static final String DESCRIPTION = "新用户注册";
	private static final String CONTEXT_AREA_WIDTH = "*";
	//UserServiceAsync userService = GWT.create(UserService.class);
	
	//private SmsUser newUser;
	private DynamicForm form;

	public RegisterView() {
		super();

		GWT.log("初始化: RegisterView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		
        form = new DynamicForm();

        //----必填----
        HeaderItem headerMust = new HeaderItem();
        headerMust.setDefaultValue("基本信息（必填）");
        
        TextItem userNameItem = new TextItem();
        userNameItem.setTitle("用户名");
        userNameItem.setName("userName");
        userNameItem.setHint("字母开头的5-16位字母数字下划线组合");

        TextItem userRealNameItem = new TextItem();
        userRealNameItem.setTitle("姓名");
        userRealNameItem.setName("userRealName");
        userRealNameItem.setHint("真实姓名，汉字");
        
        PasswordItem userPasswordItem = new PasswordItem();
        userPasswordItem.setTitle("密码");
        userPasswordItem.setName("userPassword");
        userPasswordItem.setHint("必须为6-16位字符组合");
        
        PasswordItem reUserPasswordItem = new PasswordItem();
        reUserPasswordItem.setTitle("确认密码");
        reUserPasswordItem.setName("reUserPassword");
        reUserPasswordItem.setHint("再输入一遍密码");
        
        RadioGroupItem userGenderItem = new RadioGroupItem();  
        userGenderItem.setTitle("性别");  
        userGenderItem.setName("UserGender");
        userGenderItem.setValueMap("男", "女");
        userGenderItem.setDefaultValue("男");

        TextItem userMobileItem = new TextItem();
        userMobileItem.setTitle("手机");
        userMobileItem.setName("userMobile");

        TextItem userEmailItem = new TextItem();
        userEmailItem.setTitle("E-Mail");
        userEmailItem.setName("userEmail");

        //----可选----
        HeaderItem headerOption = new HeaderItem();
        headerOption.setDefaultValue("其他信息（可选）");
        
        SelectItem userEducationItem = new SelectItem();  
        userEducationItem.setTitle("学历");
        userEducationItem.setType("select");
        userEducationItem.setName("userEducation");
        userEducationItem.setValueMap("小学", "初中", "高中", "专科", "本科", "硕士", "博士");
        userEducationItem.setDefaultToFirstOption(true);
        
        TextItem userOccupationItem = new TextItem();
        userOccupationItem.setTitle("职业");
        userOccupationItem.setName("userOccupation");
        userOccupationItem.setHint("例如：软件开发工程师");

        TextItem userMajorItem = new TextItem();
        userMajorItem.setTitle("研究方向");
        userMajorItem.setName("userMajor");
        userMajorItem.setHint("例如：JAVA网页开发");
        
        TextAreaItem userIntroItem = new TextAreaItem();  
        userIntroItem.setTitle("个人简介");  
        userIntroItem.setName("userIntro");
        userIntroItem.setHint("500字以内");
        userIntroItem.setWidth(400);
        
        form.setFields(headerMust, userNameItem, userRealNameItem, userPasswordItem, reUserPasswordItem, userGenderItem, userMobileItem, userEmailItem, 
        		headerOption, userEducationItem, userOccupationItem, userMajorItem, userIntroItem);
        
		form.setStyleName("alex_myDecoratorPanel");
		form.setMargin(10);
		form.setPadding(10);
        
		HLayout butLayout = new HLayout();
		
        IButton signUpBut = new IButton("立即注册");
        signUpBut.setWidth(100);
        signUpBut.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				if(!inputValidation()) {
//					return;
//				}
//				doRegist();
			}
		});
        IButton backBut = new IButton("返回首页");
        backBut.setWidth(100);
        backBut.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//SMS.get().reLoad();
			}
		});
        
        butLayout.setMembers(signUpBut, backBut);
        butLayout.setMargin(10);
        butLayout.setMembersMargin(10);
        this.setMembers(form, butLayout);
	}
	
//	private void doRegist() {
//		
//		final String orginPW = newUser.getUserPasswd();
//		newUser.setUserPasswd( CipherUtil.generatePassword(orginPW));
//        userService.register(newUser, new AsyncCallback<Boolean>() {
//			
//			@Override
//			public void onSuccess(Boolean result) {
//				
//				if (!result) {
//					SC.say("用户名已存在！");
//					return;
//				}
//				
//				SC.say("注册成功，点击确定自动登陆");
//				
//				AppController.doLogin(newUser.getUserName(), orginPW);
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				
//				SC.say("网络繁忙，请稍后重试！");
//				
//			}
//		});
//		
//	}
//
//	private boolean inputValidation() {
//		
//		this.newUser = new SmsUser();
//		
//		this.newUser.setUserName(form.getValueAsString("userName"));
//		this.newUser.setUserRealName(form.getValueAsString("userRealName"));
//		this.newUser.setUserPasswd(form.getValueAsString("userPassword"));
//		this.newUser.setUserGender(form.getValueAsString("UserGender").charAt(0));
//		this.newUser.setUserMobile(form.getValueAsString("userMobile"));
//		this.newUser.setUserEmail(form.getValueAsString("userEmail"));
//		this.newUser.setUserEducation(form.getValueAsString("userEducation"));
//		this.newUser.setUserOccupation(form.getValueAsString("userOccupation"));
//		this.newUser.setUserMajor(form.getValueAsString("userMajor"));
//		this.newUser.setUserIntro(form.getValueAsString("userIntro"));
//		
//		if (!isUserNameValid(this.newUser.getUserName())) {
//			SC.say("请输入合法用户名");
//			return false;
//		} else if (!isRealNameValid(this.newUser.getUserRealName())) {
//			SC.say("请输入正确姓名");
//			return false;
//		} else if (!isPasswordValid(this.newUser.getUserPasswd())) {
//			SC.say("请输入合法密码");
//			return false;
//		} else if (!this.newUser.getUserPasswd().equals(form.getValueAsString("reUserPassword"))) {
//			SC.say("两次输入密码不一致");
//			return false;
//		} else if (!isPhoneValid(this.newUser.getUserMobile())) {
//			SC.say("请输入正确手机号");
//			return false;
//		} else if (!isEmailValid(this.newUser.getUserEmail())) {
//			SC.say("请输入正确Email地址");
//			return false;
//		}
//		
//		return true;
//	}

	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			RegisterView view = new RegisterView();
			id = view.getID();

//			GWT.log("RegisterView.Factory.create()->view.getID() - " + id, null);
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