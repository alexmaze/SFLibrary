package com.successfactors.library.client.helper;


public class DataSourceIdGenerator {
	
	private static long lc = 0;

	public static String getId(String prefix) {
		if (lc > 2147483648L) {
			lc = 0;
		}
		++lc;
		return prefix + lc;
	}

}
