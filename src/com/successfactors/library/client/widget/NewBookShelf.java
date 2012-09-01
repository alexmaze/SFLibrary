package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLBook;

/**
 * 主页展示书架1-新书，只有图片，书名，作者信息
 * */
public class NewBookShelf extends VLayout {

	private static final int IMG_HEIGHT = 165;
	private static final int IMG_WIDTH = 116;
	
	public NewBookShelf() {
		super();
		GWT.log("初始化: NewBookShelf", null);
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);
		
		Label headLabel = new Label("新书推荐");
		headLabel.setStyleName("alex_header_label");
		headLabel.setHeight(20);
		//headLabel.setWidth("100%");
		//headLabel.setAlign(Alignment.CENTER);
		
        TileGrid tileGrid = new TileGrid();
        tileGrid.setCanReorderTiles(false); 
        tileGrid.setShowAllRecords(false); 
        //tileGrid.setData(CarData.getRecords());
  
        DetailViewerField bookPicUrlField = new DetailViewerField("bookPicUrl");  
        bookPicUrlField.setType("image");  
        bookPicUrlField.setImageURLPrefix("/upload/");  
        bookPicUrlField.setImageWidth(186);  
        bookPicUrlField.setImageHeight(120);  
  
        DetailViewerField bookNameField = new DetailViewerField("bookName");  
        DetailViewerField bookAuthorField = new DetailViewerField("bookAuthor");  
  
        tileGrid.setFields(bookPicUrlField, bookNameField, bookAuthorField);  
  
        this.setMembers(headLabel, tileGrid);
        
        getDataForTileGrid();
        bind();
	}
	
	private void bind() {
		
	}
	
	private void getDataForTileGrid() {
//		new RPCCall<ArrayList<SLBook>>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				SC.say("通信失败，请检查您的网络连接！");
//			}
//			@Override
//			public void onSuccess(ArrayList<SLBook> result) {
//				if (result == null || result.isEmpty()) {
//					SC.say("暂无资料。。。囧rz");
//					return;
//				}
//				for (SLBook slBook : result) {
//					slBookDS.addData(slBook.getRecord());
//				}
//			}
//			@Override
//			protected void callService(AsyncCallback<ArrayList<SLBook>> cb) {
//				bookService.getAllBookList(iStart, iEnd, cb);
//			}
//		}.retry(3);
	}
	
}
