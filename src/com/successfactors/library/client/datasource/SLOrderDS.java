package com.successfactors.library.client.datasource;

import java.util.Date;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;

public class SLOrderDS extends DataSource {
	
	public SLOrderDS() {

		this.setID("SLOS"+(new Date()).getTime());
		this.setClientOnly(true);
		
		DataSourceImageField iconField = new DataSourceImageField("icon", "#");
		iconField.setImageURLPrefix("/images/icons/16/");
		iconField.setType(FieldType.IMAGE);
		
		DataSourceTextField orderIdField = new DataSourceTextField("orderId", "预订ID");
		orderIdField.setPrimaryKey(true);

		DataSourceTextField userEmailField = new DataSourceTextField("userEmail", "读者Email");
		DataSourceTextField bookISBNField = new DataSourceTextField("bookISBN", "书籍ISBN");
		
		DataSourceDateField orderDateField = new DataSourceDateField("orderDate", "预订时间");
		
		DataSourceTextField statusField = new DataSourceTextField("status", "状态");

		
		this.setFields(
				iconField,
				orderIdField,
				userEmailField,
				bookISBNField,
				orderDateField,
				statusField
				);
	}
}
