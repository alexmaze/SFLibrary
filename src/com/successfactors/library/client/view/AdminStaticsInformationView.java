package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.successfactors.library.client.widget.AdminStaticsInfomationLayout;

public class AdminStaticsInformationView extends VLayout {

	private static final String DESCRIPTION = "统计信息";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public AdminStaticsInformationView() {
		super();

		GWT.log("初始化: AdminStaticsInformationView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		TabSet tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setWidth("100%");
		tabSet.setHeight("100%");
  
        Tab tTab1 = new Tab("借阅、预订统计", "./icons/16/reports.png");
        tTab1.setWidth(150);
        VLayout performanceProjectLayout = new AdminStaticsInfomationLayout();
        tTab1.setPane(performanceProjectLayout);
  
        Tab tTab2 = new Tab("其他统计", "./icons/16/reports.png");
        tTab2.setWidth(150);
        VLayout performanceStaffLayout = new VLayout();
        tTab2.setPane(performanceStaffLayout);
  
        tabSet.addTab(tTab1);
        tabSet.addTab(tTab2);
        
        this.setMembers(tabSet);
        this.setPadding(5);
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
