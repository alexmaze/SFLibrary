package com.successfactors.library.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.successfactors.library.shared.model.SLStatisticalData;

public interface OtherServiceAsync {

	void getThisMonthStatisticalData(AsyncCallback<SLStatisticalData> callback);

}
