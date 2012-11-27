package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ToolbarWithRecButton extends ToolStrip {
	
	public enum ToolbarButtonType {
		Recommend_Button
	}

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "100%";

	private static final String RECOMMEND_BUTTON = "toolbar/newreport.png";

	private static final String RECOMMEND_BUTTON_DISPLAY_NAME = "推荐图书";

	ToolStripButton recommendButton;

	public ToolbarWithRecButton() {
		super();

		GWT.log("初始化：ToolbarButtonType", null);

		this.setHeight(TOOLBAR_HEIGHT);
		this.setWidth(TOOLSTRIP_WIDTH);

			this.addSeparator();
			recommendButton = new ToolStripButton();
			recommendButton.setIcon(RECOMMEND_BUTTON);
			recommendButton.setTitle(RECOMMEND_BUTTON_DISPLAY_NAME);
			this.addButton(recommendButton);
	}

	public void addButtonClickHandler(ToolbarButtonType butType, ClickHandler clickHandler) {
		
		if (butType == ToolbarButtonType.Recommend_Button) {
			recommendButton.addClickHandler(clickHandler);
		} else {
			GWT.log("Toolbar 按钮绑定单击事件错误 " + butType.name());
		}
		
	}
	
}