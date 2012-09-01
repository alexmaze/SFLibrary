package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.successfactors.library.client.datasource.SLBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLBook;

public class AdminBookManagementListGrid extends ListGrid {
	
	public static final int DEFAULT_RECORDS_EACH_PAGE = 10;
	public static final int DEFAULT_IMG_HEIGHT = 76;
	public static final int DEFAULT_IMG_WIDTH = 53;
	
	private SLBookDS slBookDS = new SLBookDS();
	private int pageNo = 0;
	
	public AdminBookManagementListGrid() {
		super();
		
		GWT.log("初始化: AdminBookManagementListGrid");
		
		this.setShowAllRecords(true);
		this.setSortField("bookClass");
		this.setCellHeight(80);
		
		ListGridField bookPicUrlField = new ListGridField("bookPicUrl", "封面");
		bookPicUrlField.setImageURLPrefix("/upload/");
		bookPicUrlField.setType(ListGridFieldType.IMAGE);
		bookPicUrlField.setImageHeight(DEFAULT_IMG_HEIGHT);
		bookPicUrlField.setImageWidth(DEFAULT_IMG_WIDTH);
		
		ListGridField bookNameField = new ListGridField("bookName", "书名");
		ListGridField bookAuthorField = new ListGridField("bookAuthor", "作者");
		ListGridField bookISBNField = new ListGridField("bookISBN", "ISBN");

		ListGridField bookLanguageField = new ListGridField("bookLanguage", "语言");
		
		ListGridField bookClassField = new ListGridField("bookClass", "类别");
		ListGridField bookTotalQuantityField = new ListGridField("bookTotalQuantity", "总数");
		ListGridField bookInStoreQuantityField = new ListGridField("bookInStoreQuantity", "库中数量");
		ListGridField bookAvailableQuantityField = new ListGridField("bookAvailableQuantity", "可借数量");
		ListGridField bookIntroField = new ListGridField("bookIntro", "简介");

//		bookPicUrlField.setAutoFitWidth(true);
//		bookNameField.setAutoFitWidth(true);
//		bookAuthorField.setAutoFitWidth(true);
//		bookLanguageField.setAutoFitWidth(true);
//		bookClassField.setAutoFitWidth(true);
//		bookTotalQuantityField.setAutoFitWidth(true);
//		bookInStoreQuantityField.setAutoFitWidth(true);
//		bookAvailableQuantityField.setAutoFitWidth(true);
//		bookIntroField.setAutoFitWidth(true);

		bookIntroField.setType(ListGridFieldType.TEXT);
		
		this.setFields(
				bookPicUrlField,
				bookNameField,
				bookAuthorField,
				bookISBNField,
				bookLanguageField,
				bookClassField,
				bookTotalQuantityField,
				bookInStoreQuantityField,
				bookAvailableQuantityField,
				bookIntroField
				);
		
		updateDS(pageNo, pageNo+DEFAULT_RECORDS_EACH_PAGE);
		this.setDataSource(slBookDS);
		this.setAutoFetchData(true);
		
	}
	
	private void updateDS(final int iStart, final int iEnd) {
		new RPCCall<ArrayList<SLBook>>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}
			@Override
			public void onSuccess(ArrayList<SLBook> result) {
				if (result == null || result.isEmpty()) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLBook slBook : result) {
					slBookDS.addData(slBook.getRecord());
				}
			}
			@Override
			protected void callService(AsyncCallback<ArrayList<SLBook>> cb) {
				bookService.getAllBookList(iStart, iEnd, cb);
			}
		}.retry(3);
	}
	
}
