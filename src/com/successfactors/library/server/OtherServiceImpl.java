package com.successfactors.library.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.OtherService;
import com.successfactors.library.shared.model.SLStatisticalData;

public class OtherServiceImpl extends RemoteServiceServlet implements OtherService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7566783747177426719L;

	@Override
	public SLStatisticalData getThisMonthStatisticalData() {
		return LibraryStatisticDataCenter.getThisMonthStatisticalData();
	}

}
