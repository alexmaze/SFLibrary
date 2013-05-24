package com.successfactors.library.rest.utils;

import java.util.Date;

/**
 * ID生成�?
 * */
public class KeyGenerator {
	public static String getKey(String head, int tail) {
		return (head + String.valueOf((new Date()).getTime()) + String.format("%06d", tail));
	}
}
