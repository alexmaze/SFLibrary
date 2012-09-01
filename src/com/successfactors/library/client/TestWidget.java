package com.successfactors.library.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.successfactors.library.client.widget.BookDisplayWindow;
import com.successfactors.library.client.widget.BookEditWindow;
import com.successfactors.library.shared.model.SLBook;

public class TestWidget {

	public TestWidget() {
		
		HLayout hLayout = new HLayout();
		
		IButton but1 = new IButton("查看图书信息");
		IButton but2 = new IButton("修改图书信息-更新");
		IButton but3 = new IButton("修改图书信息-新建");
		
		hLayout.setMembers(but1, but2, but3);
		hLayout.draw();
		
		final SLBook temp = new SLBook();
		temp.setBookName("冰与火之歌（卷一）");
		temp.setBookAuthor("[美] 乔治·R. R. 马丁");
		temp.setBookISBN("9787536671256");
		temp.setBookClass("小说/文学");
		temp.setBookAvailableQuantity(1);
		temp.setBookContributor("SF");
		temp.setBookInStoreQuantity(1);
		temp.setBookIntro("《冰与火之歌》由美国著名科幻奇幻小说家乔治·R·R·马丁所著，是当代奇幻文学一部影响深远的里程碑式的作品。它于1996年刚一问世，便以别具一格的结构，浩瀚辽阔的视野，错落有致的情节和生动活泼的语言，迅速征服了欧美文坛。迄今，本书已被译为数十种文字，并在各个国家迭获大奖。");
		temp.setBookLanguage("中文");
		temp.setBookPicUrl("temppic.jpg");
		temp.setBookPrice(68.00);
		temp.setBookPublishDate(new Date());
		temp.setBookPublisher("重庆出版社");
		temp.setBookTotalQuantity(1);
		
		but1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookDisplayWindow bookDisplayWindow = new BookDisplayWindow(temp);
				bookDisplayWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookEditWindow bookEditWindow = new BookEditWindow(temp);
				bookEditWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but3.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookEditWindow bookEditWindow = new BookEditWindow();
				bookEditWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		
		
	}
}
