package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReaderBorrowView extends VLayout {
	
	private static final String DESCRIPTION = "我的借阅";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public ReaderBorrowView() {
		super();

		GWT.log("初始化: ReaderBorrowView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderBorrowView view = new ReaderBorrowView();
			id = view.getID();
			return view;
		}

		public String getID() {
			return id;
		}

		public String getDescription() {
			return DESCRIPTION;
		}
	}

}
