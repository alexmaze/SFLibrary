package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ToolbarWithRecSelect extends ToolStrip {
	
	public enum ToolbarButtonType {
		Selected_Button
	}

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "100%";

	private static final String RECOMMEND_BUTTON = "toolbar/newreport.png";

	private static final String SELECTED_BUTTON_DISPLAY_NAME = "已选择";

	ToolStripButton selectedButton;

	public ToolbarWithRecSelect() {
		super();

		GWT.log("初始化：ToolbarButtonType", null);

		this.setHeight(TOOLBAR_HEIGHT);
		this.setWidth(TOOLSTRIP_WIDTH);

			this.addSeparator();
			selectedButton = new ToolStripButton();
			selectedButton.setIcon(RECOMMEND_BUTTON);
			selectedButton.setTitle(SELECTED_BUTTON_DISPLAY_NAME);
			this.addButton(selectedButton);
	}

	public void addButtonClickHandler(ToolbarButtonType butType, ClickHandler clickHandler) {
		
		if (butType == ToolbarButtonType.Selected_Button) {
			selectedButton.addClickHandler(clickHandler);
		} else {
			GWT.log("Toolbar 按钮绑定单击事件错误 " + butType.name());
		}
		
	}
	
}