package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class Toolbar extends HLayout {

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "*";

	private static final String NEW_BUTTON = "toolbar/new.png";
	private static final String DELETE_BUTTON = "toolbar/delete.png";

	private static final String APPLY_BUTTON = "toolbar/email.png";

	private static final String NEW_BUTTON_DISPLAY_NAME = "新建";
	private static final String DELETE_BUTTON_DISPLAY_NAME = "删除";
	private static final String UPDATE_BUTTON_DISPLAY_NAME = "修改";

	ToolStripButton newButton;
	ToolStripButton deleteButton;
	ToolStripButton updateButton;

	public Toolbar() {
		super();

		GWT.log("初始化：Toolbar", null);

		this.setStyleName("crm-ToolBar");
		this.setHeight(TOOLBAR_HEIGHT);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setHeight(TOOLBAR_HEIGHT);
		toolStrip.setWidth(TOOLSTRIP_WIDTH);

		newButton = new ToolStripButton();
		newButton.setIcon(NEW_BUTTON);
		newButton.setTitle(NEW_BUTTON_DISPLAY_NAME);
		toolStrip.addButton(newButton);
		toolStrip.addSeparator();

		deleteButton = new ToolStripButton();
		deleteButton.setIcon(DELETE_BUTTON);
		deleteButton.setTitle(DELETE_BUTTON_DISPLAY_NAME);
		toolStrip.addButton(deleteButton);

		toolStrip.addSeparator();
		
		updateButton = new ToolStripButton();
		updateButton.setIcon(APPLY_BUTTON);
		updateButton.setTitle(UPDATE_BUTTON_DISPLAY_NAME);
		toolStrip.addButton(updateButton);

		this.addMember(toolStrip);
	}

	public void addButtonClickHandler(String butName, ClickHandler clickHandler) {
		
		if (butName.equals("newButton")) {
			newButton.addClickHandler(clickHandler);
		} else if (butName.equals("deleteButton")) {
			deleteButton.addClickHandler(clickHandler);
		} else if (butName.equals("updateButton")) {
			updateButton.addClickHandler(clickHandler);
		} else {
			GWT.log("Toolbar 按钮绑定单击事件错误 "+butName);
		}
		
	}

}