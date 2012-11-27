package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.recommendedBookService;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.successfactors.library.client.datasource.SLRecommendedBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendedBook;

public class AdminRecBookManagementListGrid extends ListGrid implements RecommendedBookWindow.FinishEditBook {

	public static final int DEFAULT_RECORDS_EACH_PAGE = 16;
	public static final int DEFAULT_IMG_HEIGHT = 40;
	public static final int DEFAULT_IMG_WIDTH = 28;

	public static final int DEFAULT_CELL_HEIGHT = 42;
	
	public static HashMap<String, SLRecommendedBook> recBookList = new HashMap<String, SLRecommendedBook>();

	private Refreshable jumpBar;

	private SLRecommendedBookDS slRecBookDS = new SLRecommendedBookDS();
	private int pageNowNum = 1;
	private int pageTotalNum = 1;

	public AdminRecBookManagementListGrid(Refreshable jumpbar) {
		super();
		recBookList.clear();
		jumpBar = jumpbar;

		GWT.log("初始化: AdminRecBookManagementListGrid");

		this.setShowAllRecords(true);
		this.setSortField("bookClass");
		this.setCellHeight(DEFAULT_CELL_HEIGHT);

		ListGridField bookPicUrlField = new ListGridField("bookPicUrl", "封面",
				60);
		// bookPicUrlField.setImageURLPrefix("/images/upload/");
		bookPicUrlField.setType(ListGridFieldType.IMAGE);
		bookPicUrlField.setImageHeight(DEFAULT_IMG_HEIGHT);
		bookPicUrlField.setImageWidth(DEFAULT_IMG_WIDTH);

		ListGridField bookNameField = new ListGridField("bookName", "书名");
		ListGridField bookAuthorField = new ListGridField("bookAuthor", "作者");
		ListGridField bookISBNField = new ListGridField("bookISBN", "ISBN");

		ListGridField bookLanguageField = new ListGridField("bookLanguage",
				"语言");

		ListGridField bookClassField = new ListGridField("bookClass", "类别");

		ListGridField bookPriceField = new ListGridField("bookPrice", "价格");

		ListGridField countPriceField = new ListGridField("countPrice", "计算价格");

		ListGridField userNameField = new ListGridField("recUserName", "推荐人");

		ListGridField recRateField = new ListGridField("recRate", "推荐热度");

		this.setFields(bookPicUrlField, bookNameField, bookAuthorField,
				bookISBNField, bookLanguageField, bookClassField,
				bookPriceField, countPriceField, userNameField, recRateField);

		updateDS(DEFAULT_RECORDS_EACH_PAGE, 1);
		this.setDataSource(slRecBookDS);
		this.setAutoFetchData(true);

		bind();

	}

	private void bind() {
		this.addCellDoubleClickHandler(new CellDoubleClickHandler() {

			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				RecommendedBookWindow recommendedBookWindow = new RecommendedBookWindow(
						SLRecommendedBook.parse(getSelectedRecord()), true, getSelf());
				recommendedBookWindow.show();
			}
		});
	}

	private void updateDS(final int itemsPerPage, final int pageNum) {
		new RPCCall<RecommendedBookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(RecommendedBookPage result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLRecommendedBook book : result.getTheBooks()) {
					slRecBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<RecommendedBookPage> cb) {
				recommendedBookService.getAllRecBookList(itemsPerPage, pageNum,
						cb);
			}
		}.retry(3);
	}

	public void doNextPage() {

		if (pageNowNum >= pageTotalNum) {
			SC.say("已到最后一页！");
			return;
		}

		for (Record record : this.getRecords()) {
			slRecBookDS.removeData(record);
		}

		new RPCCall<RecommendedBookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(RecommendedBookPage result) {

				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLRecommendedBook book : result.getTheBooks()) {
					slRecBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<RecommendedBookPage> cb) {
				recommendedBookService.getAllRecBookList(
						DEFAULT_RECORDS_EACH_PAGE, pageNowNum + 1, cb);
			}
		}.retry(3);

	}

	public void doPrePage() {

		if (pageNowNum <= 1) {
			SC.say("已到第一页！");
			return;
		}

		for (Record record : this.getRecords()) {
			slRecBookDS.removeData(record);
		}

		new RPCCall<RecommendedBookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(RecommendedBookPage result) {

				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLRecommendedBook book : result.getTheBooks()) {
					slRecBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<RecommendedBookPage> cb) {
				recommendedBookService.getAllRecBookList(
						DEFAULT_RECORDS_EACH_PAGE, pageNowNum - 1, cb);
			}
		}.retry(3);

	}

	@Override
	public void doRefreshPage() {

		for (Record record : this.getRecords()) {
			slRecBookDS.removeData(record);
		}

		new RPCCall<RecommendedBookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(RecommendedBookPage result) {

				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLRecommendedBook book : result.getTheBooks()) {
					slRecBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<RecommendedBookPage> cb) {
				recommendedBookService.getAllRecBookList(
						DEFAULT_RECORDS_EACH_PAGE, pageNowNum, cb);
			}
		}.retry(3);

	}
	
	private AdminRecBookManagementListGrid getSelf() {
		return this;
	}

	@Override
	public void doAddToBuy(SLRecommendedBook addBook) {
		if (recBookList == null) {
			SC.say("请刷新页面！");
			return;
		}
		if (recBookList.containsKey(addBook.getBookISBN())) {
			SC.say("不可重复添加！");
		} else {
			recBookList.put(addBook.getBookISBN(), addBook);
			SC.say("添加成功！");
		}
	}

}
