package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.borrowService;

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
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLBorrowDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLBorrow;

public class BorrowEditWindow  extends Window {

	private static final String WINDOW_WIDTH = "520px";
	private static final String WINDOW_HEIGHT = "240px";
	private static final int IMG_HEIGHT = 180;
	private static final int IMG_WIDTH = 125;
	
	private Record theRecord;
	private SLBorrowDS theDataSource;
	
	private IButton returnButton;
	
	public BorrowEditWindow() {
		super();
//		this.theRecord = borrowRec.getRecord();
		this.theDataSource = new SLBorrowDS();
//		this.theDataSource.addData(theRecord);
		initNewWindow();
	}

	public BorrowEditWindow(SLBorrow borrowRec) {
		super();
		this.theRecord = borrowRec.getRecord();
		this.theDataSource = new SLBorrowDS();
		this.theDataSource.addData(theRecord);
		initEditWindow();
	}
	
	public BorrowEditWindow(Record borrowRec) {
		super();
		this.theRecord = borrowRec;
		this.theDataSource = new SLBorrowDS();
		this.theDataSource.addData(theRecord);
		initEditWindow();
	}
	
	private void initNewWindow() {
		
		this.setAutoSize(true);
		this.setTitle("借阅记录");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout;
		final DynamicForm boorowForm1;
		
		vLayout = new VLayout();
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);
		
		//Form 1-----------------------------------------------------------------------------------------
		boorowForm1 = new DynamicForm();
		boorowForm1.setDataSource(theDataSource);
		boorowForm1.setNumCols(4);
		boorowForm1.setWidth(WINDOW_WIDTH);
		boorowForm1.setColWidths(100, "*", 100, "*");
		boorowForm1.setCellPadding(5);
		
		final TextItem borrowIdItem = new TextItem("borrowId", "借阅编号");
		borrowIdItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		borrowIdItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		borrowIdItem.setShowIcons(true);
		FormItemIcon borrowBut = new FormItemIcon();
		borrowBut.setSrc("/images/actions/approve.png");
		borrowIdItem.setIcons(borrowBut);
		borrowIdItem.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {

				new RPCCall<SLBorrow>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(SLBorrow result) {
						if (result == null) {
							SC.say("输入错误");
							return;
						}
						setTitle("借阅记录"+result.getTheBook().getBookName());
						theRecord = result.getRecord();
						theDataSource.addData(theRecord);
						boorowForm1.selectRecord(theRecord);
						boorowForm1.fetchData();
					}

					@Override
					protected void callService(AsyncCallback<SLBorrow> cb) {
						borrowService.getBorrowInfo(Integer.parseInt(borrowIdItem.getValueAsString()), cb);
					}
				}.retry(3);
			}
		});
		
		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(2);
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookISBNItem = new StaticTextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(2);
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem userNameItem = new StaticTextItem("userName", "借书人");
		userNameItem.setColSpan(2);
		userNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem userEmailItem = new StaticTextItem("userEmail", "Email");
		userEmailItem.setColSpan(2);
		userEmailItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userEmailItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem borrowDateItem = new StaticTextItem("borrowDate", "借书日期");
//		borrowDateItem.setColSpan(2);
		borrowDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		borrowDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem shouldReturnDatetem = new StaticTextItem("shouldReturnDate", "应还日期");
//		shouldReturnDatetem.setColSpan(2);
		shouldReturnDatetem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		shouldReturnDatetem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem returnDateItem = new StaticTextItem("returnDate", "归还日期");
//		returnDateItem.setColSpan(2);
		returnDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		returnDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem inStoreItem = new StaticTextItem("inStore", "需要领取");
//		inStoreItem.setColSpan(2);
		inStoreItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		inStoreItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem overdueItem = new StaticTextItem("overdue", "是否超期");
//		overdueItem.setColSpan(2);
		overdueItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		overdueItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem statusItem = new StaticTextItem("status", "借阅状态");
//		statusItem.setColSpan(2);
		statusItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		statusItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		boorowForm1.setFields(borrowIdItem,
				bookNameItem, 
				bookISBNItem, 
				userNameItem, 
				userEmailItem,
				borrowDateItem,
				shouldReturnDatetem,
				returnDateItem,
				statusItem,
				inStoreItem,
				overdueItem
				);
				
		//buttonLayout --------------------------------------------------------------------------------------
		HLayout buttonLayout = new HLayout();
		returnButton = new IButton("归还");
		buttonLayout.setMembers(returnButton);
		buttonLayout.setAlign(Alignment.RIGHT);
		
		vLayout.setMembers(boorowForm1, buttonLayout);
		
		this.addItem(vLayout);
		
		bind();
	}
	
	private void initEditWindow() {

		String strBorrowId = theRecord.getAttributeAsString("borrowId");
		
		this.setAutoSize(true);
		this.setTitle("借阅记录"+strBorrowId);
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
		Img bookPicUrlItem = new Img("/images/upload/"+strBookPicUrl, IMG_WIDTH, IMG_HEIGHT);
		
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

		StaticTextItem userNameItem = new StaticTextItem("userName", "借书人");
		userNameItem.setColSpan(2);
		userNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem userEmailItem = new StaticTextItem("userEmail", "Email");
		userEmailItem.setColSpan(2);
		userEmailItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userEmailItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem borrowDateItem = new StaticTextItem("borrowDate", "借书日期");
//		borrowDateItem.setColSpan(2);
		borrowDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		borrowDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem shouldReturnDatetem = new StaticTextItem("shouldReturnDate", "应还日期");
//		shouldReturnDatetem.setColSpan(2);
		shouldReturnDatetem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		shouldReturnDatetem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem returnDateItem = new StaticTextItem("returnDate", "归还日期");
//		returnDateItem.setColSpan(2);
		returnDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		returnDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem inStoreItem = new StaticTextItem("inStore", "需要领取");
//		inStoreItem.setColSpan(2);
		inStoreItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		inStoreItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem overdueItem = new StaticTextItem("overdue", "是否超期");
//		overdueItem.setColSpan(2);
		overdueItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		overdueItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem statusItem = new StaticTextItem("status", "借阅状态");
//		statusItem.setColSpan(2);
		statusItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		statusItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		boorowForm1.setFields(
				bookNameItem, 
				bookISBNItem, 
				userNameItem, 
				userEmailItem,
				borrowDateItem,
				shouldReturnDatetem,
				returnDateItem,
				statusItem,
				inStoreItem,
				overdueItem
				);
				
		boorowForm1.selectRecord(theRecord);
		boorowForm1.fetchData();
		
		hLayout.setMembers(bookPicUrlItem, boorowForm1);
		
		//buttonLayout --------------------------------------------------------------------------------------
		HLayout buttonLayout = new HLayout();
		returnButton = new IButton("归还");
		buttonLayout.setMembers(returnButton);
		buttonLayout.setAlign(Alignment.RIGHT);
		
		vLayout.setMembers(hLayout, buttonLayout);
//		vLayout.setMembersMargin(20);
		
		this.addItem(vLayout);
		
		bind();
	}
	
	private void bind() {
		returnButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (theRecord == null) {
					SC.say("请输入借阅编号");
					return;
				}
				SC.say("归还图书"+theRecord.getAttribute("bookName"));
			}
		});
	}
}
