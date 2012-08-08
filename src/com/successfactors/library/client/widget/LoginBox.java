package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.DateChooser;
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

public class LoginBox extends VLayout {
	
	  private static final String WEST_WIDTH = "20%";
	  
	  
	  public LoginBox() {
		super();
		
		GWT.log("初始化：LoginBox", null);
		
	    // 现初始化登录框的容器
		this.setStyleName("alex_LoginBox");
	    this.setWidth(WEST_WIDTH);
	    
        final DynamicForm form = new DynamicForm();
        form.setWidth("18%");
        
        HeaderItem header1 = new HeaderItem();
        header1.setDefaultValue("已有账号");

        HeaderItem header2 = new HeaderItem();
        header2.setDefaultValue("还没账号？");
        
        TextItem userNameItem = new TextItem();
        userNameItem.setTitle("用户名");
        userNameItem.setName("userName");
        
        PasswordItem passwordItem = new PasswordItem();
        passwordItem.setTitle("密码");
        passwordItem.setName("password");
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
        loginButItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	AppController.doLogin(form.getValueAsString("userName"), form.getValueAsString("password"));
            }
        });
        loginButItem.setWidth(60);
        loginButItem.setColSpan(2);
        loginButItem.setAlign(Alignment.RIGHT);

        ButtonItem signUpButItem = new ButtonItem();
        signUpButItem.setTitle("立即注册");
        signUpButItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	AppController.doSignUp();
            }
        });
        
        form.setFields(header1, userNameItem, passwordItem, loginButItem, header2, signUpButItem);
        form.setMargin(5);
        
        this.addMember(form);
        
        DateChooser dateChooser = new DateChooser();
        dateChooser.setStyleName("alex_LoginBox_DateChooser");
        //dateChooser.setHeight("250px");
        
        this.addMember(dateChooser);
	    
	  }
	  
	}