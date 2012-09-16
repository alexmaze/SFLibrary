package com.successfactors.library.client.view;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReaderBorrowHistoryView extends VLayout {

	private static final String DESCRIPTION = "借阅历史";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderBorrowHistoryView view = new ReaderBorrowHistoryView();
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
