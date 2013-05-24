package com.successfactors.library.rest.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些静态工具函数
 * */
@SuppressWarnings("deprecation")
public class MyTools {

	/**
	 * 将字符串 "YY-MM-DD hh:mm:ss" 转换为日期
	 * */
	public static Date getDateFormString(String strDate, String strTime)
	{
		Date ret = new Date();
		
		String[] arrDate = strDate.split("-");
		
		ret.setYear(Integer.parseInt(arrDate[0])-1900);
		ret.setMonth(Integer.parseInt(arrDate[1])-1);
		ret.setDate(Integer.parseInt(arrDate[2]));
		
		String[] arrTime = strTime.split(":");
		ret.setHours(Integer.parseInt(arrTime[0]));
		ret.setMinutes(Integer.parseInt(arrTime[1]));
		ret.setSeconds(Integer.parseInt(arrTime[2]));
		
		return ret;
	}

	/**
	 * 格式化日期，返回 YYYY-MM-DD
	 * */
	public static String formatDate(Date dt)
	{
		if (dt == null) {
			return "";
		}
		String ret = new String();
		
		ret = String.valueOf(dt.getYear()+1900) + "-"
		+ String.valueOf(dt.getMonth()+1) + "-"
		+ String.valueOf(dt.getDate());
		
		return ret;
	}
	
	/**
	 * 格式化时间，返回 YYYY-MM-DD hh:mm:ss
	 * */
	public static String formatDateTime(Date dt)
	{
		if (dt == null) {
			return "";
		}
		String ret = new String();
		
		ret = formatDate(dt) + " " + formatTime(dt);
		
		return ret;
	}

	/**
	 * 格式化时间，返回 hh:mm:ss
	 * */
	public static String formatTime(Date dt)
	{
		if (dt == null) {
			return "";
		}
		String ret = new String();
		
		ret = String.valueOf(dt.getHours()) + ":"
		+ String.valueOf(dt.getMinutes()) + ":"
		+ String.valueOf(dt.getSeconds());
		
		return ret;
	}
	
	/**
	 * 返回时段：上午、下午、晚上
	 * */
	public static String getTimePeriod(Date dt)
	{
		if (dt == null) {
			return "";
		}
		String ret = new String();
		
		int hour = dt.getHours();
		
		if(hour < 12) {
			ret = "上午";
		} else if(hour < 18) {
			ret = "下午";
		} else {
			ret = "晚上";
		}
		
		return ret;
	}
	
	/**
	 * 用于生成ID（根据time）
	 * */
	public static String generateID(String strHead) {
		String ret = strHead + (new Date()).getTime();
		return ret;
	}
	
	public static String getWords(int num, String strContent) {
		
		if(strContent == null) {
			return "......";
		}
		
		if(strContent.length() > num)
			return strContent.subSequence(0, num) + "......";
		else
			return strContent + "......";
	}
	public static String getWordsForShort(int num, String strContent) {
		
		if(strContent == null) {
			return "";
		}
		
		if(strContent.length() > num)
			return strContent.subSequence(0, num) + "...";
		else
			return strContent;
	}
	


	
	/**
	 * 功能：将字符串 "yy-MM-dd" 转换为日期
	 * @param strDate "yy-MM-dd"
	 * @return Date 日期 
	 * */
	public static Date getDateFormString(String strDate)
	{
		if (strDate == null || strDate.equals("")) {
			return null;
		}
		
		Date ret = new Date();
		
		String[] arrDate = strDate.split("-");
		if(arrDate.length == 1) {
			ret.setYear(Integer.parseInt(arrDate[0])-1900);
			ret.setMonth(1);
			ret.setDate(1);
		} else if(arrDate.length == 2) {
			ret.setYear(Integer.parseInt(arrDate[0])-1900);
			ret.setMonth(Integer.parseInt(arrDate[1])-1);
			ret.setDate(1);
		} else if(arrDate.length >= 3){
			ret.setYear(Integer.parseInt(arrDate[0])-1900);
			ret.setMonth(Integer.parseInt(arrDate[1])-1);
			ret.setDate(Integer.parseInt(arrDate[2]));
		}
		
		
		return ret;
	}
	
	/**
	 * 过滤字符串后转换为Double
	 */
	public static Double getDouble(String str) {
		if (str == null || str.equals("")) {
			return 0.0;
		}
		// 只允数字
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		String cleanString = m.replaceAll("").trim();
		if (cleanString.equals("")) {
			return 0.0;
		}
		return Double.parseDouble(cleanString);
	}

	/**
	 * 过滤字符串中的[]
	 */
	public static String getStringWithoutBrcket(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String regEx="[\\[\\]]";  
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
	}
}
