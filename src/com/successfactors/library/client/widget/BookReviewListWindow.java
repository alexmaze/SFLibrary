package com.successfactors.library.client.widget;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLBookReviewDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.service.BookReviewService;
import com.successfactors.library.client.service.BookReviewServiceAsync;
import com.successfactors.library.shared.model.PageDataWrapper;
import com.successfactors.library.shared.model.SLBookReview;

public class BookReviewListWindow extends Window {
	
	private BookReviewServiceAsync bookReviewService = GWT.create(BookReviewService.class);
	

	public static final int DEFAULT_RECORDS_EACH_PAGE = 1000;

	private static final String WINDOW_WIDTH = "620px";
	private static final String WINDOW_HEIGHT = "420px";

	private PageDataWrapper<ArrayList<SLBookReview>> theInfo;
	private SLBookReviewDS theDataSource;
	private ListGrid theListGrid;
	private IButton addButton;
	
	private String bookISBN;
	

	public BookReviewListWindow(String bookISBN, String bookName) {
		super();
		
		this.bookISBN = bookISBN;
		
		this.setAutoSize(true);
		this.setTitle("《"+bookName+"》" + "的书评列表");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		theDataSource = new SLBookReviewDS();
		Label headerItem1 = new Label("当前书评");
		headerItem1.setStyleName("alex_header_label");
		headerItem1.setHeight(20);
		
		VLayout theLayout = new VLayout();
		
		theListGrid = new ListGrid();
		ListGridField iconField = new ListGridField("icon", "#", 20);
		iconField.setType(ListGridFieldType.IMAGE);
		ListGridField titleField = new ListGridField("title", "标题");
		ListGridField subTitleField = new ListGridField("subTitle", "副标题");
		//ListGridField contentField = new ListGridField("content", "内容");
		ListGridField postDateeField = new ListGridField("postDate", "发表日期",120);
		theListGrid.setFields(iconField, titleField, subTitleField, postDateeField);
		
		theListGrid.setDataSource(theDataSource);
		theListGrid.setAutoFetchData(true);
		
		addButton = new IButton("发表书评");
		addButton.setIcon("icons/16/add.png");
		
		theLayout.setMargin(12);
		theLayout.setPadding(14);
		theLayout.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		theLayout.setStyleName("alex_myDecoratorPanel");
		theLayout.setMembers(headerItem1, theListGrid, addButton);
		theLayout.setMembersMargin(5);
		
		this.addItem(theLayout);
		
		updateData();
		bind();
		
	}



	private void updateData() {
		new RPCCall<PageDataWrapper<ArrayList<SLBookReview>>>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(PageDataWrapper<ArrayList<SLBookReview>> result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				theInfo = result;
				
				for (SLBookReview slBookReview : theInfo.getData()) {
					theDataSource.addData(slBookReview.toRecord());
				}
			}

			@Override
			protected void callService(
					AsyncCallback<PageDataWrapper<ArrayList<SLBookReview>>> cb) {
				bookReviewService.getBookReviewListByBookISBN(bookISBN, DEFAULT_RECORDS_EACH_PAGE, 1, cb);
			}
		}.retry(3);
	}



	private void bind() {
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		theListGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
}
