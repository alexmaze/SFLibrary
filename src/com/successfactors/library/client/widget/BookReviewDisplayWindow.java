package com.successfactors.library.client.widget;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.widget.BookReviewEditWindow.FinishEditReview;
import com.successfactors.library.shared.model.SLBookReview;

public class BookReviewDisplayWindow extends Window {
	
	private static final String WINDOW_WIDTH = "620px";
	private static final String WINDOW_HEIGHT = "360px";
	
	private SLBookReview theReview;
	private FinishEditReview finishEdit;

	private IButton updateButton;
	private DynamicForm theForm;
	
	/**
	 * 阅读评论
	 * @param bookReview
	 * @param finishEditReview
	 */
	public BookReviewDisplayWindow(Record bookReviewRecord, FinishEditReview finishEditReview) {
		super();
		this.theReview = SLBookReview.parse(bookReviewRecord);
		this.finishEdit = finishEditReview;
		initDisplayWindow();
	}

	private void initDisplayWindow() {

		this.setAutoSize(true);
		this.setTitle("阅读评论");
		this.setCanDragReposition(true);
		this.setCanDragResize(true);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout = new VLayout();
//		vLayout.setWidth(605);
//		vLayout.setHeight(100);
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);
		vLayout.setPosition(Positioning.RELATIVE);
		vLayout.setMembersMargin(10);
		
		theForm = new DynamicForm();
		theForm.setNumCols(2);
		theForm.setWidth100();
		theForm.setColWidths(100, "*");
		theForm.setCellPadding(5);
		theForm.setCellBorder(1);
		
		StaticTextItem titleItem = new StaticTextItem("title", "标题");
		titleItem.setWidth("100%");
		titleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		titleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem subTitleItem = new StaticTextItem("subTitle", "副标题");
		subTitleItem.setWidth("100%");
		subTitleItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		subTitleItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		

		StaticTextItem userEmailItem = new StaticTextItem("userEmail", "发布者");
		userEmailItem.setWidth("100%");
		userEmailItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		userEmailItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		

		StaticTextItem postDateItem = new StaticTextItem("postDate", "发布时间");
		postDateItem.setWidth("100%");
		postDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		postDateItem.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		
		StaticTextItem contentItem = new StaticTextItem("content", "");
		contentItem.setTextBoxStyle("alex_bookdisplaywindow_form_intro_content");
		contentItem.setShowTitle(false);
		contentItem.setColSpan(2);
		
		theForm.setFields(titleItem, subTitleItem, userEmailItem, postDateItem, contentItem);

		theForm.setValue("title", theReview.getTitle());
		theForm.setValue("subTitle", theReview.getSubTitle());
		theForm.setValue("userEmail", theReview.getUserEmail());
		theForm.setValue("postDate", theReview.getPostDate());
		theForm.setValue("content", theReview.getContent());
		
		
		vLayout.addMember(theForm);
		
		if (theReview.getUserEmail().equals(SFLibrary.get().getNowUser().getUserEmail())) {
			updateButton = new IButton("修改书评");
			updateButton.setIcon("actions/approve.png");
			vLayout.addMember(updateButton);
		}
		
		this.addChild(vLayout);
		bind();
		
	}

	private void bind() {
		
		if(updateButton != null) {
			
			updateButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					BookReviewEditWindow window = new BookReviewEditWindow(theReview.toRecord(), finishEdit);
					window.show();
					destroy();
					
				}
			});
			
		}
	}
	
	
}
