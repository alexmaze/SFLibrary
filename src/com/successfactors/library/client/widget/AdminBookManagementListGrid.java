package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.successfactors.library.client.datasource.SLBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

public class AdminBookManagementListGrid extends ListGrid implements
		BookEditWindow.FinishEditBook {

	public static final int DEFAULT_RECORDS_EACH_PAGE = 10;
	public static final int DEFAULT_IMG_HEIGHT = 40;
	public static final int DEFAULT_IMG_WIDTH = 28;

	public static final int DEFAULT_CELL_HEIGHT = 42;

	private Refreshable jumpBar;

	private SLBookDS slBookDS = new SLBookDS();
	private int pageNowNum = 1;
	private int pageTotalNum = 1;

	public AdminBookManagementListGrid(Refreshable jumpbar) {
		super();
		jumpBar = jumpbar;

		GWT.log("初始化: AdminBookManagementListGrid");

		this.setShowAllRecords(true);
		this.setSortField("bookClass");
		this.setCellHeight(DEFAULT_CELL_HEIGHT);

		ListGridField bookPicUrlField = new ListGridField("bookPicUrl", "封面",
				60);
		//bookPicUrlField.setImageURLPrefix("/images/upload/");
		bookPicUrlField.setType(ListGridFieldType.IMAGE);
		bookPicUrlField.setImageHeight(DEFAULT_IMG_HEIGHT);
		bookPicUrlField.setImageWidth(DEFAULT_IMG_WIDTH);

		ListGridField bookNameField = new ListGridField("bookName", "书名");
		ListGridField bookAuthorField = new ListGridField("bookAuthor", "作者");
		ListGridField bookISBNField = new ListGridField("bookISBN", "ISBN");

		ListGridField bookLanguageField = new ListGridField("bookLanguage",
				"语言");

		ListGridField bookClassField = new ListGridField("bookClass", "类别");
		ListGridField bookTotalQuantityField = new ListGridField(
				"bookTotalQuantity", "总数");
		ListGridField bookInStoreQuantityField = new ListGridField(
				"bookInStoreQuantity", "库中数量");
		ListGridField bookAvailableQuantityField = new ListGridField(
				"bookAvailableQuantity", "可借数量");

		this.setFields(bookPicUrlField, bookNameField, bookAuthorField,
				bookISBNField, bookLanguageField, bookClassField,
				bookTotalQuantityField, bookInStoreQuantityField,
				bookAvailableQuantityField);

		updateDS(DEFAULT_RECORDS_EACH_PAGE, 1);
		this.setDataSource(slBookDS);
		this.setAutoFetchData(true);

		bind();

	}

	private void bind() {
		this.addCellDoubleClickHandler(new CellDoubleClickHandler() {

			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				BookEditWindow bookEditWindow = new BookEditWindow(
						getSelectedRecord(), getSelf());
				bookEditWindow.show();
			}
		});
	}

	private void updateDS(final int itemsPerPage, final int pageNum) {
		new RPCCall<BookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(BookPage result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLBook book : result.getTheBooks()) {
					slBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<BookPage> cb) {
				bookService.getAllBookList(itemsPerPage, pageNum, cb);
			}
		}.retry(3);
	}

	public void doAddBook() {
		BookEditWindow bookEditWindow = new BookEditWindow(getSelf());
		bookEditWindow.show();
	}

	public void doDeleteBook() {
		SC.ask("删除书籍", "您确定要删除这本书么？", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {

					bookService.deleteBook(
							getSelectedRecord().getAttribute("bookISBN"),
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									if (result) {
										slBookDS.removeData(getSelectedRecord());
										SC.say("删除成功！");
									} else {
										SC.say("删除失败，请稍后重试！");
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									SC.say("网络繁忙，请稍后重试！");
								}
							});
				}
			}
		});
	}

	public void doUpdateBook() {
		BookEditWindow bookEditWindow = new BookEditWindow(getSelectedRecord(),
				getSelf());
		bookEditWindow.show();
	}

	public void doSearchBook(final String[] searchInfo) {

		for (Record record : this.getRecords()) {
			slBookDS.removeData(record);
		}

		new RPCCall<BookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(BookPage result) {

				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLBook book : result.getTheBooks()) {
					slBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<BookPage> cb) {
				bookService.searchBookList(BookSearchType.parse(searchInfo[1]),
						searchInfo[0], DEFAULT_RECORDS_EACH_PAGE, 1, cb);
			}
		}.retry(3);

	}

	public void doNextPage(boolean isSearchMode, final String[] searchInfo) {

		if (pageNowNum >= pageTotalNum) {
			SC.say("已到最后一页！");
			return;
		}

		for (Record record : this.getRecords()) {
			slBookDS.removeData(record);
		}

		if (isSearchMode) {

			new RPCCall<BookPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}

				@Override
				public void onSuccess(BookPage result) {

					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLBook book : result.getTheBooks()) {
						slBookDS.addData(book.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}

				@Override
				protected void callService(AsyncCallback<BookPage> cb) {
					bookService.searchBookList(
							BookSearchType.parse(searchInfo[1]), searchInfo[0],
							DEFAULT_RECORDS_EACH_PAGE, pageNowNum + 1, cb);
				}
			}.retry(3);

		} else {
			new RPCCall<BookPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}

				@Override
				public void onSuccess(BookPage result) {

					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLBook book : result.getTheBooks()) {
						slBookDS.addData(book.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}

				@Override
				protected void callService(AsyncCallback<BookPage> cb) {
					bookService.getAllBookList(DEFAULT_RECORDS_EACH_PAGE,
							pageNowNum + 1, cb);
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
			slBookDS.removeData(record);
		}

		if (isSearchMode) {

			new RPCCall<BookPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}

				@Override
				public void onSuccess(BookPage result) {

					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLBook book : result.getTheBooks()) {
						slBookDS.addData(book.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}

				@Override
				protected void callService(AsyncCallback<BookPage> cb) {
					bookService.searchBookList(
							BookSearchType.parse(searchInfo[1]), searchInfo[0],
							DEFAULT_RECORDS_EACH_PAGE, pageNowNum - 1, cb);
				}
			}.retry(3);

		} else {

			new RPCCall<BookPage>() {
				@Override
				public void onFailure(Throwable caught) {
					SC.say("通信失败，请检查您的网络连接！");
				}

				@Override
				public void onSuccess(BookPage result) {

					if (result == null) {
						SC.say("暂无资料。。。囧rz");
						return;
					}
					for (SLBook book : result.getTheBooks()) {
						slBookDS.addData(book.getRecord());
					}
					pageNowNum = result.getPageNum();
					pageTotalNum = result.getTotalPageNum();
					jumpBar.refreshView(pageNowNum, pageTotalNum);
				}

				@Override
				protected void callService(AsyncCallback<BookPage> cb) {
					bookService.getAllBookList(DEFAULT_RECORDS_EACH_PAGE,
							pageNowNum - 1, cb);
				}
			}.retry(3);
		}
	}

	@Override
	public void doRefreshPage() {

		for (Record record : this.getRecords()) {
			slBookDS.removeData(record);
		}

		new RPCCall<BookPage>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(BookPage result) {

				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				for (SLBook book : result.getTheBooks()) {
					slBookDS.addData(book.getRecord());
				}
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(AsyncCallback<BookPage> cb) {
				bookService.getAllBookList(DEFAULT_RECORDS_EACH_PAGE,
						pageNowNum, cb);
			}
		}.retry(3);

	}

	private AdminBookManagementListGrid getSelf() {
		return this;
	}

}
