package com.successfactors.library.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.data.NavigationPaneRecord;
import com.successfactors.library.client.view.ContextAreaFactory;
import com.successfactors.library.client.view.RegisterView;
import com.successfactors.library.client.view.WelcomeView;
import com.successfactors.library.client.widget.LoginBox;
import com.successfactors.library.client.widget.Masthead;
import com.successfactors.library.client.widget.NavigationPane;
import com.successfactors.library.client.widget.NavigationPaneHeader;
import com.successfactors.library.client.widget.StatusInfoBar;

public class SFLibrary implements EntryPoint {
	
	private static SFLibrary singleton;

	private static final int NORTH_HEIGHT = 85;
	public static final String WEST_WIDTH = "250";

	private VLayout mainLayout;
	private HLayout northLayout;
	private HLayout southLayout;
	private VLayout eastLayout;
	private VLayout westLayout;
	
	private StatusInfoBar statusInfoBar;
	private NavigationPaneHeader navigationPaneHeader;
	
	private SimpleEventBus eventBus;
	
	public void onModuleLoad() {

		singleton = this;
		eventBus = new SimpleEventBus();
		new AppController(eventBus);
		
		Window.enableScrolling(false);
		Window.setMargin("0px");
		
		mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		northLayout = new HLayout();
		northLayout.setHeight(NORTH_HEIGHT);

		VLayout vLayout = new VLayout();
		vLayout.addMember(new Masthead());
		
		navigationPaneHeader = new NavigationPaneHeader();
		
		statusInfoBar = new StatusInfoBar();
		vLayout.addMember(statusInfoBar);
		vLayout.addMember(navigationPaneHeader);
		
		northLayout.addMember(vLayout);
		
		initWestPart();;

		initEastPart();

		southLayout = new HLayout();
		westLayout.setWidth(WEST_WIDTH);
		southLayout.setMembers(westLayout, eastLayout);

		mainLayout.addMember(northLayout);
		mainLayout.addMember(southLayout);
		
		mainLayout.draw();
	}
	
	/**
	 * 重新加载页面
	 * */
	public void reLoad() {
		onModuleLoad();
	}
	
	/**
	 * 获取应用实例
	 * */
	public static SFLibrary get() {
		return singleton;
	}
	
	/**
	 * 获取统一应用事件总线
	 * */
	public SimpleEventBus getEventBus() {
		return eventBus;
	}
	
	// 默认登录框
	private void initWestPart() {
		navigationPaneHeader.setNavigationPaneHeaderLabelContents("登录");
		westLayout = new LoginBox();
	}
	
	// 默认欢迎界面
	private void initEastPart() {
		navigationPaneHeader.setContextAreaHeaderLabelContents("欢迎使用");
		eastLayout = new WelcomeView();
	}
	
	// 显示新用户注册界面
	public void showRegisterView() {
		
		ContextAreaFactory factory = new RegisterView.Factory();
		navigationPaneHeader.setContextAreaHeaderLabelContents(factory.getDescription());
		Canvas view = factory.create();
		southLayout.setMembers(westLayout, view);
	}
	
	// 登录成功后显示导航框
	public void showNavigationPane() {
		
		navigationPaneHeader.setNavigationPaneHeaderLabelContents("控制面板");
		
		NavigationPane navigationPane = new NavigationPane();
//		navigationPane.add("用户信息管理",
//				PersonNavigationPaneSectionData.getRecords(),
//				new NavigationPaneClickHandler());
//		navigationPane.add("项目信息管理",
//				ProjectNavigationPaneSectionData.getRecords(),
//				new NavigationPaneClickHandler());
//		navigationPane.add("其他",
//				OtherNavigationPaneSectionData.getRecords(),
//				new NavigationPaneClickHandler());
		
		navigationPane.expandSection(0);
		westLayout = navigationPane;
		westLayout.setWidth(WEST_WIDTH);
		
		southLayout.setMembers(westLayout, eastLayout);
	}
	
	// 导航框点击事件
	private class NavigationPaneClickHandler implements RecordClickHandler {
		@Override
		public void onRecordClick(RecordClickEvent event) {
			NavigationPaneRecord record = (NavigationPaneRecord) event
					.getRecord();
			setContextAreaView(record);
		}
	}
	
	// 设置导航框点击后显示View
	public void setContextAreaView(NavigationPaneRecord record) {
		String name = record.getName();
		navigationPaneHeader.setContextAreaHeaderLabelContents(name);
		ContextAreaFactory factory = record.getFactory();
		Canvas view = factory.create();
		southLayout.setMembers(westLayout, view);
	}
	
}
