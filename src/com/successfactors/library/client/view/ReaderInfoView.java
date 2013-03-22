package com.successfactors.library.client.view;

import static com.successfactors.library.client.SFLibrary.userService;
import static com.successfactors.library.shared.FieldVerifier.isEmailValid;
import static com.successfactors.library.shared.FieldVerifier.isNotEmptyValid;
import static com.successfactors.library.shared.FieldVerifier.isPasswordValid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.widget.UploadImageWindow;
import com.successfactors.library.client.widget.UploadImageWindow.FinishUploadOperatable;
import com.successfactors.library.shared.CipherUtil;
import com.successfactors.library.shared.model.SLUser;

public class ReaderInfoView extends VLayout implements UploadImageWindow.FinishUploadOperatable {
	
	private static final String DESCRIPTION = "我的信息";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private static final int IMG_HEIGHT = 150;
	private static final int IMG_WIDTH = 120;

	private DynamicForm form;
	private SLUser userInfo;
	
	Img avatarUrlItem;
	String avatarUrl;
	
	public ReaderInfoView() {
		super();

		GWT.log("初始化: ReaderInfoView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		

		HLayout hLayout = new HLayout();
		
		//HLayout ---------------------------------------------------------------------------------------
		
		VLayout imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		imgVLayout.setHeight(200);
		avatarUrlItem = new Img("", IMG_WIDTH, IMG_HEIGHT);
		avatarUrlItem.setBorder("1px solid lightgray");
		
		IButton uploadPicButton = new IButton("上传头像");
		uploadPicButton.setIcon("actions/plus.png");
		uploadPicButton.setWidth(IMG_WIDTH);
		
		imgVLayout.setMembers(avatarUrlItem, uploadPicButton);
		imgVLayout.setMembersMargin(5);
		imgVLayout.setMargin(8);
		
		form = new DynamicForm();
		

		final TextItem userRealNameItem = new TextItem();
		userRealNameItem.setTitle("姓名");
		userRealNameItem.setName("userRealName");
		userRealNameItem.setWidth("100%");

		final TextItem userEmailItem = new TextItem();
		userEmailItem.setTitle("E-Mail");
		userEmailItem.setName("userEmail");
		userEmailItem.setWidth("100%");

		final TextItem userOccupationItem = new TextItem();
		userOccupationItem.setTitle("用户类型");
		userOccupationItem.setName("userType");
		userOccupationItem.setWidth("100%");

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
		

		form.setColWidths(80, "*");
		form.setFields(userRealNameItem,
				userEmailItem, userOccupationItem,
				userEducationItem, userFloorItem, userPositionItem);
		form.setWidth(300);
		form.setCellPadding(5);
		form.setHeight(200);

//		form.setStyleName("alex_myDecoratorPanel");
//		form.setMargin(10);
//		form.setPadding(10);
		
		userRealNameItem.setCanEdit(false);
		userEmailItem.setCanEdit(false);
		userEducationItem.setCanEdit(false);
		userOccupationItem.setCanEdit(false);
		userFloorItem.setCanEdit(false);
		userPositionItem.setCanEdit(false);
		
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
				
				userFloorItem.setCanEdit(true);
				userPositionItem.setCanEdit(true);
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
						userFloorItem.setCanEdit(false);
						userPositionItem.setCanEdit(false);
						
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
		uploadPicButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				UploadImageWindow uploadWindow = new UploadImageWindow(getSelf());
				uploadWindow.show();
			}
		});
		
		
		HLayout butLayout = new HLayout();
		butLayout.setMembers(butEdit, butSubmit, butChangePW);
		butLayout.setMargin(10);
		butLayout.setMembersMargin(10);
		
		hLayout.setMembers(imgVLayout, form);
		hLayout.setMembersMargin(10);
		hLayout.setStyleName("alex_myDecoratorPanel");
		hLayout.setPadding(10);
		hLayout.setMargin(10);
		hLayout.setHeight(250);
		
		Label headerMust = new Label("读者基本信息");
		headerMust.setStyleName("alex_header_label");
		headerMust.setSize("105px", "30px");
		headerMust.setAlign(Alignment.RIGHT);
		headerMust.setValign(VerticalAlignment.BOTTOM);
		
		this.setMembers(headerMust, hLayout, butLayout);

		fillDatas();
		
	}
	
	protected FinishUploadOperatable getSelf() {
		return this;
	}

	private void fillDatas() {
		userInfo = SFLibrary.get().getNowUser();

		form.setValue("userRealName", userInfo.getUserName());
		form.setValue("userEmail", userInfo.getUserEmail());
		form.setValue("userType", userInfo.getUserType());
		form.setValue("userDepartment", userInfo.getUserDepartment());
		form.setValue("userFloor", userInfo.getUserFloor()==null?"":userInfo.getUserFloor());
		form.setValue("userPosition", userInfo.getUserPosition()==null?"":userInfo.getUserPosition());

		avatarUrl = userInfo.getAvatarUrl();
		// http://img4.qzone.cc/one/o/82/073908.jpg
		avatarUrlItem.setSrc(avatarUrl==null?"http://img4.qzone.cc/one/o/82/073908.jpg":avatarUrl);

	}
	
	private boolean inputValidation() {
		
		this.userInfo.setUserName(form.getValueAsString("userRealName"));
		this.userInfo.setUserEmail(form.getValueAsString("userEmail"));
		this.userInfo.setUserType(form.getValueAsString("userType"));
		this.userInfo.setUserDepartment(form.getValueAsString("userDepartment"));

		this.userInfo.setUserFloor(form.getValueAsString("userFloor"));
		this.userInfo.setUserPosition(form.getValueAsString("userPosition"));

		this.userInfo.setAvatarUrl(avatarUrl);
		
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

	@Override
	public void doAfterFinishUpload(String picName) {
		avatarUrl = picName;
		avatarUrlItem.setSrc(avatarUrl);
	}

}
