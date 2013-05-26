package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.service.BookReviewService;
import com.successfactors.library.client.service.BookReviewServiceAsync;
import com.successfactors.library.shared.model.DataWrapper;
import com.successfactors.library.shared.model.SLBookReview;

public class BookReviewEditWindow extends Window {

	private BookReviewServiceAsync bookReviewService = GWT.create(BookReviewService.class);
	
	public interface FinishEditReview {
		void doRefreshPage();
	}
	
	private static final String WINDOW_WIDTH = "620px";
	private static final String WINDOW_HEIGHT = "500px";
	
	private SLBookReview theReview;
	private FinishEditReview finishEdit;

	private IButton addButton;
	private IButton updateButton;
	private DynamicForm theForm;
	
	/**
	 * 创建新的评论
	 * @param bookISBN
	 * @param finishEditReview
	 */
	public BookReviewEditWindow(String bookISBN, FinishEditReview finishEditReview) {
		super();
		this.theReview = new SLBookReview();
		this.theReview.setBookISBN(bookISBN);
		this.finishEdit = finishEditReview;
		initNewWindow();
	}

	/**
	 * 修改已有评论
	 * @param bookReview
	 * @param finishEditReview
	 */
	public BookReviewEditWindow(Record bookReviewRecord, FinishEditReview finishEditReview) {
		super();
		this.theReview = SLBookReview.parse(bookReviewRecord);
		this.finishEdit = finishEditReview;
		initEditWindow();
	}

	private void initNewWindow() {
		
		this.setAutoSize(false);
		this.setTitle("发布评论");
		this.setCanDragReposition(true);
		this.setCanDragResize(true);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout = new VLayout();
		vLayout.setWidth(610);
		vLayout.setHeight(480);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);
		vLayout.setPosition(Positioning.RELATIVE);
		
		theForm = new DynamicForm();
		theForm.setNumCols(2);
		theForm.setWidth100();
		theForm.setColWidths(80, "*");
		theForm.setCellPadding(5);
		
		TextItem titleItem = new TextItem("title", "标题");
		titleItem.setWidth("100%");
		titleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		titleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		TextItem subTitleItem = new TextItem("subTitle", "副标题");
		subTitleItem.setWidth("100%");
		subTitleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		subTitleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		RichTextItem contentItem = new RichTextItem("content", "内容");
		contentItem.setWidth("100%");
		contentItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		contentItem.setHeight(300);
		
		theForm.setFields(titleItem, subTitleItem, contentItem);
		
		addButton = new IButton("提交");
		addButton.setIcon("actions/approve.png");
		
		vLayout.setMembers(theForm, addButton);
		
		this.addChild(vLayout);
		bind();
	}

	private void initEditWindow() {
		
		this.setAutoSize(false);
		this.setTitle("修改评论");
		this.setCanDragReposition(true);
		this.setCanDragResize(true);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout = new VLayout();
		vLayout.setWidth(610);
		vLayout.setHeight(480);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);
		vLayout.setPosition(Positioning.RELATIVE);
		
		theForm = new DynamicForm();
		theForm.setNumCols(2);
		theForm.setWidth100();
		theForm.setColWidths(80, "*");
		theForm.setCellPadding(5);
		
		TextItem titleItem = new TextItem("title", "标题");
		titleItem.setWidth("100%");
		titleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		titleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		TextItem subTitleItem = new TextItem("subTitle", "副标题");
		subTitleItem.setWidth("100%");
		subTitleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		subTitleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		RichTextItem contentItem = new RichTextItem("content", "内容");
		contentItem.setWidth("100%");
		contentItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		contentItem.setHeight(300);
		
		theForm.setFields(titleItem, subTitleItem, contentItem);

		theForm.setValue("title", theReview.getTitle());
		theForm.setValue("subTitle", theReview.getSubTitle());
		theForm.setValue("content", theReview.getContent());
		
		updateButton = new IButton("提交");
		updateButton.setIcon("actions/approve.png");
		
		vLayout.setMembers(theForm, updateButton);
		
		this.addChild(vLayout);
		bind();
		
	}

	private void bind() {
		
		if (addButton != null) {
			addButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!updateDataFromForm()) {
						return;
					}
					if (SFLibrary.get().getNowUser() == null) {
						SC.say("未登录", "请先登录！");
						return;
					}
					theReview.setUserEmail(SFLibrary.get().getNowUser().getUserEmail());
					
					new RPCCall<DataWrapper<SLBookReview>>() {

						@Override
						public void onFailure(Throwable caught) {
							SC.say("通信失败，请检查您的网络连接！");
						}

						@Override
						public void onSuccess(DataWrapper<SLBookReview> result) {
							if (result == null) {
								SC.say("暂无资料。。。囧rz");
								return;
							}
							theReview = result.getData();
							finishEdit.doRefreshPage();
							destroy();
						}

						@Override
						protected void callService(
								AsyncCallback<DataWrapper<SLBookReview>> cb) {
							bookReviewService.addBookReview(theReview, cb);
						}
					}.retry(3);
				}
			});
		}
		
		if(updateButton != null) {
			
			updateButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (!updateDataFromForm()) {
						return;
					}
					
					new RPCCall<DataWrapper<SLBookReview>>() {

						@Override
						public void onFailure(Throwable caught) {
							SC.say("通信失败，请检查您的网络连接！");
						}

						@Override
						public void onSuccess(DataWrapper<SLBookReview> result) {
							if (result == null) {
								SC.say("暂无资料。。。囧rz");
								return;
							}
							theReview = result.getData();
							finishEdit.doRefreshPage();
							destroy();
						}

						@Override
						protected void callService(
								AsyncCallback<DataWrapper<SLBookReview>> cb) {
							bookReviewService.updateBookReview(theReview, cb);
						}
					}.retry(3);
				}
			});
			
		}
	}
	


	private boolean updateDataFromForm() {
		theReview.setTitle(theForm.getValueAsString("title"));
		theReview.setSubTitle(theForm.getValueAsString("subTitle"));
		theReview.setContent(theForm.getValueAsString("content"));
		
		if (theReview.getTitle() == null || theReview.getTitle().equals("")) {
			SC.say("空标题", "评论标题不可为空！");
			return false;
		}
		if (theReview.getContent() == null || theReview.getContent().equals("")) {
			SC.say("空内容", "评论内容不可为空！");
			return false;
		}
		
		return true;
	}
	
}
