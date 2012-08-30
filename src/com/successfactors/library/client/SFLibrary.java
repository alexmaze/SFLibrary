package com.successfactors.library.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.data.AdminNavigationPaneSectionData;
import com.successfactors.library.client.data.NavigationPaneRecord;
import com.successfactors.library.client.data.ReaderNavigationPaneSectionData;
import com.successfactors.library.client.service.BookService;
import com.successfactors.library.client.service.BookServiceAsync;
import com.successfactors.library.client.service.BorrowService;
import com.successfactors.library.client.service.BorrowServiceAsync;
import com.successfactors.library.client.service.OrderService;
import com.successfactors.library.client.service.OrderServiceAsync;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.client.service.UserServiceAsync;
import com.successfactors.library.client.view.ContextAreaFactory;
import com.successfactors.library.client.view.RegisterView;
import com.successfactors.library.client.view.ReaderMainView;
import com.successfactors.library.client.widget.BookDisplayWindow;
import com.successfactors.library.client.widget.LoginBox;
import com.successfactors.library.client.widget.Masthead;
import com.successfactors.library.client.widget.NavigationPane;
import com.successfactors.library.client.widget.NavigationPaneHeader;
import com.successfactors.library.client.widget.StatusInfoBar;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLUser;

public class SFLibrary implements EntryPoint {

	public final static UserServiceAsync userService = GWT.create(UserService.class);
	public final static BookServiceAsync bookService = GWT.create(BookService.class);
	public final static OrderServiceAsync orderService = GWT.create(OrderService.class);
	public final static BorrowServiceAsync borrowService = GWT.create(BorrowService.class);
	
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
	
	private SLUser nowUser;
	
	public void onModuleLoad() {
		
		testWidget();

//		nowUser = null;
//		singleton = this;
//		eventBus = new SimpleEventBus();
//		new AppController();
//		
//		Window.enableScrolling(false);
//		Window.setMargin("0px");
//		
//		mainLayout = new VLayout();
//		mainLayout.setWidth100();
//		mainLayout.setHeight100();
//
//		northLayout = new HLayout();
//		northLayout.setHeight(NORTH_HEIGHT);
//
//		VLayout vLayout = new VLayout();
//		vLayout.addMember(new Masthead());
//		
//		navigationPaneHeader = new NavigationPaneHeader();
//		
//		statusInfoBar = new StatusInfoBar();
//		vLayout.addMember(statusInfoBar);
//		vLayout.addMember(navigationPaneHeader);
//		
//		northLayout.addMember(vLayout);
//		
//		initWestPart();;
//
//		initEastPart();
//
//		southLayout = new HLayout();
//		westLayout.setWidth(WEST_WIDTH);
//		southLayout.setMembers(westLayout, eastLayout);
//
//		mainLayout.addMember(northLayout);
//		mainLayout.addMember(southLayout);
//		
//		mainLayout.draw();
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
		eastLayout = new ReaderMainView();
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
		
		if (nowUser.getUserType().equals("管理员")) {
			
			navigationPane.add("读者",
					ReaderNavigationPaneSectionData.getRecords(),
					new NavigationPaneClickHandler());
			navigationPane.add("管理员",
					AdminNavigationPaneSectionData.getRecords(),
					new NavigationPaneClickHandler());
			
		} else if (nowUser.getUserType().equals("读者")) {
			
			navigationPane.add("读者",
					ReaderNavigationPaneSectionData.getRecords(),
					new NavigationPaneClickHandler());
			
		} else {
			SC.say("出错了==！");
			return;
		}
		
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
	
	public StatusInfoBar getStatusInfoBar() {
		return statusInfoBar;
	}

	public void setStatusInfoBar(StatusInfoBar statusInfoBar) {
		this.statusInfoBar = statusInfoBar;
	}

	public SLUser getNowUser() {
		return nowUser;
	}

	public void setNowUser(SLUser nowUser) {
		this.nowUser = nowUser;
	}
	
	private void testWidget() {
		
		GWT.log("*********** Test Widget Start *************");

		SLBook temp = new SLBook();
		temp.setBookName("冰与火之歌（卷一）");
		temp.setBookAuthor("[美] 乔治·R. R. 马丁");
		temp.setBookISBN("9787536671256");
		temp.setBookClass("小说");
		temp.setBookAvailableQuantity(1);
		temp.setBookContributor("SF");
		temp.setBookInStoreQuantity(1);
		temp.setBookIntro("《冰与火之歌》由美国著名科幻奇幻小说家乔治·R·R·马丁所著，是当代奇幻文学一部影响深远的里程碑式的作品。它于1996年刚一问世，便以别具一格的结构，浩瀚辽阔的视野，错落有致的情节和生动活泼的语言，迅速征服了欧美文坛。迄今，本书已被译为数十种文字，并在各个国家迭获大奖。");
		temp.setBookLanguage("中文");
		//temp.setBookPicUrl("/upload/temppic.jpg");
		temp.setBookPicUrl("http://www.iebook.cn/zhuanti/Jinrongzhenjuan/images/hk.jpg");
		temp.setBookPrice(68.00);
		temp.setBookPublishDate(new Date());
		temp.setBookPublisher("重庆出版社");
		temp.setBookTotalQuantity(1);
		
		BookDisplayWindow bookDisplayWindow = new BookDisplayWindow(temp.getRecord());
		bookDisplayWindow.draw();
		
		GWT.log("***********  Test Widget End  *************");
	}
}
