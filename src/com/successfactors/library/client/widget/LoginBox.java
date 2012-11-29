package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.AppController;
import com.successfactors.library.client.SFLibrary;

public class LoginBox extends VLayout {
	
	  public LoginBox() {
		super();
		
		GWT.log("初始化：LoginBox", null);
		
	    // 现初始化登录框的容器
		this.setStyleName("alex_LoginBox");
	    this.setWidth(SFLibrary.WEST_WIDTH);
	    
        final DynamicForm form = new DynamicForm();
        form.setWidth("18%");
        
        HeaderItem header1 = new HeaderItem();
        header1.setDefaultValue("已有账号");

        HeaderItem header2 = new HeaderItem();
        header2.setDefaultValue("还没账号？");
        //header2.setCellStyle("alex_LoginBox_Header");
        
        TextItem userNameItem = new TextItem();
        userNameItem.setTitle("Email");
        userNameItem.setName("userName");
        userNameItem.setWidth(170);
        
        PasswordItem passwordItem = new PasswordItem();
        passwordItem.setTitle("密码");
        passwordItem.setName("password");
        passwordItem.setWidth(170);
        passwordItem.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					AppController.doLogin(form.getValueAsString("userName"), form.getValueAsString("password"));
				}
			}
		});

        ButtonItem loginButItem = new ButtonItem();
        loginButItem.setTitle("登录");
        loginButItem.setIcon("actions/accept.png");
        loginButItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	AppController.doLogin(form.getValueAsString("userName"), form.getValueAsString("password"));
            }
        });
        loginButItem.setWidth(100);
        loginButItem.setColSpan(2);
        loginButItem.setAlign(Alignment.RIGHT);

        ButtonItem signUpButItem = new ButtonItem();
        signUpButItem.setTitle("立即注册");
        signUpButItem.setColSpan(2);
        signUpButItem.setIcon("actions/add.png");
        signUpButItem.setWidth(100);
        signUpButItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	// AppController.doSignUp();
            	SC.say("注册功能已关闭，请直接用sf邮箱登录，初始密码为：123456");
            }
        });
        
        form.setFields(header1, userNameItem, passwordItem, loginButItem, header2, signUpButItem);
        form.setColWidths(54,170);
        form.setMargin(5);
        
        this.addMember(form);
        
        Label adminInfoLabel1 = new Label(
        		"管理员："
        		);
        Label adminInfoLabel2 = new Label(
        		"Megan Wang<br/>" +
        		"&nbsp&nbsp meganwang@successfactors.com"
        		);
        Label adminInfoLabel3 = new Label(
        		"April Mei<br/>" +
        		"&nbsp&nbsp amei@successfactors.com"
        		);
        Label adminInfoLabel4 = new Label(
        		"Daniel Wei<br/>" +
        		"&nbsp&nbsp dwei@successfactors.com"
        		);
        adminInfoLabel1.setStyleName("alex_admin_info_title");
        adminInfoLabel2.setStyleName("alex_admin_info_label");
        adminInfoLabel3.setStyleName("alex_admin_info_label");
        adminInfoLabel4.setStyleName("alex_admin_info_label");
        
        adminInfoLabel1.setHeight("40px");
        adminInfoLabel2.setHeight("40px");
        adminInfoLabel3.setHeight("40px");
        adminInfoLabel4.setHeight("40px");
        
        VLayout adminLayout = new VLayout();
        
        adminLayout.addMember(adminInfoLabel1);
        adminLayout.addMember(adminInfoLabel2);
        adminLayout.addMember(adminInfoLabel3);
        adminLayout.addMember(adminInfoLabel4);
        
        adminLayout.setMargin(10);
        adminLayout.setHeight("140px");
	    
        this.addMember(adminLayout);

        
        Img logo = new Img("minerva.jpg"); 
        logo.setWidth(240);
        logo.setHeight(374);
        logo.setStyleName("alex_LoginBox_Picture");
        
        this.addMember(logo);
	  }
	  
	}