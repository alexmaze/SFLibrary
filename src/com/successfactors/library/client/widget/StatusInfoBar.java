package com.successfactors.library.client.widget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.successfactors.library.client.helper.MyToolsInClient;

public class StatusInfoBar extends HLayout{

	private static final int APPLICATION_MENU_HEIGHT = 27;
	private  Label leftLabel;
	private  Label rightLabel;
	private  Label centerLabel;
	public StatusInfoBar(){
		super();

		GWT.log("初始化： UserLogOutView", null);

		this.setStyleName("crm-ApplicationMenu");
		this.setHeight(APPLICATION_MENU_HEIGHT);
		
		leftLabel = new Label();  
		leftLabel.setStyleName("crm-MastHead-Name");  	 
		leftLabel.setContents("<b>"+MyToolsInClient.getTimePeriod(new Date())+"好： "+"游客"+"</b>"); 
		leftLabel.setWidth("300px");
	    
	    HLayout westLayout = new HLayout();	 	
	    westLayout.setWidth100();	   
	    westLayout.addMember(leftLabel);	    
	
	    centerLabel = new Label();
	    centerLabel.setStyleName("crm-MastHead-Name");  
	    centerLabel.setContents(""); 
	    centerLabel.setStyleName("alex_logout_label");
	    
	    rightLabel = new Label();
	    rightLabel.setStyleName("crm-MastHead-Name");  
	    rightLabel.setContents("<b>......</b>"); 
	    rightLabel.setStyleName("alex_logout_label");

	    HLayout eastLayout = new HLayout();
	    eastLayout.setAlign(Alignment.RIGHT);  
	
	    eastLayout.setWidth("100%");
	    eastLayout.addMember(centerLabel);	
	    eastLayout.addMember(rightLabel);	
	    
	   
		this.addMember(westLayout);  	
		this.addMember(eastLayout); 		
	}
	
	public void setLeftInfo(String strInfo) {
		leftLabel.setContents("<b>"+strInfo+"</b>"); 
	}
	
	public void setRightInfo(String strInfo) {
		rightLabel.setContents("<b>"+strInfo+"</b>"); 
	}
	
	public void setCenterInfo(String strInfo) {
		centerLabel.setContents("<b>"+strInfo+"</b>"); 
	}
	
	public void addLeftClickHandler(ClickHandler clickhd) {
		leftLabel.addClickHandler(clickhd);
	}

	public void addRightClickHandler(ClickHandler clickhd) {
		rightLabel.addClickHandler(clickhd);
	}

	public void addCenterClickHandler(ClickHandler clickhd) {
		centerLabel.addClickHandler(clickhd);
	}
}
