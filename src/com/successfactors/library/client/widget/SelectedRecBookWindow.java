package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.recommendedBookService;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.datasource.SLRecommendedBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.model.SLRecommendedBook;

public class SelectedRecBookWindow  extends Window {

	private static final String WINDOW_WIDTH = "580px";
	private static final String WINDOW_HEIGHT = "340px";

	private ListGrid theListGrid;
	private DynamicForm theForm;
	SLRecommendedBookDS bookDS;
	
	double totalPrice = 0;
	
	IButton conformButton;
	
	public SelectedRecBookWindow() {
		super();
		initWindow();
	}
	
	private void initWindow() {
		
		this.setAutoSize(true);
		this.setTitle("挑选书单");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout = new VLayout();
		vLayout.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		initListGrid();
		initInfoForm();
		
		conformButton = new IButton("确认提交");
		conformButton.setIcon("actions/approve.png");
		
		vLayout.setMembers(theForm, theListGrid, conformButton);
		vLayout.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		vLayout.setMembersMargin(5);
		vLayout.setMargin(10);
		
		this.addItem(vLayout);

		bind();
	}

	private void initInfoForm() {
		theForm = new DynamicForm();
		
		theForm.setNumCols(4);
		theForm.setWidth(WINDOW_WIDTH);
		theForm.setColWidths("*","*","*","*");
		theForm.setCellPadding(3);

		StaticTextItem bookNameItem = new StaticTextItem("selectedNum", "已选");
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem bookAuthorItem = new StaticTextItem("totalPrice", "总价");
		bookAuthorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookAuthorItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		theForm.setFields(
				bookNameItem,
				bookAuthorItem
				);
		
		theForm.setValue("selectedNum", AdminRecBookManagementListGrid.recBookList.size() + " 本");
		theForm.setValue("totalPrice", totalPrice + " 元");
		
	}

	private void initListGrid() {
		
		theListGrid = new ListGrid();
		theListGrid.setPrompt("双击删除！");
		
		bookDS = new SLRecommendedBookDS();
		
		for (SLRecommendedBook book : AdminRecBookManagementListGrid.recBookList) {
			bookDS.addData(book.toRecord());
			totalPrice += book.getCountPrice();
		}
		
		ListGridField userNameField = new ListGridField("bookName", "书名");
		ListGridField borrowDateField = new ListGridField("recUserName", "推荐人");
		ListGridField shouldReturnDateField = new ListGridField("countPrice", "计算价格");
		theListGrid.setFields(userNameField, borrowDateField, shouldReturnDateField);
		
		theListGrid.setDataSource(bookDS);
		theListGrid.setAutoFetchData(true);
		
	}

	private void bind() {
		theListGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				String deleteISBN = theListGrid.getSelectedRecord().getAttributeAsString("bookISBN");
				
				totalPrice -= theListGrid.getSelectedRecord().getAttributeAsDouble("countPrice");
				theForm.setValue("totalPrice", totalPrice + " 元");
				bookDS.removeData(theListGrid.getSelectedRecord());
				theListGrid.fetchData();

				for (SLRecommendedBook book : AdminRecBookManagementListGrid.recBookList) {
					if (book.getBookISBN().equals(deleteISBN)) {
						AdminRecBookManagementListGrid.recBookList.remove(book);
						break;
					}
				}
				
				theForm.setValue("selectedNum", AdminRecBookManagementListGrid.recBookList.size() + " 本");
			}
		});
		conformButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (AdminRecBookManagementListGrid.recBookList == null || 
					AdminRecBookManagementListGrid.recBookList.size() == 0) {
					SC.say("请选择要购买的书籍！");
					return;
				}
				
				new RPCCall<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						SC.say("通信失败，请检查您的网络连接！");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result == null || !result) {
							SC.say("请稍候重试！");
							return;
						}
						SC.say("购买成功，已将购买书单发送至管理员邮箱！");
						destroy();

					}

					@Override
					protected void callService(AsyncCallback<Boolean> cb) {
						
						ArrayList<String> bookISBNList = new ArrayList<String>();

						for (SLRecommendedBook book : AdminRecBookManagementListGrid.recBookList) {
							bookISBNList.add(book.getBookISBN());
						}
						
						recommendedBookService.buyBookList(bookISBNList, cb);
					}
				}.retry(3);
			}
		});
	}
}
