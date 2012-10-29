package com.successfactors.library.server;

import java.util.Calendar;
import java.util.TimerTask;

import javax.servlet.ServletContext;

public class SFLibDailyTask extends TimerTask {

	// 每天0点执行
	private static final int C_SCHEDULE_HOUR = 0;
	
	private static boolean isRunning = false;
	private ServletContext context = null;

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
		// TODO Auto-generated method stub
		
	}

}
