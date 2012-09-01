package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 主页展示书架2-热门书籍，包含详细信息
 * */
public class HotBookShelf extends VLayout {
	
	public HotBookShelf() {
		super();

		GWT.log("初始化: HotBookShelf", null);
		
		
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);
	}

}
