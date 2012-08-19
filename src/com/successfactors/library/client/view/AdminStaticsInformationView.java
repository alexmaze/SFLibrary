package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminStaticsInformationView extends VLayout {

	private static final String DESCRIPTION = "统计信息";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public AdminStaticsInformationView() {
		super();

		GWT.log("初始化: AdminStaticsInformationView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			AdminStaticsInformationView view = new AdminStaticsInformationView();
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
