package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.orderService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLOrderDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLOrder;

public class OrderEditWindow  extends Window {

	public interface FinishEditOrder {
		void doRefreshPage();
	}
	private static final String WINDOW_WIDTH = "450px";
	private static final String WINDOW_HEIGHT = "220px";
	private static final int IMG_HEIGHT = 150;
	private static final int IMG_WIDTH = 104;
	
	private Record theRecord;
	private SLOrderDS theDataSource;
	private IButton deleteButton;
	
	private FinishEditOrder finishEdit;
	
	public OrderEditWindow(SLOrder orderRec, FinishEditOrder finish) {
		super();
		this.theRecord = orderRec.getRecord();
		this.theDataSource = new SLOrderDS();
		this.theDataSource.addData(theRecord);
		this.finishEdit = finish;
		initDisplayWindow();
	}
	
	public OrderEditWindow(Record orderRec, FinishEditOrder finish) {
		super();
		this.theRecord = orderRec;
		this.theDataSource = new SLOrderDS();
		this.theDataSource.addData(theRecord);
		this.finishEdit = finish;
		initDisplayWindow();
	}
	
	private void initDisplayWindow() {

		String strOrderId = theRecord.getAttributeAsString("orderId");
		
		this.setAutoSize(true);
		this.setTitle("预定记录"+strOrderId);
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout;
		HLayout hLayout;
		DynamicForm boorowForm1;
		
		vLayout = new VLayout();
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);
		
		hLayout = new HLayout();
		hLayout.setWidth(WINDOW_WIDTH);
		
		String strBookPicUrl = theRecord.getAttributeAsString("bookPicUrl");
		Img bookPicUrlItem = new Img(strBookPicUrl, IMG_WIDTH, IMG_HEIGHT);
		
		//Form 1-----------------------------------------------------------------------------------------
		boorowForm1 = new DynamicForm();
		boorowForm1.setDataSource(theDataSource);
		boorowForm1.setNumCols(4);
		boorowForm1.setWidth("*");
		boorowForm1.setColWidths(100, "*", 100, "*");
		boorowForm1.setCellPadding(5);
		
		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(2);
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookISBNItem = new StaticTextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(2);
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem userNameItem = new StaticTextItem("userName", "预订人");
		userNameItem.setColSpan(2);
		userNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem userEmailItem = new StaticTextItem("userEmail", "Email");
		userEmailItem.setColSpan(2);
		userEmailItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userEmailItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem borrowDateItem = new StaticTextItem("orderDate", "预订时间");
		borrowDateItem.setColSpan(2);
		borrowDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		borrowDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		

		StaticTextItem statusItem = new StaticTextItem("status", "预订状态");
		statusItem.setColSpan(2);
		statusItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		statusItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		boorowForm1.setFields(
				bookNameItem, 
				bookISBNItem, 
				userNameItem, 
				userEmailItem,
				borrowDateItem,
				statusItem
				);
				
		boorowForm1.selectRecord(theRecord);
		boorowForm1.fetchData();
		
		hLayout.setMembers(bookPicUrlItem, boorowForm1);
		
		HLayout buttonLayout = new HLayout();
		deleteButton = new IButton("取消预订");
		buttonLayout.setMembers(deleteButton);
		buttonLayout.setAlign(Alignment.RIGHT);
		
		vLayout.setMembers(hLayout, buttonLayout);
//		vLayout.setMembersMargin(20);

		this.addItem(vLayout);
		
		bind();
		
	}
	
	private void bind() {
		deleteButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				new RPCCall<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}
					@Override
					public void onSuccess(Boolean result) {
						if (result == false) {
							SC.say("取消失败，请稍后重试！");
							return;
						}
						SC.say("取消预定成功");
						if (finishEdit != null) {
							finishEdit.doRefreshPage();
						}
						destroy();
					}
					@Override
					protected void callService(AsyncCallback<Boolean> cb) {
						orderService.cancelOrder(theRecord.getAttributeAsInt("orderId"), cb);
					}
				}.retry(3);
			}
		});
	}
	
}
