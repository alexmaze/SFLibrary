package com.successfactors.library.server;

import com.successfactors.library.shared.SLEmailUtil;

public class AlexPlay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			SLEmailUtil emailUtil = new SLEmailUtil();
			emailUtil.sendEmail("MinervaBookLib@successfactors.com", "测试邮件76，请忽略！", "Thanks,\n Alex Yan");
			System.out.println("Over!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
