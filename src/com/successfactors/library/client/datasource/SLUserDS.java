package com.successfactors.library.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.successfactors.library.client.helper.DataSourceIdGenerator;

public class SLUserDS extends DataSource {
	
	public SLUserDS() {

		this.setID(DataSourceIdGenerator.getId("SLUSER"));
		this.setClientOnly(true);
		
		DataSourceTextField userEmailField = new DataSourceTextField("userEmail", "");
		userEmailField.setPrimaryKey(true);
		
		this.setFields(
				userEmailField
				);
	}
}
