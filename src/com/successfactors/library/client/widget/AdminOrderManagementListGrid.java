package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.orderService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.successfactors.library.client.datasource.SLOrderDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.OrderSearchType;
import com.successfactors.library.shared.OrderStatusType;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;

public class AdminOrderManagementListGrid extends ListGrid implements OrderEditWindow.FinishEditOrder {
	
	public static final int DEFAULT_RECORDS_EACH_PAGE = 10;
	public static final int DEFAULT_IMG_HEIGHT = 40;
	public static final int DEFAULT_IMG_WIDTH = 28;

	public static final int DEFAULT_CELL_HEIGHT = 42;
	
	private Refreshable jumpBar;
	
	private SLOrderDS slOrderDS = new SLOrderDS();
	private int pageNowNum = 1;
	private int pageTotalNum = 1;
	
	public AdminOrderManagementListGrid(Refreshable jumpbar) {
		super();
		jumpBar = jumpbar;
		
		GWT.log("初始化: AdminOrderManagementListGrid");
		
		this.setShowAllRecords(true);
		this.setSortField("bookClass");
		this.setCellHeight(DEFAULT_CELL_HEIGHT);
		
		ListGridField bookPicUrlField = new ListGridField("bookPicUrl", "封面", 60);
		bookPicUrlField.setImageURLPrefix("/images/upload/");
		bookPicUrlField.setType(ListGridFieldType.IMAGE);
		bookPicUrlField.setImageHeight(DEFAULT_IMG_HEIGHT);
		bookPicUrlField.setImageWidth(DEFAULT_IMG_WIDTH);

		ListGridField orderIdField = new ListGridField("orderId", "预订编号", 100);
		ListGridField bookNameField = new ListGridField("bookName", "书名");
		ListGridField bookISBNField = new ListGridField("bookISBN", "ISBN");
		ListGridField userNameField = new ListGridField("userName", "预订人");
		
		ListGridField orderDateField = new ListGridField("orderDate", "预订日期");
		ListGridField statusField = new ListGridField("status", "状态");
		
		this.setFields(
				bookPicUrlField,
				orderIdField,
				bookNameField,
				bookISBNField,
				userNameField,
				orderDateField,
				statusField
				);
		
		updateDS(DEFAULT_RECORDS_EACH_PAGE, 1);
		this.setDataSource(slOrderDS);
		this.setAutoFetchData(true);
		
		bind();
		
	}
	
	private void bind() {
		this.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				OrderEditWindow orderEditWindow = new OrderEditWindow(getSelectedRecord(), getSelf());
				orderEditWindow.show();
			}
		});
	}
	
	private void updateDS(final int itemsPerPage, final int pageNum) {
		new RPCCall<OrderPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}
			@Override
			public void onSuccess(OrderPage result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLOrder order : result.getTheOrders()) {
					slOrderDS.addData(order.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}
			@Override
			protected void callService(AsyncCallback<OrderPage> cb) {
				orderService.getOrderList(OrderStatusType.NOW, null, itemsPerPage, pageNum, cb);
			}
		}.retry(3);
	}

	public void doSearchOrder(final String[] searchInfo) {
		
		for (Record record : this.getRecords()) {
			slOrderDS.removeData(record);
		}
		
		new RPCCall<OrderPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}
			@Override
			public void onSuccess(OrderPage result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLOrder order : result.getTheOrders()) {
					slOrderDS.addData(order.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}
			@Override
			protected void callService(AsyncCallback<OrderPage> cb) {
				orderService.searchOrderList(OrderStatusType.NOW, OrderSearchType.parse(searchInfo[1]), searchInfo[0], DEFAULT_RECORDS_EACH_PAGE, 1, cb);
			}
		}.retry(3);
		
	}

	public void doNextPage(boolean isSearchMode, final String[] searchInfo) {
		
		if (pageNowNum >= pageTotalNum) {
			SC.say("已到最后一页！");
			return;
		}
		
		for (Record record : this.getRecords()) {
			slOrderDS.removeData(record);
		}
		
		if (isSearchMode) {
			
			new RPCCall<OrderPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}
				@Override
				public void onSuccess(OrderPage result) {
					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLOrder order : result.getTheOrders()) {
						slOrderDS.addData(order.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}
				@Override
				protected void callService(AsyncCallback<OrderPage> cb) {
					orderService.searchOrderList(OrderStatusType.NOW, OrderSearchType.parse(searchInfo[1]), searchInfo[0], DEFAULT_RECORDS_EACH_PAGE, pageNowNum+1, cb);
				}
			}.retry(3);
			
		} else {
			new RPCCall<OrderPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}
				@Override
				public void onSuccess(OrderPage result) {
					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLOrder order : result.getTheOrders()) {
						slOrderDS.addData(order.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}
				@Override
				protected void callService(AsyncCallback<OrderPage> cb) {
					orderService.getOrderList(OrderStatusType.NOW, null, DEFAULT_RECORDS_EACH_PAGE, pageNowNum+1, cb);
				}
			}.retry(3);
		}
		
	}

	public void doPrePage(boolean isSearchMode, final String[] searchInfo) {

		if (pageNowNum <= 1) {
			SC.say("已到第一页！");
			return;
		}
		
		for (Record record : this.getRecords()) {
			slOrderDS.removeData(record);
		}
		
		if (isSearchMode) {
			
			new RPCCall<OrderPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}
				@Override
				public void onSuccess(OrderPage result) {
					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLOrder order : result.getTheOrders()) {
						slOrderDS.addData(order.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}
				@Override
				protected void callService(AsyncCallback<OrderPage> cb) {
					orderService.searchOrderList(OrderStatusType.NOW, OrderSearchType.parse(searchInfo[1]), searchInfo[0], DEFAULT_RECORDS_EACH_PAGE, pageNowNum-1, cb);
				}
			}.retry(3);
			
		} else {
			
			new RPCCall<OrderPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}
				@Override
				public void onSuccess(OrderPage result) {
					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLOrder order : result.getTheOrders()) {
						slOrderDS.addData(order.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}
				@Override
				protected void callService(AsyncCallback<OrderPage> cb) {
					orderService.getOrderList(OrderStatusType.NOW, null, DEFAULT_RECORDS_EACH_PAGE, pageNowNum-1, cb);
				}
			}.retry(3);
		}
	}

	@Override
	public void doRefreshPage() {
		for (Record record : this.getRecords()) {
			slOrderDS.removeData(record);
		}
		new RPCCall<OrderPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}
			@Override
			public void onSuccess(OrderPage result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLOrder order : result.getTheOrders()) {
					slOrderDS.addData(order.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}
			@Override
			protected void callService(AsyncCallback<OrderPage> cb) {
				orderService.getOrderList(OrderStatusType.NOW, null, DEFAULT_RECORDS_EACH_PAGE, pageNowNum, cb);
			}
		}.retry(3);
	}
	
	private AdminOrderManagementListGrid getSelf() {
		return this;
	}

}
