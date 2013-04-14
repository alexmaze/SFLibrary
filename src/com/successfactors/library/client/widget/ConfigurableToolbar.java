package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ConfigurableToolbar extends HLayout {

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "*";

//	private static final String NEW_BUTTON = "toolbar/new.png";
//	private static final String DELETE_BUTTON = "toolbar/delete.png";
//
//	private static final String APPLY_BUTTON = "toolbar/reports.png";
//
//	private static final String NEW_BUTTON_DISPLAY_NAME = "新建";
//	private static final String DELETE_BUTTON_DISPLAY_NAME = "删除";
//	private static final String UPDATE_BUTTON_DISPLAY_NAME = "修改";
//
//	ToolStripButton newButton;
//	ToolStripButton deleteButton;
//	ToolStripButton updateButton;
	
	private ToolStrip toolStrip;

	public ConfigurableToolbar() {
		super();
		GWT.log("初始化：Toolbar", null);
		this.setStyleName("crm-ToolBar");
		this.setHeight(TOOLBAR_HEIGHT);
		toolStrip = new ToolStrip();
		toolStrip.setHeight(TOOLBAR_HEIGHT);
		toolStrip.setWidth(TOOLSTRIP_WIDTH);
		this.addMember(toolStrip);
		this.setMargin(5);
		
		this.setMaxWidth(1007);
	}
	
	public ToolStripButton addToolStripButton(String displayName, String iconPath) {
		ToolStripButton newButton = new ToolStripButton();
		newButton.setIcon(iconPath);
		newButton.setTitle(displayName);
		toolStrip.addButton(newButton);
		return newButton;
	}
	
	public void addSeparator() {
		toolStrip.addSeparator();
	}

}