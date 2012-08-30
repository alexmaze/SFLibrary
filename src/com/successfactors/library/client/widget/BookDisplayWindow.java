package com.successfactors.library.client.widget;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.successfactors.library.client.datasource.SLBookDS;

public class BookDisplayWindow  extends Window {

	private static final String WINDOW_WIDTH = "560px";
	private static final String WINDOW_HEIGHT = "360px";
	
	private Record theRecord;
	private SLBookDS theDataSource;
	
	private DynamicForm bookForm;
	
	public BookDisplayWindow(Record bookRec) {
		super();
		this.theRecord = bookRec;
		this.theDataSource = new SLBookDS();
		this.theDataSource.addData(theRecord);
		initDisplayWindow();
	}
	
	private void initDisplayWindow() {
		
		String strBookName = theRecord.getAttributeAsString("bookName");
		
		this.setAutoSize(true);
		this.setTitle("《"+strBookName+"》"+"详细信息");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		bookForm = new DynamicForm();
		bookForm.setDataSource(theDataSource);
		bookForm.setNumCols(6);

		bookForm.setWidth(WINDOW_WIDTH);
		bookForm.setHeight(WINDOW_HEIGHT);
		//bookForm.setColWidths(100, "*", 80, "*");
		bookForm.setBorder("2px solid #7598C7");

		FileItem bookPicUrlItem = new FileItem("bookPicUrl", "图书封面");
		bookPicUrlItem.setColSpan(2);
		bookPicUrlItem.setRowSpan(5);
		bookPicUrlItem.setShowTitle(false);
		bookPicUrlItem.setShowHint(false);
		bookPicUrlItem.setCanEdit(false);
		//bookPicUrlItem.setShowFileInline(true);
		
		
		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(4);
		
		StaticTextItem bookAuthorItem = new StaticTextItem("bookAuthor", "作者");
		bookAuthorItem.setColSpan(4);
		
		StaticTextItem bookPublisherItem = new StaticTextItem("bookPublisher", "出版社");
		bookPublisherItem.setColSpan(2);
		
		StaticTextItem bookPublishDateItem = new StaticTextItem("bookPublishDate", "出版日期");
		bookPublishDateItem.setColSpan(2);
		
		StaticTextItem bookClassItem = new StaticTextItem("bookClass", "类别");
		bookClassItem.setColSpan(2);
		
		StaticTextItem bookLanguageItem = new StaticTextItem("bookLanguage", "语言");
		bookLanguageItem.setColSpan(2);
		
		StaticTextItem bookContributorItem = new StaticTextItem("bookContributor", "贡献者");
		bookContributorItem.setColSpan(2);
		
		StaticTextItem bookPriceItem = new StaticTextItem("bookPrice", "价格");
		bookPriceItem.setColSpan(2);
		
		bookForm.setFields(
				bookPicUrlItem, 
				bookNameItem, 
				bookAuthorItem, 
				bookPublisherItem, 
				bookPublishDateItem,
				bookClassItem,
				bookLanguageItem,
				bookContributorItem,
				bookPriceItem);
		
//		TextItem planNameField = new TextItem("planName", "计划名称");
//		planNameField.setColSpan(4);
//		
//		SelectItem planManagerField = new SelectItem("planManagerName", "负责人");
//		planManagerField.setColSpan(4);
//
//		DateItem planStartDateField = new DateItem("planStartDate", "开始日期");
//		planStartDateField.setColSpan(4);
//		
//		planStartDateField.setStartDate(theRecord.getAttributeAsDate("planStartDate"));
//		planStartDateField.setEndDate(theRecord.getAttributeAsDate("planExpectedDate"));
//		planStartDateField.setDefaultValue(theRecord.getAttributeAsDate("planStartDate"));
//		
//		DateItem planExpectedDateField = new DateItem("planExpectedDate", "预计结束日期");
//		planExpectedDateField.setColSpan(4);
//		
//		planExpectedDateField.setStartDate(theRecord.getAttributeAsDate("planStartDate"));
//		planExpectedDateField.setEndDate(theRecord.getAttributeAsDate("planExpectedDate"));
//		planExpectedDateField.setDefaultValue(theRecord.getAttributeAsDate("planExpectedDate"));
//
//		TextItem planCostField = new TextItem("planCost", "计划预算￥");
//		
//		TextAreaItem planResourceField = new TextAreaItem("planResource", "计划资源");
//		planResourceField.setColSpan(4);
//		planResourceField.setWidth("*");
//		planResourceField.setHeight("*");
//		
//		TextAreaItem planIntroField = new TextAreaItem("planIntro", "计划简介");
//		planIntroField.setColSpan(4);
//		planIntroField.setWidth("*");
//		planIntroField.setHeight("*");
//
//		ButtonItem butSend = new ButtonItem("butSend", "创建计划");
//		butSend.setColSpan(4);
//		butSend.setAlign(Alignment.RIGHT);
//		butSend.setWidth("200px");
//
//		bookForm.setFields(planNameField, planManagerField, planStartDateField,planExpectedDateField, planCostField, planResourceField, planIntroField, butSend);
		
		bookForm.setMargin(12);
		bookForm.setPadding(14);
		
		bookForm.selectRecord(theRecord);
		bookForm.fetchData();

		this.addItem(bookForm);
	}
	
}
