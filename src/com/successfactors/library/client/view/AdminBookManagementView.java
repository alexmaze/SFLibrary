package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.AdminBookManagementListGrid;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ToolbarWithBookSearch;
import com.successfactors.library.client.widget.ToolbarWithBookSearch.ToolbarButtonType;

public class AdminBookManagementView extends VLayout {

	private static final String DESCRIPTION = "书库管理";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ToolbarWithBookSearch theToolbar;
	private AdminBookManagementListGrid theListGrid;
	private JumpBar theJumpBar;
	
	private boolean isSearchMode = false;
	
	public AdminBookManagementView() {
		super();

		GWT.log("初始化: AdminBookManagementView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolbarWithBookSearch(true);
		theJumpBar = new JumpBar();
		theListGrid = new AdminBookManagementListGrid(theJumpBar);
		
		this.addMember(theToolbar);
		this.addMember(theListGrid);
		this.addMember(theJumpBar);
		
		bind();
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.New_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doAddBook();
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Delete_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doDeleteBook();
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Update_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doUpdateBook();
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Search_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isSearchMode = true;
				theListGrid.doSearchBook(theToolbar.getSearchInfo());
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
