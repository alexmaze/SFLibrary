package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.AdminOrderHistoryListGrid;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ToolBarWithOrderSearch;
import com.successfactors.library.client.widget.ToolBarWithOrderSearch.ToolbarButtonType;

public class AdminOrderHistoryView extends VLayout {

	private static final String DESCRIPTION = "预订历史";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ToolBarWithOrderSearch theToolbar;
	private AdminOrderHistoryListGrid theListGrid;
	private JumpBar theJumpBar;
	
	private boolean isSearchMode = false;

	public AdminOrderHistoryView() {
		super();

		GWT.log("初始化: AdminOrderHistoryView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolBarWithOrderSearch();
		theJumpBar = new JumpBar();
		theListGrid = new AdminOrderHistoryListGrid(theJumpBar);
		
		this.setMembers(theToolbar, theListGrid, theJumpBar);
		bind();
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.Search_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isSearchMode = true;
				theListGrid.doSearchOrder(theToolbar.getSearchInfo());
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
			AdminOrderHistoryView view = new AdminOrderHistoryView();
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