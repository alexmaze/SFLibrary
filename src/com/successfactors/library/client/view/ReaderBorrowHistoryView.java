package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ReaderBorrowHistoryListGrid;
import com.successfactors.library.client.widget.ToolBarWithBorrowSearch;

public class ReaderBorrowHistoryView extends VLayout {

	private static final String DESCRIPTION = "借阅历史";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ToolBarWithBorrowSearch theToolbar;
	private ReaderBorrowHistoryListGrid theListGrid;
	private JumpBar theJumpBar;

	public ReaderBorrowHistoryView() {
		super();

		GWT.log("初始化: ReaderBorrowHistoryView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolBarWithBorrowSearch(false, false);
		theJumpBar = new JumpBar();
		theListGrid = new ReaderBorrowHistoryListGrid(theJumpBar);
		
		this.setMembers(theToolbar, theListGrid, theJumpBar);
		bind();
		
	}
	
	private void bind() {
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage(theToolbar.getSearchInfo());
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage(theToolbar.getSearchInfo());
			}
		});
	}
	
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
