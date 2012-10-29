package com.successfactors.library.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import javax.servlet.ServletContext;

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
	private void doTask() {
		//  借书超期处理
		BorrowServiceImpl bookService = new BorrowServiceImpl();
		ArrayList<SLBorrow> overdueList = bookService.getOverdueBorrowList();
		
		for (SLBorrow slBorrow : overdueList) {
			sendOverdueEmail(slBorrow);
		}
		
	}

	private void sendOverdueEmail(SLBorrow slBorrow) {
		// TODO 发送超期邮件
		
		String toEmail = slBorrow.getUserEmail();
		String strTitle = "[Minerva's Book Lib]借书超期提醒";
		String strContent = "亲爱的"+slBorrow.getTheUser().getUserName()+"，\n"
				+ "您于" + slBorrow.getBorrowDate()+ "借阅的" + "《" + slBorrow.getTheBook().getBookName() + "》\n"
				+ "已超过借阅期限，请尽快归还！\n"
				+ "感谢您的支持！\n"
				+ "Minerva's Book Lib\n";
		
		emailUtil.sendEmail(toEmail, strTitle, strContent);
	}


}
