package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
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
            	AppController.doSignUp();
            }
        });
        
        form.setFields(header1, userNameItem, passwordItem, loginButItem, header2, signUpButItem);
        form.setColWidths(54,170);
        form.setMargin(5);
        
        this.addMember(form);
        
        Img logo = new Img("minerva.jpg"); 
        logo.setWidth(240);
        logo.setHeight(374);
        logo.setStyleName("alex_LoginBox_Picture");
        
        this.addMember(logo);
	    
        // TODO 测试用
        userNameItem.setValue("ayan@successfactors.com");
        passwordItem.setValue("123456");
        
	  }
	  
	}