package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.ConfigurableToolbar;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.UserInfoPanel;
import com.successfactors.library.client.widget.UserListGrid;

public class AdminUserManagementView extends VLayout {

	private static final String DESCRIPTION = "用户管理";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	private ConfigurableToolbar theToolbar;
	private JumpBar theJumpBar;
	private UserListGrid theListGrid;
	private UserInfoPanel theInfoPanel;
	
	public AdminUserManagementView() {
		super();

		GWT.log("初始化: AdminUserManagementView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ConfigurableToolbar();
		theJumpBar = new JumpBar();
		theListGrid = new UserListGrid(theJumpBar);
		theInfoPanel = new UserInfoPanel(theListGrid);
		
		VLayout vLayout = new VLayout();
		vLayout.addMember(theListGrid);
		vLayout.addMember(theJumpBar);
		vLayout.setWidth(370);
		vLayout.setMargin(5);
		
		HLayout hLayout = new HLayout();
		hLayout.addMember(vLayout);
		hLayout.addMember(theInfoPanel);

		this.addMember(theToolbar);
		this.addMember(hLayout);
		
		bind();
	}
	
	private void bind() {
		theToolbar.addToolStripButton("新用户", "toolbar/new.png")
			.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				theInfoPanel.addUser();
			}
		});
		theToolbar.addSeparator();

		theToolbar.addToolStripButton("删除用户", "toolbar/delete.png")
			.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.deleteUser();
			}
		});
		theToolbar.addSeparator();
		
		
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage();
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage();
			}
		});
		
			theListGrid.addCellClickHandler(new CellClickHandler() {
				
				@Override
				public void onCellClick(CellClickEvent event) {
					theInfoPanel.refresh(event.getRecord());
				}
			});
		
	}

	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			AdminUserManagementView view = new AdminUserManagementView();
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
