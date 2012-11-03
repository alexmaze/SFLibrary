package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.successfactors.library.server.dao.SLBorrowDao;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.model.SLBorrow;

public class SFLibDailyTask extends TimerTask {

	// 每天0点执行
	private static final int C_SCHEDULE_HOUR = 0;
	
	private static boolean isRunning = false;
	private ServletContext context = null;
	
	private SLEmailUtil emailUtil = new SLEmailUtil();

	public SFLibDailyTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();
		if (!isRunning) {
			if (C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) {
				isRunning = true;
				context.log("开始执行定时任务");

				doTask();

				isRunning = false;
				context.log("定时任务执行结束");
			}
		} else {
			context.log("上一次任务执行还未结束");
		}
	}

	// 执行任务
	public void doTask() {
		BorrowServiceImpl borrowService = new BorrowServiceImpl();
		SLBorrowDao borrowDao = new SLBorrowDao();
		
		// 检查书籍超期情况
		ArrayList<SLBorrow> nowBorrowList = borrowService.getBorrowList(BorrowStatusType.NOW, null, Integer.MAX_VALUE, 1).getTheBorrows();
		if (nowBorrowList == null) {
			return;
		}
		for (SLBorrow slBorrow : nowBorrowList) {
			if (slBorrow.getShouldReturnDate().before(new Date())) {
				slBorrow.setStatus("已超期");
				borrowDao.update(slBorrow);
				context.log("设置借阅ID： "+slBorrow.getBorrowId()+" 为已超期");
				//System.out.println("设置借阅ID： "+slBorrow.getBorrowId()+" 为已超期");
			}
		}
		
		// 借书超期处理
		ArrayList<SLBorrow> overdueList = borrowService.getOverdueBorrowList();
		
		for (SLBorrow slBorrow : overdueList) {
			emailUtil.sendOverdueEmail(slBorrow);
		}
		
	}

}
