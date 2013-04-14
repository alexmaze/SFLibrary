package com.successfactors.library.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;
import com.successfactors.library.client.helper.DataSourceIdGenerator;

public class SLBookDS extends DataSource {
	
	public SLBookDS() {

		this.setID(DataSourceIdGenerator.getId("SLBS"));
		this.setClientOnly(true);
		
		DataSourceImageField bookPicUrlField = new DataSourceImageField("bookPicUrl", "封面");
		//bookPicUrlField.setImageURLPrefix("/images/upload/");
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
		DataSourceTextField bookTotalQuantityField = new DataSourceTextField("bookTotalQuantity", "总数");
		DataSourceTextField bookInStoreQuantityField = new DataSourceTextField("bookInStoreQuantity", "库中数量");
		DataSourceTextField bookAvailableQuantityField = new DataSourceTextField("bookAvailableQuantity", "可借数量");
		DataSourceTextField bookIntroField = new DataSourceTextField("bookIntro", "简介");

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
				bookTotalQuantityField,
				bookInStoreQuantityField,
				bookAvailableQuantityField,
				bookIntroField
				);
	}
}
