package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class Masthead extends HLayout {

  private static final int MASTHEAD_HEIGHT = 58;
   
  /**
   * 主页面布局——网站顶部
   * */
  public Masthead() {
	super();
		
	GWT.log("初始化：Masthead", null);
	
    // Initialize the Masthead layout container
	this.setStyleName("crm-Masthead");	
    this.setHeight(MASTHEAD_HEIGHT);
    
	// Initialize the Logo image
    Img logo = new Img("logo.png", 430, 48); 
    logo.setStyleName("crm-Masthead-Logo");	  
    
	// Initialize the Name label	
	Label name = new Label();  
	name.setStyleName("crm-MastHead-Name");  
	name.setContents(""); 
    
    // Initialize the West layout container
    HLayout westLayout = new HLayout();
    westLayout.setHeight(MASTHEAD_HEIGHT);	
    westLayout.setWidth("50%");
    westLayout.addMember(logo);
    westLayout.addMember(name);
    
    // Initialize the Signed In User label
	Label signedInUser = new Label();  
	signedInUser.setStyleName("crm-MastHead-SignedInUser");  
	signedInUser.setContents("<b>&nbsp &nbsp &nbsp SuccessFactors</b><br /><i>&nbsp&nbsp&nbsp— 内部使用</i>");   

    // Initialize the East layout container
    HLayout eastLayout = new HLayout();
    eastLayout.setAlign(Alignment.RIGHT);  
    eastLayout.setHeight(MASTHEAD_HEIGHT);
    eastLayout.setWidth("50%");
    eastLayout.addMember(signedInUser);	
    
    // Add the West and East layout containers to the Masthead layout container
	this.addMember(westLayout);  	
	this.addMember(eastLayout); 
  }	
}