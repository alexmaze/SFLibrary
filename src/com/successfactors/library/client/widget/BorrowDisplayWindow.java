package com.successfactors.library.client.widget;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLBorrowDS;
import com.successfactors.library.shared.model.SLBorrow;

public class BorrowDisplayWindow  extends Window {

	private static final String WINDOW_WIDTH = "520px";
	private static final String WINDOW_HEIGHT = "220px";
	private static final int IMG_HEIGHT = 180;
	private static final int IMG_WIDTH = 125;
	
	private Record theRecord;
	private SLBorrowDS theDataSource;
	
	public BorrowDisplayWindow(SLBorrow borrowRec) {
		super();
		this.theRecord = borrowRec.getRecord();
		this.theDataSource = new SLBorrowDS();
		this.theDataSource.addData(theRecord);
		initDisplayWindow();
	}
	
	public BorrowDisplayWindow(Record borrowRec) {
		super();
		this.theRecord = borrowRec;
		this.theDataSource = new SLBorrowDS();
		this.theDataSource.addData(theRecord);
		initDisplayWindow();
	}
	
	private void initDisplayWindow() {

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

		CheckboxItem inStoreItem = new CheckboxItem("inStore", "需要领取");
//		inStoreItem.setColSpan(2);
		inStoreItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		inStoreItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		inStoreItem.setCanEdit(false);

		CheckboxItem overdueItem = new CheckboxItem("overdue", "是否超期");
//		overdueItem.setColSpan(2);
		overdueItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		overdueItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		overdueItem.setCanEdit(false);

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
		vLayout.setMembers(hLayout);
//		vLayout.setMembersMargin(20);

		this.addItem(vLayout);
		
	}
	
}
