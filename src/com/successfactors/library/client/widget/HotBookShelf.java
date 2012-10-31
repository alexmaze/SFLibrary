package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.helper.MyToolsInClient;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

/**
 * 主页展示书架2-热门书籍，包含详细信息
 * */
public class HotBookShelf extends VLayout {

	private static final int IMG_HEIGHT = 90;
	private static final int IMG_WIDTH = 62;

	private static final String TILE_HEIGHT = "180px";
	private static final String TILE_WIDTH = "155px";

	private static final int BOOK_NUM = 3;

	private ArrayList<SLBook> bookList;

	public HotBookShelf() {
		super();

		GWT.log("初始化: HotBookShelf", null);
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);

		Label headLabel = new Label(
				"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp热门图书");
		headLabel.setStyleName("alex_header_label");
		headLabel.setHeight(20);

		this.addMember(headLabel);
		this.setMembersMargin(10);
		this.setPadding(10);

		fetchDataAndDisplay();
	}

	private void fetchDataAndDisplay() {
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
				bookList = result.getTheBooks();
				refreshView();
			}

			@Override
			protected void callService(AsyncCallback<BookPage> cb) {
				bookService.getHotBookList(BOOK_NUM, cb);
			}
		}.retry(3);
	}

	private void refreshView() {
		for (SLBook book : bookList) {
			this.addMember(getBookBox(book));
		}
	}

	private HLayout getBookBox(final SLBook theBook) {

		HLayout bookBox = new HLayout();
		bookBox.setAlign(Alignment.CENTER);

		Img bookPicUrlImg = new Img(
				"/images/upload/" + theBook.getBookPicUrl(), IMG_WIDTH,
				IMG_HEIGHT);

		VLayout bookInfo1 = new VLayout();
		VLayout bookInfo2 = new VLayout();

		Label bookNameLabel = new Label(theBook.getBookName());
		Label bookAuthorLabel = new Label(theBook.getBookAuthor());
		Label bookPublisherLabel = new Label(theBook.getBookPublisher());
		// Label bookClassLabel = new Label(theBook.getBookClass());
		// Label bookLanguageLabel = new Label(theBook.getBookLanguage());

		bookNameLabel.setStyleName("alex_bookshelf_bookname");
		bookNameLabel.setSize("110px", "20px");

		bookAuthorLabel.setStyleName("alex_bookshelf_bookauthor");
		bookAuthorLabel.setSize("110px", "20px");

		bookPublisherLabel.setStyleName("alex_bookshelf_bookauthor");
		bookPublisherLabel.setSize("110px", "20px");

		// bookClassLabel.setStyleName("alex_bookshelf_bookauthor");
		// bookClassLabel.setSize("110px", "20px");
		//
		// bookLanguageLabel.setStyleName("alex_bookshelf_bookauthor");
		// bookLanguageLabel.setSize("110px", "20px");

		bookInfo1.setWidth(100);
		bookInfo1.setMargin(10);
		bookInfo1
				.setMembers(bookNameLabel, bookAuthorLabel, bookPublisherLabel);
		bookInfo1.setMembersMargin(5);
		// bookInfo1.setAlign(Alignment.CENTER);

		Label bookIntroLabel = new Label("&nbsp&nbsp&nbsp&nbsp"
				+ MyToolsInClient.getWords(120, theBook.getBookIntro()));
		bookIntroLabel.setWidth(320);

		bookIntroLabel.setStyleName("alex_bookshelf_bookauthor");
		bookIntroLabel.setMargin(5);

		bookBox.setMembers(bookPicUrlImg, bookInfo1, bookIntroLabel);
		bookBox.setMembersMargin(10);

		// bookBox.setSize(TILE_WIDTH, TILE_HEIGHT);

		bookNameLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				new RPCCall<SLBook>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(SLBook result) {
						if (result == null) {
							SC.say("暂无资料。。。囧rz");
							return;
						}
						BookDisplayWindow bookDisplayWindow = new BookDisplayWindow(
								result, null);
						bookDisplayWindow.draw();
					}

					@Override
					protected void callService(AsyncCallback<SLBook> cb) {
						bookService.getBookByISBN(theBook.getBookISBN(), cb);
					}
				}.retry(3);

			}
		});

		return bookBox;

	}

}
