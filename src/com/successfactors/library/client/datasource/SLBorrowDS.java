package com.successfactors.library.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;
import com.successfactors.library.client.helper.DataSourceIdGenerator;

public class SLBorrowDS extends DataSource {
	
	public SLBorrowDS() {

		this.setID(DataSourceIdGenerator.getId("SLBWS"));
		this.setClientOnly(true);
		
		DataSourceImageField iconField = new DataSourceImageField("icon", "#");
		iconField.setImageURLPrefix("/images/icons/16/");
		iconField.setType(FieldType.IMAGE);
		
		DataSourceTextField borrowIdField = new DataSourceTextField("borrowId", "借阅ID");
		borrowIdField.setPrimaryKey(true);

		DataSourceTextField userEmailField = new DataSourceTextField("userEmail", "读者Email");
		DataSourceTextField bookISBNField = new DataSourceTextField("bookISBN", "书籍ISBN");
		
		DataSourceDateField borrowDateField = new DataSourceDateField("borrowDate", "借书日期");
		DataSourceDateField shouldReturnDateField = new DataSourceDateField("shouldReturnDate", "应还日期");
		DataSourceDateField returnDateField = new DataSourceDateField("returnDate", "归还日期");
		
		DataSourceBooleanField inStoreField = new DataSourceBooleanField("inStore", "是否已取");
		DataSourceBooleanField overdueField = new DataSourceBooleanField("overdue", "是否超期");
		DataSourceTextField statusField = new DataSourceTextField("status", "状态");

		//--------------------------------------------------------------------------------------
		DataSourceTextField bookNameField = new DataSourceTextField("bookName", "书名");
		DataSourceTextField bookPicUrlField = new DataSourceTextField("bookPicUrl", "封面");
		DataSourceTextField userNameField = new DataSourceTextField("userName", "借书人");
		
		this.setFields(
				iconField,
				borrowIdField,
				userEmailField,
				bookISBNField,
				borrowDateField,
				shouldReturnDateField,
				returnDateField,
				inStoreField,
				overdueField,
				statusField,
				bookNameField,
				bookPicUrlField,
				userNameField
				);
	}
}
