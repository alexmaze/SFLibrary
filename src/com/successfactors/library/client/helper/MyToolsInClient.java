package com.successfactors.library.client.helper;

import java.util.Date;

/**
 * 一些静态工具函数
 * */
@SuppressWarnings("deprecation")
public class MyToolsInClient {

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
	
	public static String getTenWords(String strContent) {
		
		if(strContent == null) {
			return "......";
		}
		
		if(strContent.length() > 12)
			return strContent.subSequence(1, 12) + "......";
		else
			return strContent + "......";
	}
}
