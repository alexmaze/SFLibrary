package com.successfactors.library.server;

import java.util.Date;

import com.successfactors.library.server.dao.SLStatisticalDataDao;
import com.successfactors.library.shared.model.SLStatisticalData;

public class LibraryStatisticDataCenter {
	private static SLStatisticalData catchedLibraryStatisticalData;
	private static SLStatisticalDataDao theDao = new SLStatisticalDataDao();
	
	@SuppressWarnings("deprecation")
	public static SLStatisticalData getThisMonthStatisticalData() {
		
		if (catchedLibraryStatisticalData == null || 
				catchedLibraryStatisticalData.getTimestamp().getMonth() != (new Date()).getMonth()) {
			catchedLibraryStatisticalData = theDao.computeLibraryStatisticalData();	
		} 
		
		return catchedLibraryStatisticalData;
		
	}
}
