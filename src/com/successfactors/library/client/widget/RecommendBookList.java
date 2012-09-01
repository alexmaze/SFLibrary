package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 主页展示推荐书目列表
 * */
public class RecommendBookList extends VLayout {
	
	public RecommendBookList() {
		super();

		GWT.log("初始化: RecommendBookList", null);
		
		
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);
	}
	
}
