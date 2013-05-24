package com.successfactors.library.rest.utils;

import java.util.HashMap;

import com.successfactors.library.shared.CipherUtil;
import com.successfactors.library.shared.model.SLUser;

public class SLSessionManager {

	private static int KEY_COUNT = 0;
	private static HashMap<String, SLUser> USER_SESSION_MAP = new HashMap<String, SLUser>();
	
	public static String newSession(SLUser user) {
		
		String sessionKey = CipherUtil.generatePassword(KeyGenerator.getKey("SESSION_KEY", KEY_COUNT));
		++KEY_COUNT;
		if (KEY_COUNT >= 1000000) {
			KEY_COUNT = 0;
		}
		
		if (!USER_SESSION_MAP.containsKey(sessionKey)) {
			USER_SESSION_MAP.put(sessionKey, user);
		}
		
		return sessionKey;
	}
	
	public static SLUser getSession(String key) {
		return USER_SESSION_MAP.get(key);
	}
	
	public static void removeSession(String key) {
		if (USER_SESSION_MAP.containsKey(key)) {
			USER_SESSION_MAP.remove(key);
		}
	}
	
	public static boolean isLogin(String key) {
		return USER_SESSION_MAP.containsKey(key);
	}
	
}
