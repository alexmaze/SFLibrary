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
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.BookPage;
import com.successfactors.library.shared.model.SLBook;

/**
 * 主页展示书架1-新书，只有图片，书名，作者信息
 * */
public class NewBookShelf extends VLayout {

	private static final int IMG_HEIGHT = 158;
	private static final int IMG_WIDTH = 110;

	private static final String TILE_HEIGHT = "180px";
	private static final String TILE_WIDTH = "155px";
	
	private static final int BOOK_NUM = 6;
	
	private ArrayList<SLBook> bookList;
	private HLayout theShelf;
	
	public NewBookShelf() {
		super();
		GWT.log("初始化: NewBookShelf", null);
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);
		
		Label headLabel = new Label("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp新书推荐");
		headLabel.setStyleName("alex_header_label");
		headLabel.setHeight(20);
		
		theShelf = new HLayout();
		theShelf.setAlign(Alignment.CENTER);
		
        this.setMembers(headLabel, theShelf);
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
				bookService.getNewBookList(BOOK_NUM, cb);
			}
		}.retry(3);
	}
	
	private void refreshView() {
		for (SLBook book : bookList) {
			theShelf.addMember(getBookBox(book));
		}
	}
	
	private VLayout getBookBox(final SLBook theBook) {
		
		VLayout bookBox = new VLayout();
		
		Img bookPicUrlImg = new Img("/images/upload/"+theBook.getBookPicUrl(), IMG_WIDTH, IMG_HEIGHT);
		Label bookNameLabel = new Label(theBook.getBookName());
		Label bookAuthorLabel = new Label(theBook.getBookAuthor());
		
		bookNameLabel.setStyleName("alex_bookshelf_bookname");
		bookAuthorLabel.setStyleName("alex_bookshelf_bookauthor");
		bookNameLabel.setSize("110px", "20px");
		bookAuthorLabel.setSize("110px", "20px");
		bookNameLabel.setAlign(Alignment.CENTER);
		bookAuthorLabel.setAlign(Alignment.CENTER);
		
		bookBox.setMembers(bookPicUrlImg, bookNameLabel, bookAuthorLabel);
		
		bookBox.setSize(TILE_WIDTH, TILE_HEIGHT);
		
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
						BookDisplayWindow bookDisplayWindow = new BookDisplayWindow(result);
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
