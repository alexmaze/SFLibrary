package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;
import static com.successfactors.library.client.SFLibrary.borrowService;
import static com.successfactors.library.client.SFLibrary.orderService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
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
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.datasource.SLBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.widget.BookEditWindow.FinishEditBook;
import com.successfactors.library.shared.model.BookBorrowOrderListInfo;
import com.successfactors.library.shared.model.SLBook;

public class BookDisplayWindow  extends Window {

	private static final String WINDOW_WIDTH = "580px";
	private static final String WINDOW_HEIGHT = "360px";
	private static final int IMG_HEIGHT = 165;
	private static final int IMG_WIDTH = 116;
	
	private Record theRecord;
	private SLBookDS theDataSource;

	private IButton orderButton;
	private IButton borrowButton;
	private IButton borrowOrderListButton;
	
	private FinishEditBook finishEdit;
	
	public BookDisplayWindow(SLBook bookRec, FinishEditBook finish) {
		super();
		this.theRecord = bookRec.getRecord();
		this.theDataSource = new SLBookDS();
		this.theDataSource.addData(theRecord);
		finishEdit = finish;
		initDisplayWindow();
	}
	
	public BookDisplayWindow(Record bookRec, FinishEditBook finish) {
		super();
		this.theRecord = bookRec;
		this.theDataSource = new SLBookDS();
		this.theDataSource.addData(theRecord);
		finishEdit = finish;
		initDisplayWindow();
	}
	
	private void initDisplayWindow() {
		
		String strBookName = theRecord.getAttributeAsString("bookName");
		
		this.setAutoSize(true);
		this.setTitle("《"+strBookName+"》"+"详细信息");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout;
		HLayout hLayout;
		DynamicForm bookForm1;
		DynamicForm bookForm2;
		DynamicForm bookForm3;
		HLayout buttonLayout;
		
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
		bookForm1 = new DynamicForm();
		bookForm1.setDataSource(theDataSource);
		bookForm1.setNumCols(4);
		bookForm1.setWidth("*");
		bookForm1.setColWidths(100, "*", 100, "*");
		bookForm1.setCellPadding(5);
		
		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(2);
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookAuthorItem = new StaticTextItem("bookAuthor", "作者");
		bookAuthorItem.setColSpan(2);
		bookAuthorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookAuthorItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookISBNItem = new StaticTextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(2);
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookPublisherItem = new StaticTextItem("bookPublisher", "出版社");
		bookPublisherItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublisherItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookPublishDateItem = new StaticTextItem("bookPublishDate", "出版日期");
		bookPublishDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublishDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookClassItem = new StaticTextItem("bookClass", "类别");
		bookClassItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookClassItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookLanguageItem = new StaticTextItem("bookLanguage", "语言");
		bookLanguageItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookLanguageItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookContributorItem = new StaticTextItem("bookContributor", "贡献者");
		bookContributorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookContributorItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookPriceItem = new StaticTextItem("bookPrice", "价格");
		bookPriceItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPriceItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		bookForm1.setFields(
				bookNameItem, 
				bookAuthorItem, 
				bookISBNItem,
				bookPublisherItem, 
				bookPublishDateItem,
				bookClassItem,
				bookLanguageItem,
				bookContributorItem,
				bookPriceItem);
				
		bookForm1.selectRecord(theRecord);
		bookForm1.fetchData();
		
		//Form 2-----------------------------------------------------------------------------------------
		bookForm2 = new DynamicForm();
		bookForm2.setDataSource(theDataSource);
		bookForm2.setNumCols(3);
		bookForm2.setWidth(WINDOW_WIDTH);
		bookForm2.setColWidths("*","*","*");
		bookForm2.setCellPadding(3);
		bookForm2.setCellBorder(1);
		bookForm2.setTitleOrientation(TitleOrientation.TOP);

		StaticTextItem bookTotalQuantityItemTitle = new StaticTextItem("bookTotalQuantityTitle", "");
		bookTotalQuantityItemTitle.setTextBoxStyle("alex_bookdisplaywindow_form_header");
		bookTotalQuantityItemTitle.setShowTitle(false);

		StaticTextItem bookInStoreQuantityitemTitle = new StaticTextItem("bookInStoreQuantityTitle", "");
		bookInStoreQuantityitemTitle.setTextBoxStyle("alex_bookdisplaywindow_form_header");
		bookInStoreQuantityitemTitle.setShowTitle(false);

		StaticTextItem bookAvailableQuantityItemTitle = new StaticTextItem("bookAvailableQuantityTitle", "");
		bookAvailableQuantityItemTitle.setTextBoxStyle("alex_bookdisplaywindow_form_header");
		bookAvailableQuantityItemTitle.setShowTitle(false);
		
		StaticTextItem bookTotalQuantityItem = new StaticTextItem("bookTotalQuantity", "");
		bookTotalQuantityItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		bookTotalQuantityItem.setShowTitle(false);

		StaticTextItem bookInStoreQuantityitem = new StaticTextItem("bookInStoreQuantity", "");
		bookInStoreQuantityitem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		bookInStoreQuantityitem.setShowTitle(false);

		StaticTextItem bookAvailableQuantityItem = new StaticTextItem("bookAvailableQuantity", "");
		bookAvailableQuantityItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		bookAvailableQuantityItem.setShowTitle(false);
		
		bookForm2.setFields(
				bookTotalQuantityItemTitle,
				bookInStoreQuantityitemTitle,
				bookAvailableQuantityItemTitle,
				bookTotalQuantityItem,
				bookInStoreQuantityitem,
				bookAvailableQuantityItem
				);
		
		bookForm2.selectRecord(theRecord);
		bookForm2.fetchData();
		
		//Form 3-----------------------------------------------------------------------------------------
		bookForm3 = new DynamicForm();
		bookForm3.setDataSource(theDataSource);
		bookForm3.setWidth(WINDOW_WIDTH);
		bookForm3.setCellPadding(3);
		bookForm3.setTitleOrientation(TitleOrientation.TOP);

		StaticTextItem bookIntroItemTitle = new StaticTextItem("bookIntroTitle", "");
		bookIntroItemTitle.setTextBoxStyle("alex_bookdisplaywindow_form_text_title");
		bookIntroItemTitle.setShowTitle(false);

		StaticTextItem bookIntroItem = new StaticTextItem("bookIntro", "");
		bookIntroItem.setTextBoxStyle("alex_bookdisplaywindow_form_intro_content");
		bookIntroItem.setShowTitle(false);
		bookIntroItem.setColSpan(2);

		bookForm3.setFields(bookIntroItemTitle, bookIntroItem);
		
		bookForm3.selectRecord(theRecord);
		bookForm3.fetchData();
		
		//buttonLayout --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		orderButton = new IButton("预订本书");
		orderButton.setIcon("actions/approve.png");
		borrowButton = new IButton("借阅本书");
		borrowButton.setIcon("actions/approve.png");
		
		borrowOrderListButton = new IButton("当前借阅与预订");
		borrowOrderListButton.setIcon("icons/16/reports.png");
		borrowOrderListButton.setWidth("150px");
		
		buttonLayout.setMembers(borrowOrderListButton, orderButton, borrowButton);
		buttonLayout.setAlign(Alignment.RIGHT);
		
		int bookAvailableQuantity = theRecord.getAttributeAsInt("bookAvailableQuantity");
		orderButton.setVisible(bookAvailableQuantity <= 0);
		borrowButton.setVisible(bookAvailableQuantity > 0);
		
		hLayout.setMembers(bookPicUrlItem, bookForm1);
		vLayout.setMembers(hLayout, bookForm2, bookForm3, buttonLayout);
		vLayout.setMembersMargin(20);

		this.addItem(vLayout);
		
		bind();
	}
	
	private void bind() {
		orderButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (SFLibrary.get().getNowUser() == null) {
					SC.say("请先登录！");
					return;
				}
				new RPCCall<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == false) {
							SC.say("预定失败，您正在借阅或已预订本书！");
							return;
						}
						SC.say("预定成功");
						if (finishEdit != null) {
							finishEdit.doRefreshPage();
						}
						destroy();
					}

					@Override
					protected void callService(AsyncCallback<Boolean> cb) {
						orderService.orderBook(theRecord.getAttribute("bookISBN"), cb);
					}
				}.retry(3);
			}
		});
		borrowButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (SFLibrary.get().getNowUser() == null) {
					SC.say("请先登录！");
					return;
				}
				new RPCCall<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == false) {
							SC.say("借阅失败，无可借数量或您正在借阅本书！");
							return;
						}
						SC.say("借阅成功\n" +
								"请尽快到图书管理员处领取");
						if (finishEdit != null) {
							finishEdit.doRefreshPage();
						}
						destroy();
					}

					@Override
					protected void callService(AsyncCallback<Boolean> cb) {
						borrowService.borrowBook(theRecord.getAttribute("bookISBN"), cb);
					}
				}.retry(3);
			}
		});
		borrowOrderListButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				new RPCCall<BookBorrowOrderListInfo>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(BookBorrowOrderListInfo result) {
						if (result == null) {
							SC.say("当前无任何预订与借阅信息！");
							return;
						}
						// 显示当前借阅预订队列
						(new BookNowBorrowOrderListWindow(theRecord.getAttribute("bookName"), result)).show();
					}

					@Override
					protected void callService(AsyncCallback<BookBorrowOrderListInfo> cb) {
						bookService.getBookNowBorrowOrderListByISBN(theRecord.getAttribute("bookISBN"), cb);
					}
				}.retry(3);
			}
		});
	}
	
}
