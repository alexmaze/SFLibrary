package com.successfactors.library.client.widget;

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
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendedBook;
import static com.successfactors.library.client.SFLibrary.recommendedBookService;

/**
 * 主页展示推荐书目列表
 * */
public class RecommendBookList extends VLayout {

	private static final int BOOK_NUM = 3;

	private static final int IMG_HEIGHT = 90;
	private static final int IMG_WIDTH = 62;
	
	private ArrayList<SLRecommendedBook> bookList;

	public RecommendBookList() {
		super();

		GWT.log("初始化: RecommendBookList", null);
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);

		Label headLabel = new Label(
				"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp最新推荐");
		headLabel.setStyleName("alex_header_label");
		headLabel.setHeight(20);

		this.addMember(headLabel);
		this.setMembersMargin(10);
		this.setPadding(10);

		fetchDataAndDisplay();
	}

	private void fetchDataAndDisplay() {
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
				bookList = result.getTheBooks();
				refreshView();
			}

			@Override
			protected void callService(AsyncCallback<RecommendedBookPage> cb) {
				recommendedBookService.getNewRecBookList(BOOK_NUM, cb);
			}
		}.retry(3);
	}

	private void refreshView() {
		for (SLRecommendedBook book : bookList) {
			this.addMember(getBookBox(book));
		}
	}
	
	private HLayout getBookBox(final SLRecommendedBook theBook) {

		HLayout bookBox = new HLayout();
		bookBox.setAlign(Alignment.CENTER);

		Img bookPicUrlImg = new Img(theBook.getBookPicUrl(), IMG_WIDTH,
				IMG_HEIGHT);

		VLayout bookInfo1 = new VLayout();
//		VLayout bookInfo2 = new VLayout();

		Label bookNameLabel = new Label(theBook.getBookName());
		Label bookAuthorLabel = new Label(MyToolsInClient.getWordsForShort(10, theBook.getBookAuthor()));
		Label bookClassLabel = new Label(theBook.getBookClass());
		//Label recUserLabel = new Label(theBook.getRecUserName());

		bookNameLabel.setStyleName("alex_bookshelf_bookname");
		bookNameLabel.setSize("110px", "20px");

		bookAuthorLabel.setStyleName("alex_bookshelf_bookauthor");
		bookAuthorLabel.setSize("110px", "20px");

		bookClassLabel.setStyleName("alex_bookshelf_bookauthor");
		bookClassLabel.setSize("110px", "20px");
		
		//recUserLabel.setStyleName("alex_bookshelf_bookauthor");
		//recUserLabel.setSize("110px", "20px");

		bookInfo1.setWidth(100);
		bookInfo1.setMargin(10);
		bookInfo1
				.setMembers(bookNameLabel, bookAuthorLabel, bookClassLabel);
		bookInfo1.setMembersMargin(5);
		// bookInfo1.setAlign(Alignment.CENTER);

		bookBox.setMembers(bookPicUrlImg, bookInfo1);
		bookBox.setMembersMargin(10);

		// bookBox.setSize(TILE_WIDTH, TILE_HEIGHT);

		bookNameLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				new RPCCall<SLRecommendedBook>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(SLRecommendedBook result) {
						if (result == null) {
							SC.say("暂无资料。。。囧rz");
							return;
						}
						RecommendedBookWindow window = new RecommendedBookWindow(result, false, null);
						window.show();
					}

					@Override
					protected void callService(AsyncCallback<SLRecommendedBook> cb) {
						recommendedBookService.getRecommendedBook(theBook.getBookISBN(), cb);
					}
				}.retry(3);

			}
		});
		
		bookPicUrlImg.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				new RPCCall<SLRecommendedBook>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(SLRecommendedBook result) {
						if (result == null) {
							SC.say("暂无资料。。。囧rz");
							return;
						}
						RecommendedBookWindow window = new RecommendedBookWindow(result, false, null);
						window.show();
					}

					@Override
					protected void callService(AsyncCallback<SLRecommendedBook> cb) {
						recommendedBookService.getRecommendedBook(theBook.getBookISBN(), cb);
					}
				}.retry(3);
				
			}
		});

		return bookBox;

	}

}
