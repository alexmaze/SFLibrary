package com.successfactors.library.server;

import java.util.Timer;

import javax.servlet.ServletContextEvent;

public class DailyTaskListener implements javax.servlet.ServletContextListener {

	// 任务触发周期
	private static final long TASK_PERIOD = 60 * 60 * 1000;
	
	private Timer timer = new Timer();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
		arg0.getServletContext().log("每日任务定时器已销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		timer = new Timer(true);
		timer.schedule(new SFLibDailyTask(arg0.getServletContext()), 0, TASK_PERIOD);
		
		arg0.getServletContext().log("每日任务定时器已启动");
		arg0.getServletContext().log("已经添加任务调度表");
	}

}
