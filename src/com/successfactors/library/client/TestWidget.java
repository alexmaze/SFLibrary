package com.successfactors.library.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.BookDisplayWindow;
import com.successfactors.library.client.widget.BookEditWindow;
import com.successfactors.library.client.widget.BorrowDisplayWindow;
import com.successfactors.library.client.widget.BorrowEditWindow;
import com.successfactors.library.client.widget.BorrowNeedReturnWindow;
import com.successfactors.library.client.widget.BorrowNeedTookenWindow;
import com.successfactors.library.client.widget.OrderDisplayWindow;
import com.successfactors.library.client.widget.OrderEditWindow;
import com.successfactors.library.client.widget.UploadImageWindow;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

public class TestWidget {

	public TestWidget() {
		
		VLayout hLayout = new VLayout();
		
		IButton but1 = new IButton("查看图书信息");
		IButton but2 = new IButton("修改图书信息-更新");
		IButton but3 = new IButton("修改图书信息-新建");
		
		IButton but4 = new IButton("查看借阅信息");
		IButton but5 = new IButton("修改借阅信息-更新");
		IButton but6 = new IButton("修改借阅信息-新建");
		
		IButton but7 = new IButton("查看预定信息");
		IButton but8 = new IButton("修改预定信息-更新");

		IButton but9 = new IButton("未取窗口");
		IButton but10 = new IButton("超期窗口");
		

		IButton but11 = new IButton("上传图片");
		
		hLayout.setMembers(
				but1, but2, but3,
				but4, but5, but6,
				but7, but8,
				but9, but10, but11);
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
		
		final SLUser user = new SLUser();
		user.setUserName("Alex Yan");
		user.setUserEmail("ayan@successfactors.com");
		
		final SLBorrow borrow = new SLBorrow();
		borrow.setBookISBN("9787536671256");
		borrow.setBorrowId(100);
		borrow.setBorrowDate(new Date());
		borrow.setUserEmail("ayan@successfactors.com");
		borrow.setShouldReturnDate(new Date());
		borrow.setReturnDate(null);
		borrow.setInStore(true);
		borrow.setOverdue(false);
		borrow.setStatus("已借出");
		borrow.setTheBook(temp);
		borrow.setTheUser(user);

		final SLOrder order = new SLOrder();
		order.setOrderId(454);
		order.setBookISBN("9787536671256");
		order.setUserEmail("ayan@successfactors.com");
		order.setOrderDate(new Date());
		order.setStatus("排队中");
		order.setTheBook(temp);
		order.setTheUser(user);
		
		//----------------------------------------------------------------------------------------------------------------------
		but1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookDisplayWindow bookDisplayWindow = new BookDisplayWindow(temp, null);
				bookDisplayWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookEditWindow bookEditWindow = new BookEditWindow(temp, null);
				bookEditWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but3.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BookEditWindow bookEditWindow = new BookEditWindow(null);
				bookEditWindow.draw();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		//----------------------------------------------------------------------------------------------------------------------
		but4.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				
				BorrowDisplayWindow borrowDisplayWindow = new BorrowDisplayWindow(borrow.getRecord());
				borrowDisplayWindow.show();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but5.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				BorrowEditWindow borrowDisplayWindow = new BorrowEditWindow(borrow.getRecord(), null);
				borrowDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but6.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				BorrowEditWindow borrowDisplayWindow = new BorrowEditWindow(null);
				borrowDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		//----------------------------------------------------------------------------------------------------------------------
		but7.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");

				OrderDisplayWindow orderDisplayWindow = new OrderDisplayWindow(order.getRecord());
				orderDisplayWindow.show();
				
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but8.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				OrderEditWindow orderDisplayWindow = new OrderEditWindow(order.getRecord(), null);
				orderDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		//----------------------------------------------------------------------------------------------------------------------

		but9.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				BorrowNeedTookenWindow orderDisplayWindow = new BorrowNeedTookenWindow();
				orderDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		but10.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				BorrowNeedReturnWindow orderDisplayWindow = new BorrowNeedReturnWindow();
				orderDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		

		but11.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("*********** Test Widget Start *************");
				UploadImageWindow orderDisplayWindow = new UploadImageWindow(null);
				orderDisplayWindow.show();
				GWT.log("***********  Test Widget End  *************");
			}
		});
		
		
	}
}
