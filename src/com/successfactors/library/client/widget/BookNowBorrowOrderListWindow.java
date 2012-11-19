package com.successfactors.library.client.widget;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLBorrowDS;
import com.successfactors.library.client.datasource.SLOrderDS;
import com.successfactors.library.shared.model.BookBorrowOrderListInfo;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;

public class BookNowBorrowOrderListWindow extends Window {

	private static final String WINDOW_WIDTH = "620px";
	private static final String WINDOW_HEIGHT = "320px";
	
	private BookBorrowOrderListInfo theInfo;

	private VLayout borrowListLayout;
	private ListGrid borrowListGrid;
	
	private VLayout orderListLayout;
	private ListGrid orderListGrid;

	private SLBorrowDS borrowDS;
	private SLOrderDS orderDS;
	
	public BookNowBorrowOrderListWindow(String strBookName, BookBorrowOrderListInfo bookBorrowOrderListInfo) {
		super();
		this.theInfo = bookBorrowOrderListInfo;

		this.setAutoSize(true);
		this.setTitle("《"+strBookName+"》" + "的当前借阅及预订队列");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		initBorrowListLayout();
		initOrderListLayout();
		
		HLayout hLayout = new HLayout();
		hLayout.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		hLayout.setMembers(borrowListLayout, orderListLayout);
		hLayout.setMembersMargin(5);
		
		this.addItem(hLayout);
		
		bind();
	}
	
	private void bind() {
		borrowListGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				SC.say("你想干嘛？");
			}
		});
		orderListGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				SC.say("你想干嘛？");
			}
		});
	}

	private void initBorrowListLayout() {
		
		borrowDS = new SLBorrowDS();
		for (SLBorrow borrow : theInfo.getTheBorrows()) {
			borrowDS.addData(borrow.getRecord());
		}

		Label headerItem1 = new Label("当前借阅");
		headerItem1.setStyleName("alex_header_label");
		headerItem1.setHeight(20);
		
		borrowListLayout = new VLayout();
		
		borrowListGrid = new ListGrid();
		ListGridField userNameField = new ListGridField("userName", "借书人");
		ListGridField borrowDateField = new ListGridField("borrowDate", "借书日期");
		ListGridField shouldReturnDateField = new ListGridField("shouldReturnDate", "应还日期");
		borrowListGrid.setFields(userNameField, borrowDateField, shouldReturnDateField);
		
		borrowListGrid.setDataSource(borrowDS);
		borrowListGrid.setAutoFetchData(true);
		
		borrowListLayout.setMargin(12);
		borrowListLayout.setPadding(14);
		borrowListLayout.setWidth("40%");
		borrowListLayout.setStyleName("alex_myDecoratorPanel");
		borrowListLayout.setMembers(headerItem1, borrowListGrid);
		borrowListLayout.setMembersMargin(5);
	}

	private void initOrderListLayout() {

		orderDS = new SLOrderDS();
		for (SLOrder order : theInfo.getTheOrders()) {
			orderDS.addData(order.getRecord());
		}
		
		Label headerItem2 = new Label("当前预订");
		headerItem2.setStyleName("alex_header_label");
		headerItem2.setHeight(20);
		
		orderListLayout = new VLayout();
		
		orderListGrid = new ListGrid();
		ListGridField userNameField = new ListGridField("userName", "预订人");
		ListGridField orderDateField = new ListGridField("orderDate", "预订时间");
		orderListGrid.setFields(userNameField, orderDateField);
		
		orderListGrid.setDataSource(orderDS);
		orderListGrid.setAutoFetchData(true);
		
		orderListLayout.setMargin(12);
		orderListLayout.setPadding(14);
		orderListLayout.setWidth("40%");
		orderListLayout.setStyleName("alex_myDecoratorPanel");
		orderListLayout.setMembers(headerItem2, orderListGrid);
		orderListLayout.setMembersMargin(5);
	}
	
}
