package com.successfactors.library.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.FieldType;
import com.successfactors.library.client.helper.DataSourceIdGenerator;

public class SLBookReviewDS extends DataSource {

	public SLBookReviewDS() {

		this.setID(DataSourceIdGenerator.getId("SLBRDS"));
		this.setClientOnly(true);

		DataSourceImageField iconField = new DataSourceImageField("icon", "#");
		iconField.setType(FieldType.IMAGE);
		
		DataSourceTextField reviewIdField = new DataSourceTextField("reviewId",
				"ID");
		reviewIdField.setPrimaryKey(true);

		this.setFields(iconField, reviewIdField);
	}
}
