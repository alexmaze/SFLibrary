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
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.shared.CipherUtil;
import com.successfactors.library.shared.model.SLUser;

public class ReaderInfoView extends VLayout {
	
	private static final String DESCRIPTION = "我的信息";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private DynamicForm form;
	private SLUser userInfo;
	
	public ReaderInfoView() {
		super();

		GWT.log("初始化: ReaderInfoView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		form = new DynamicForm();
		
		// ----基本信息----
		HeaderItem headerMust = new HeaderItem();
		headerMust.setDefaultValue("读者基本信息");

		final TextItem userRealNameItem = new TextItem();
		userRealNameItem.setTitle("姓名");
		userRealNameItem.setName("userRealName");

		final TextItem userEmailItem = new TextItem();
		userEmailItem.setTitle("E-Mail");
		userEmailItem.setName("userEmail");

		final TextItem userOccupationItem = new TextItem();
		userOccupationItem.setTitle("用户类型");
		userOccupationItem.setName("userType");

		final SelectItem userEducationItem = new SelectItem();
		userEducationItem.setTitle("所在团队");
		userEducationItem.setType("select");
		userEducationItem.setName("userDepartment");
		userEducationItem.setValueMap(
        		"Varpay",
        		"Recruiting", 
        		"Compensation",
        		"API",
        		"Platform", 
        		"Employee Center",
        		"Gole Management",
        		"Performance",
        		"Special Project",
        		"Rules Engine",
        		"Mobile",
        		"Automation",
        		"360",
        		"IT",
        		"Other"
        		);
		userEducationItem.setDefaultToFirstOption(true);

		form.setFields(headerMust, userRealNameItem,
				userEmailItem, userOccupationItem,
				userEducationItem);

		form.setStyleName("alex_myDecoratorPanel");
		form.setMargin(10);
		form.setPadding(10);
		
		userRealNameItem.setCanEdit(false);
		userEmailItem.setCanEdit(false);
		userEducationItem.setCanEdit(false);
		userOccupationItem.setCanEdit(false);
		
		// 按钮
		final IButton butEdit = new IButton("编辑信息");
		final IButton butSubmit = new IButton("提交修改");
		IButton butChangePW = new IButton("修改密码");
		
		// 按钮详细
		butSubmit.setVisible(false);
		
		butEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				butSubmit.setVisible(true);
				butEdit.setVisible(false);
				
				userRealNameItem.setCanEdit(true);
				userEmailItem.setCanEdit(true);
				userEducationItem.setCanEdit(true);
				userOccupationItem.setCanEdit(false);
			}
		});
		butSubmit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (!inputValidation()) {
					return;
				}
				
		        userService.saveUserInfo(userInfo, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						
						if (!result) {
							SC.say("保存失败，请刷新后重试！");
							return;
						}
						
						SC.say("更新成功");

						butSubmit.setVisible(false);
						butEdit.setVisible(true);
						userRealNameItem.setCanEdit(false);
						userEmailItem.setCanEdit(false);
						userEducationItem.setCanEdit(false);
						userOccupationItem.setCanEdit(false);
						
						SFLibrary.get().setNowUser(userInfo);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						SC.say("网络繁忙，请稍后重试！");
					}
				});
				
			}
		});
		butChangePW.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showChangePWWindow();
			}
		});
		
		
		HLayout butLayout = new HLayout();
		butLayout.setMembers(butEdit, butSubmit, butChangePW);
		butLayout.setMargin(10);
		butLayout.setMembersMargin(10);
		
		this.setMembers(form, butLayout);

		fillDatas();
		
	}
	
	private void fillDatas() {
		userInfo = SFLibrary.get().getNowUser();

		form.setValue("userRealName", userInfo.getUserName());
		form.setValue("userEmail", userInfo.getUserEmail());
		form.setValue("userType", userInfo.getUserType());
		form.setValue("userDepartment", userInfo.getUserDepartment());

	}
	
	private boolean inputValidation() {
		
		this.userInfo.setUserName(form.getValueAsString("userRealName"));
		this.userInfo.setUserEmail(form.getValueAsString("userEmail"));
		this.userInfo.setUserType(form.getValueAsString("userType"));
		this.userInfo.setUserDepartment(form.getValueAsString("userDepartment"));
		
		if (!isNotEmptyValid(this.userInfo.getUserName())) {
			SC.say("请输入正确姓名");
			return false;
		} else if (!isEmailValid(this.userInfo.getUserEmail())) {
			SC.say("请输入正确Email地址");
			return false;
		}
		
		return true;
	}
	
	private void showChangePWWindow() {

		final Window window = new Window();
        window.setAutoSize(true);
        window.setTitle("修改密码");
        window.setCanDragReposition(true);
        window.setCanDragResize(false);
        window.setAutoCenter(true);
        window.setSize("300px", "175px");
        
        final DynamicForm changePWForm = new DynamicForm();

        PasswordItem oldPwItem = new PasswordItem();
		oldPwItem.setTitle("原密码");
		oldPwItem.setName("oldPw");

		PasswordItem newPwItem = new PasswordItem();
		newPwItem.setTitle("新密码");
		newPwItem.setName("newPw");

		PasswordItem reNewPwItem = new PasswordItem();
		reNewPwItem.setTitle("确认密码");
		reNewPwItem.setName("reNewPw");

		ButtonItem butConform = new ButtonItem();
		butConform.setTitle("提交");
		butConform.setWidth(60);
		butConform.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				
				String strOldPw = changePWForm.getValueAsString("oldPw");
				String strNewPw = changePWForm.getValueAsString("newPw");
				String strReNewPw = changePWForm.getValueAsString("reNewPw");
				
				if (!userInfo.getUserPassword().equals(CipherUtil.generatePassword(strOldPw))) {
					SC.say("原密码输入不正确！");
					return;
				} else if (!isPasswordValid(strNewPw)) {
					SC.say("新密码应为6-16位字符组合!！");
					return;
				} else if (!strNewPw.equals(strReNewPw)) {
					SC.say("两次密码不一致！");
					return;
				}
				
				userInfo = SFLibrary.get().getNowUser();
				userInfo.setUserPassword(CipherUtil.generatePassword(strNewPw));
				
		        userService.saveUserInfo(userInfo, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						
						if (!result) {
							SC.say("保存失败，请刷新后重试！");
							return;
						}
						
						SC.say("更新成功");
						SFLibrary.get().setNowUser(userInfo);
						window.hide();
						window.destroy();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						SC.say("网络繁忙，请稍后重试！");
					}
				});
				
			}
		});
		
		
		changePWForm.setFields(oldPwItem, newPwItem, reNewPwItem, butConform);
		changePWForm.setMargin(20);
        
		window.addItem(changePWForm);
		
        window.show();
		
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderInfoView view = new ReaderInfoView();
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
