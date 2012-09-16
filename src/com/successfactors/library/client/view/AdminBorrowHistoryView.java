package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.AdminBorrowHistoryListGrid;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ToolBarWithBorrowSearch;
import com.successfactors.library.client.widget.ToolBarWithBorrowSearch.ToolbarButtonType;

public class AdminBorrowHistoryView extends VLayout {

	private static final String DESCRIPTION = "借阅历史";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	private ToolBarWithBorrowSearch theToolbar;
	private AdminBorrowHistoryListGrid theListGrid;
	private JumpBar theJumpBar;
	
	private boolean isSearchMode = false;
	
	public AdminBorrowHistoryView() {
		super();

		GWT.log("初始化: AdminBorrowHistoryView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolBarWithBorrowSearch(false, false);
		theJumpBar = new JumpBar();
		theListGrid = new AdminBorrowHistoryListGrid(theJumpBar);
		
		this.setMembers(theToolbar, theListGrid, theJumpBar);
		bind();
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.Search_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isSearchMode = true;
				theListGrid.doSearchBorrow(theToolbar.getSearchInfo());
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage(isSearchMode, theToolbar.getSearchInfo());
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage(isSearchMode, theToolbar.getSearchInfo());
			}
		});
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			AdminBorrowHistoryView view = new AdminBorrowHistoryView();
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
