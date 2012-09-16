package com.successfactors.library.client.view;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminBorrowHistoryView extends VLayout {

	private static final String DESCRIPTION = "借阅历史";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			AdminBookManagementView view = new AdminBookManagementView();
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
