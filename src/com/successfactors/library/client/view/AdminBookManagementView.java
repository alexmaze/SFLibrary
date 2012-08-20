package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.AdminBookManagementListGrid;
import com.successfactors.library.client.widget.Toolbar;
import com.successfactors.library.client.widget.Toolbar.ToolbarButtonType;

public class AdminBookManagementView extends VLayout {

	private static final String DESCRIPTION = "书库管理";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private Toolbar theToolbar;
	private ListGrid theListGrid;
	
	public AdminBookManagementView() {
		super();

		GWT.log("初始化: AdminBookManagementView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new Toolbar();
		theListGrid = new AdminBookManagementListGrid();
		
		this.addMember(theToolbar);
		this.addMember(theListGrid);
		
		bind();
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.New_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.say("ADD");
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Delete_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.say("DELETE");
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Update_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.say("UPDATE");
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
