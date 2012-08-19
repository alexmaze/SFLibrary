package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminBorrowManagementView extends VLayout {

	private static final String DESCRIPTION = "借阅管理";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public AdminBorrowManagementView() {
		super();

		GWT.log("初始化: AdminBorrowManagementView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			AdminBorrowManagementView view = new AdminBorrowManagementView();
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
