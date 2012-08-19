package com.successfactors.library.shared;

/**
 * 输入框信息正确性验证类
 * */
public class FieldVerifier {
	
	/**
	 * 用户名：字母开头5-16位字母数字下划线组合
	 * */
	public static boolean isUserNameValid(String username) {
		return username == null?false : username.matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}$");
	}
	
	/**
	 * 真实姓名：汉字
	 * */
	public static boolean isRealNameValid(String realname) {
		return realname == null?false : realname
				.matches("[\u4e00-\u9fa5]{1,10}");
	}
	
	/**
	 * 密码为6-16位字符组合!
	 * */
	public static boolean isPasswordValid(String password) {
		return password == null?false : password.matches("^[a-zA-Z0-9_]{6,16}$");
	}

	/**
	 * 电子邮箱验证
	 * */
	public static boolean isEmailValid(String email) {
		return email == null?false : email
				.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}
	
	/**
	 * 输入非空验证
	 * */
	public static boolean isNotEmptyValid(String strinfo) {
		if (strinfo == null)
			return false;
		if (strinfo.length() == 0)
			return false;
		
		return true;
	}
	
	/**
	 * 身份证验证
	 * */
	public static boolean isCerIDValid(String cerID) {
		return cerID == null?false : cerID.matches("\\d{18}|\\d{15}");
	}

	/**
	 * 手机号验证
	 * */
	public static boolean isPhoneValid(String phone) {
		return phone == null?false : phone.matches("(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}");
	}
	
}
