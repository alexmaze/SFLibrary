package com.successfactors.library.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.successfactors.library.shared.model.SLStatisticalData;

public class SLStatisticalDataDao {

	private static final Logger log = Logger.getLogger(SLStatisticalDataDao.class);
	
    private SLBookDao bookDao = SLBookDao.getDao();
    private SLOrderDao orderDao = SLOrderDao.getDao();
    private SLBorrowDao borrowDao = SLBorrowDao.getDao();
    private SLRecommendedBookDao recommendedBookDao = SLRecommendedBookDao.getDao();
    
    @SuppressWarnings("deprecation")
	public SLStatisticalData computeLibraryStatisticalData() {
    	
    	log.debug("start SLStatisticalDataDao: computeLibraryStatisticalData");
    	
    	SLStatisticalData ret = new SLStatisticalData();
    	
    	ret.setTotalBookNumber(bookDao.getSumAll("bookTotalQuantity"));
    	ret.setInStoreBookNumber(bookDao.getSumAll("bookInStoreQuantity"));
    	ret.setAvaliableBookNumber(bookDao.getSumAll("bookAvailableQuantity"));
    	ret.setRemainingRecommendNumber(recommendedBookDao.getCountAllRemain());
    	ret.setNowOverdueBorrowNumber(borrowDao.getCountNowAllOverdue());
    	ret.setNowOverdueRate(ret.getNowOverdueBorrowNumber() * 1.0 / borrowDao.getCountNowAllBorrow());
    	ret.setTotalLostBookNumber(0);
    	ret.setTotalLostBookRate(0.0);
    	
    	Date todayDate = new Date();
    	todayDate.setHours(24);
    	todayDate.setMinutes(0);
    	todayDate.setSeconds(0);
    	Date yearBeforeDate = getBeforeDate(todayDate, 365);
    	
    	ret.setBorrowNumberEachTeam(borrowDao.getEachTeamBorrowNumber(yearBeforeDate, todayDate));
    	ret.setOrderNumberEachTeam(orderDao.getEachTeamOrderNumber(yearBeforeDate, todayDate));
    	
    	Map<Integer, Long> activinessEachMonth_borrow = borrowDao.getEachMonthBorrowNumber(yearBeforeDate, todayDate);
    	Map<Integer, Long> activinessEachMonth_order = orderDao.getEachMonthOrderNumber(yearBeforeDate, todayDate);
    	Map<Integer, Long> activinessEachMonth = new HashMap<Integer, Long>();
    	for (int i = 1; i < 13; i++) {
    		long borrowc = activinessEachMonth_borrow.get(i) == null?0:activinessEachMonth_borrow.get(i).longValue();
    		long orderc = activinessEachMonth_order.get(i) == null?0:activinessEachMonth_order.get(i).longValue();
    		activinessEachMonth.put(i, borrowc + orderc);
		}
    	ret.setActivinessEachMonth(activinessEachMonth);
    	
    	return ret;
    	
    }
    
	public static Date getBeforeDate(Date date,int days)
	{
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);
		return calendar.getTime();
	}
	
    
}
