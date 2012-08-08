package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.successfactors.library.client.data.NavigationPaneRecord;

public class NavigationPaneSection extends SectionStackSection {
	
  private ListGrid listGrid;
  private NavigationPaneRecord[] sectionData;
  
  public NavigationPaneSection(String sectionName, NavigationPaneRecord[] sectionData, 
		  					   RecordClickHandler clickHandler) {
  	super(sectionName);
  				
  	GWT.log("初始化：NavigationPaneSection", null);
  	
  	this.sectionData = sectionData;
  	
	// Initialize the List Grid  
  	listGrid = new ListGrid();  
  	listGrid.setBaseStyle("crm-NavigationPaneGridCell");  
  	listGrid.setWidth("100%");  
  	listGrid.setHeight("100%");  
  	listGrid.setShowAllRecords(true);  
  	listGrid.setShowHeader(false);
  		
  	// Initialize the Icon field
  	ListGridField appIconField = new ListGridField("icon", "Icon", 27);  
  	appIconField.setImageSize(16); 
  	appIconField.setAlign(Alignment.RIGHT);
  	appIconField.setType(ListGridFieldType.IMAGE);  
  	appIconField.setImageURLPrefix("icons/16/");  
  	appIconField.setImageURLSuffix(".png");  
  	appIconField.setCanEdit(false);  
  	
  	// Initialize the Name field
  	ListGridField appNameField = new ListGridField("name", "Name");  
  	   
  	// Add the fields to the list Grid
  	listGrid.setFields(new ListGridField[] {appIconField, appNameField});  
  	
  	// Set up the column data
  	listGrid.setData(sectionData);  
  	listGrid.selectRecord(0);
  	
  	// Register the click handler
  	listGrid.addRecordClickHandler(clickHandler);
  	
    // Section.setItems(appList);
    this.addItem(listGrid);
    this.setExpanded(true);  
  }
  
  public ListGrid getListGrid() {
	return listGrid;
  }

  public void selectRecord(String name) {
    for (int i = 0; i < this.sectionData.length; i++) { 
   	  NavigationPaneRecord record = this.sectionData[i];
      	
   	  if (name.contentEquals(record.getName())) {
   		GWT.log("selectRecord->name.contentEquals(record.getName())", null);
   		getListGrid().deselectAllRecords();
   		getListGrid().selectRecord(i); 
   	  }
    }
  }
    
  public int getRecord(String appName) {
	int result = -1;
    for (int i = 0; i < this.sectionData.length; i++) { 
      NavigationPaneRecord record = this.sectionData[i];
  	    	
  	  if (appName.contentEquals(record.getName())) {
  	    GWT.log("selectRecord->name.contentEquals(record.getName())", null);
  	    result = i;
  	  }
    }
      
    return result;
  }

  public void setSectionData(NavigationPaneRecord[] sectionData) {
    this.sectionData = sectionData;
  }

  public NavigationPaneRecord[] getSectionData() {
	return sectionData;
  }
}