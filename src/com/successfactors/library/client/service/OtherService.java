package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.successfactors.library.shared.model.SLStatisticalData;

@RemoteServiceRelativePath("otherService")
public interface OtherService extends RemoteService {
	
	SLStatisticalData getThisMonthStatisticalData();
	
}
