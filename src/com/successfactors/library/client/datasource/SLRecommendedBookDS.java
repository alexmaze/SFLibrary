package com.successfactors.library.client.datasource;

import java.util.Date;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;

public class SLRecommendedBookDS extends DataSource {
	
	public SLRecommendedBookDS() {

		this.setID("SLRBS"+(new Date()).getTime());
		this.setClientOnly(true);
		
		DataSourceImageField bookPicUrlField = new DataSourceImageField("bookPicUrl", "封面");
		bookPicUrlField.setType(FieldType.IMAGE);
		
		DataSourceTextField bookNameField = new DataSourceTextField("bookName", "书名");
		DataSourceTextField bookAuthorField = new DataSourceTextField("bookAuthor", "作者");
		DataSourceTextField bookISBNField = new DataSourceTextField("bookISBN", "ISBN");
		bookISBNField.setPrimaryKey(true);

		DataSourceTextField bookPublisherField = new DataSourceTextField("bookPublisher", "出版商");
		DataSourceDateField bookPublishDateField = new DataSourceDateField("bookPublishDate", "出版日期");
		DataSourceTextField bookLanguageField = new DataSourceTextField("bookLanguage", "语言");
		DataSourceTextField bookPriceField = new DataSourceTextField("bookPrice", "价格");
		DataSourceTextField bookContributorField = new DataSourceTextField("bookContributor", "贡献者");
		
		DataSourceTextField bookClassField = new DataSourceTextField("bookClass", "类别");
		DataSourceTextField bookIntroField = new DataSourceTextField("bookIntro", "简介");
		
		DataSourceTextField recUserNameField = new DataSourceTextField("recUserName", "推荐人");
		DataSourceTextField recUserEmailField = new DataSourceTextField("recUserEmail", "推荐人邮箱");
		DataSourceTextField recStatusField = new DataSourceTextField("recStatus", "推荐状态");
		DataSourceDateField recDateField = new DataSourceDateField("recDate", "推荐日期");
		DataSourceTextField recRateField = new DataSourceTextField("recRate", "推荐热度");

		this.setFields(
				bookPicUrlField,
				bookNameField,
				bookAuthorField,
				bookISBNField,
				bookPublisherField,
				bookPublishDateField,
				bookLanguageField,
				bookPriceField,
				bookContributorField,
				bookClassField,
				bookIntroField,
				recUserNameField,
				recUserEmailField,
				recStatusField,
				recDateField,
				recRateField
				);
	}
}
