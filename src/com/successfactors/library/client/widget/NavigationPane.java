package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.successfactors.library.client.data.NavigationPaneRecord;

public class NavigationPane extends VLayout {

  private SectionStack sectionStack ;
	
  public NavigationPane() {
	super();
				
	GWT.log("初始化：NavigationPane", null);
	
    // Initialize the Navigation Pane layout container
	this.setStyleName("crm-NavigationPane");	
    this.setWidth100();
    // this.setShowResizeBar(true); 
	
	// Initialize the Section Stack
    sectionStack = new SectionStack();
    sectionStack.setWidth100();
    sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
    sectionStack.setShowExpandControls(true);
    sectionStack.setAnimateSections(true);	
    
    // Add the Section Stack to the Navigation Pane layout container
	this.addMember(sectionStack); 
  }	
  
  public void add(String sectionName, NavigationPaneRecord[] sectionData, 
		  		  RecordClickHandler clickHandler) {
	sectionStack.addSection(new NavigationPaneSection(sectionName, sectionData,
							clickHandler));
  } 

  public void expandSection(int section) {
	sectionStack.expandSection(section);
  } 
}