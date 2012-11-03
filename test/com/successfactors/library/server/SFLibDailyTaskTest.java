/**
 * 
 */
package com.successfactors.library.server;

import org.junit.Test;

/**
 * @author alex
 *
 */
public class SFLibDailyTaskTest {
	
	SFLibDailyTask dailyTask = new SFLibDailyTask(null);

	/**
	 * 测试：开始执行每日定时任务
	 */
	@Test
	public void testDoTask() {
		dailyTask.doTask();
	}

}
