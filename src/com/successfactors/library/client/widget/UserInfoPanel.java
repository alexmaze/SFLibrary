package com.successfactors.library.client.widget;

import static com.successfactors.library.shared.FieldVerifier.isEmailValid;
import static com.successfactors.library.shared.FieldVerifier.isNotEmptyValid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLUserDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.client.service.UserServiceAsync;
import com.successfactors.library.client.widget.UploadImageWindow.FinishUploadOperatable;
import com.successfactors.library.shared.CipherUtil;
import com.successfactors.library.shared.model.SLUser;

public class UserInfoPanel extends VLayout implements FinishUploadOperatable {
	
	public interface FinishUserInfoEdit {
		void doRefreshPage();
	}
	
	public UserServiceAsync userService = GWT.create(UserService.class);

	private static final int IMG_HEIGHT = 150;
	private static final int IMG_WIDTH = 120;
	
	private SLUser theUser;
	private Record theRecord;
	private SLUserDS theDataSource;

	private DynamicForm form1;
	
	private IButton submitButton;
	private IButton resetPasswordButton;
	private IButton newButton;
	private IButton uploadPicButton;
	
	private Img avatarSmallUrlItem;
	private String avatarUrl;
	private VLayout imgVLayout;
	
	private FinishUserInfoEdit finishEdit;

	public UserInfoPanel(FinishUserInfoEdit finish) {
		super();
		theUser = new SLUser();
		finishEdit = finish;
		initNewPanel();
		
		this.setBorder("1px solid lightgray");
		this.setMargin(5);
		this.setPadding(30);
		this.setWidth(640);
		this.setBackgroundColor("#FFFFFF");
		
		//this.setAlign(Alignment.CENTER);
		
	}

	public void refresh(ListGridRecord record) {
		theUser = SLUser.parse(record);
		
		initEditPanel();
		avatarUrl = theUser.getAvatarUrl();
		
		theRecord = theUser.getRecord();
		theDataSource.addData(theRecord);

		form1.selectRecord(theRecord);
		form1.fetchData();
	}
	
	private void initEditPanel() {
		HLayout hLayout;
		HLayout buttonLayout;
		
		hLayout = new HLayout();
		
		//HLayout ---------------------------------------------------------------------------------------
		imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		avatarSmallUrlItem = new Img(theUser.getAvatarUrl(), IMG_WIDTH, IMG_HEIGHT);
		avatarSmallUrlItem.setBorder("1px solid lightgray");
		
		uploadPicButton = new IButton("上传头像");
		uploadPicButton.setIcon("actions/plus.png");
		uploadPicButton.setWidth(IMG_WIDTH);
		
		imgVLayout.setMembers(avatarSmallUrlItem, uploadPicButton);
		imgVLayout.setMembersMargin(10);
		
		//Form 1-----------------------------------------------------------------------------------------
		theDataSource = new SLUserDS();
		
		form1 = new DynamicForm();
		form1.setDataSource(theDataSource);

		final TextItem userRealNameItem = new TextItem();
		userRealNameItem.setTitle("姓名");
		userRealNameItem.setName("userName");
		userRealNameItem.setWidth("100%");

		final TextItem userEmailItem = new TextItem();
		userEmailItem.setTitle("E-Mail");
		userEmailItem.setName("userEmail");
		userEmailItem.setWidth("100%");

		final SelectItem userOccupationItem = new SelectItem();
		userOccupationItem.setTitle("用户类型");
		userOccupationItem.setName("userType");
		userOccupationItem.setWidth("100%");
		userOccupationItem.setValueMap(
        		"读者",
        		"管理员"
        		);
		userOccupationItem.setDefaultToFirstOption(true);

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
		userEducationItem.setWidth("100%");
		


		final SelectItem userFloorItem = new SelectItem();
		userFloorItem.setTitle("所在楼层");
		userFloorItem.setType("select");
		userFloorItem.setName("userFloor");
		userFloorItem.setValueMap(
        		"3楼",
        		"10楼"
        		);
		userFloorItem.setDefaultToFirstOption(true);
		userFloorItem.setWidth("100%");

		final SelectItem userPositionItem = new SelectItem();
		userPositionItem.setTitle("所在位置");
		userPositionItem.setType("select");
		userPositionItem.setName("userPosition");
		userPositionItem.setValueMap(
        		"A区",
        		"B区",
        		"C区",
        		"D区",
        		"E区"
        		);
		userPositionItem.setDefaultToFirstOption(true);
		userPositionItem.setWidth("100%");
		
		form1.setColWidths(80, "*");
		form1.setFields(userRealNameItem,
				userEmailItem, userOccupationItem,
				userEducationItem, userFloorItem, userPositionItem);
		form1.setWidth(300);
		form1.setCellPadding(5);
		form1.setHeight(200);
		
		//buttonLayout --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		submitButton = new IButton("提交修改");
		submitButton.setIcon("actions/approve.png");
		resetPasswordButton = new IButton("重置密码");
		resetPasswordButton.setIcon("actions/approve.png");

		submitButton.setWidth(IMG_WIDTH);
		resetPasswordButton.setWidth(IMG_WIDTH);

		buttonLayout.setMembers(resetPasswordButton, submitButton);
		//buttonLayout.setAlign(Alignment.RIGHT);
		buttonLayout.setMembersMargin(20);
		
		hLayout.setMembers(imgVLayout, form1);
		hLayout.setHeight(220);
		
		this.setMembers(hLayout, buttonLayout);
//		this.setMembersMargin(20);
		
		bind();
	}


	private void initNewPanel() {
		
		theUser = new SLUser();
		
		HLayout hLayout;
		HLayout buttonLayout;
		
		hLayout = new HLayout();
		
		//HLayout ---------------------------------------------------------------------------------------
		imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		avatarSmallUrlItem = new Img(theUser.getAvatarUrl(), IMG_WIDTH, IMG_HEIGHT);
		avatarSmallUrlItem.setBorder("1px solid lightgray");
		
		uploadPicButton = new IButton("上传头像");
		uploadPicButton.setIcon("actions/plus.png");
		uploadPicButton.setWidth(IMG_WIDTH);
		
		imgVLayout.setMembers(avatarSmallUrlItem, uploadPicButton);
		imgVLayout.setMembersMargin(10);

		//Form 1-----------------------------------------------------------------------------------------
		theDataSource = new SLUserDS();
		
		form1 = new DynamicForm();
		form1.setDataSource(theDataSource);

		final TextItem userRealNameItem = new TextItem();
		userRealNameItem.setTitle("姓名");
		userRealNameItem.setName("userName");
		userRealNameItem.setWidth("100%");

		final TextItem userEmailItem = new TextItem();
		userEmailItem.setTitle("E-Mail");
		userEmailItem.setName("userEmail");
		userEmailItem.setWidth("100%");

		final SelectItem userOccupationItem = new SelectItem();
		userOccupationItem.setTitle("用户类型");
		userOccupationItem.setName("userType");
		userOccupationItem.setWidth("100%");
		userOccupationItem.setValueMap(
        		"读者",
        		"管理员"
        		);
		userOccupationItem.setDefaultToFirstOption(true);

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
		userEducationItem.setWidth("100%");
		


		final SelectItem userFloorItem = new SelectItem();
		userFloorItem.setTitle("所在楼层");
		userFloorItem.setType("select");
		userFloorItem.setName("userFloor");
		userFloorItem.setValueMap(
        		"3楼",
        		"10楼"
        		);
		userFloorItem.setDefaultToFirstOption(true);
		userFloorItem.setWidth("100%");

		final SelectItem userPositionItem = new SelectItem();
		userPositionItem.setTitle("所在位置");
		userPositionItem.setType("select");
		userPositionItem.setName("userPosition");
		userPositionItem.setValueMap(
        		"A区",
        		"B区",
        		"C区",
        		"D区",
        		"E区"
        		);
		userPositionItem.setDefaultToFirstOption(true);
		userPositionItem.setWidth("100%");
		
		form1.setColWidths(80, "*");
		form1.setFields(userRealNameItem,
				userEmailItem, userOccupationItem,
				userEducationItem, userFloorItem, userPositionItem);
		form1.setWidth(300);
		form1.setCellPadding(5);
		form1.setHeight(200);
		
		//buttonLayout --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		newButton = new IButton("创建新用户");
		newButton.setIcon("actions/approve.png");
		newButton.setWidth(IMG_WIDTH);
		
		buttonLayout.setMembers(newButton);
		buttonLayout.setMembersMargin(20);
		//buttonLayout.setWidth(450);
		//buttonLayout.setAlign(Alignment.CENTER);
		
		hLayout.setMembers(imgVLayout, form1);
		
		hLayout.setHeight(220);
		//hLayout.setWidth(450);
		
		this.setMembers(hLayout, buttonLayout);
		
		bind();
	}

	private void bind() {
		
		if (submitButton != null) {
			submitButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("确认修改？", "您确定要提交修改么？", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								doUpdateUser();
							}
						}
					} );
				}
			});
		}

		if (resetPasswordButton != null) {
			resetPasswordButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("确认修改？", "您确定要重置该用户密码为“123456”么？", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								doResetPasswordUser();
							}
						}
					} );
				}
			});
		}

		if (newButton != null) {
			newButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("确认新增？", "您确定要提交新增用户信息么？", new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if (value) {
								doAddNewUser();
							}
						}
					} );
				}
			});
		}

		if (uploadPicButton != null) {
			uploadPicButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					UploadImageWindow uploadWindow = new UploadImageWindow(getSelf());
					uploadWindow.show();
				}
			});
		}
		
		
	}

	protected UserInfoPanel getSelf() {
		return this;
	}
	
	/**
	 * 增加新用户
	 * */
	public void addUser() {
		initNewPanel();
	}

	private void doUpdateUser() {
		if(!updateUserInfo()) {
			return;
		}
		new RPCCall<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					SC.say("警告", "更新失败，请刷新页面后重试！");
					return;
				}
				if (finishEdit != null) {
					finishEdit.doRefreshPage();
				}
			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				userService.saveUserInfo(theUser, cb);
			}
		}.retry(3);
	}
	private void doAddNewUser() {
		if(!updateUserInfo()) {
			return;
		}
		theUser.setUserPassword(CipherUtil.generatePassword("123456"));
		
		new RPCCall<SLUser>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(SLUser result) {
				if (result == null) {
					SC.say("警告", "添加失败，用户已存在！");
					return;
				}
				if (finishEdit != null) {
					finishEdit.doRefreshPage();
				}
			}

			@Override
			protected void callService(AsyncCallback<SLUser> cb) {
				userService.register(theUser, cb);
			}
		}.retry(3);
	}
	private void doResetPasswordUser() {
		theUser.setUserPassword(CipherUtil.generatePassword("123456"));
		new RPCCall<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					SC.say("警告", "更新失败，请刷新页面后重试！");
					return;
				}
				if (finishEdit != null) {
					finishEdit.doRefreshPage();
				}
			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				userService.saveUserInfo(theUser, cb);
			}
		}.retry(3);
	}
	
	
	private boolean updateUserInfo() {
		
		theUser.setUserName(form1.getValueAsString("userName"));
		this.theUser.setUserEmail(form1.getValueAsString("userEmail"));
		this.theUser.setUserType(form1.getValueAsString("userType"));
		this.theUser.setUserDepartment(form1.getValueAsString("userDepartment"));

		this.theUser.setUserFloor(form1.getValueAsString("userFloor"));
		this.theUser.setUserPosition(form1.getValueAsString("userPosition"));

		this.theUser.setAvatarUrl(avatarUrl);
		
		if (!isNotEmptyValid(this.theUser.getUserName())) {
			SC.say("请输入正确姓名");
			return false;
		} else if (!isEmailValid(this.theUser.getUserEmail())) {
			SC.say("请输入正确Email地址");
			return false;
		}
		
		return true;
	}

	@Override
	public void doAfterFinishUpload(String picName) {
		avatarUrl = picName;
		avatarSmallUrlItem.setSrc(avatarUrl);
	}

}
