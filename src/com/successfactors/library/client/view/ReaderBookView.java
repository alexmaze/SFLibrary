package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReaderBookView extends VLayout {
	
	private static final String DESCRIPTION = "图书查询";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public ReaderBookView() {
		super();

		GWT.log("初始化: ReaderBookView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderBookView view = new ReaderBookView();
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
