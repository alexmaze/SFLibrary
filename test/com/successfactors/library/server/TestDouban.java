package com.successfactors.library.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stringtree.json.JSONReader;
import org.stringtree.json.JSONValidatingReader;

import com.successfactors.library.shared.model.SLBook;

public class TestDouban {

	private static final String DOUBAN_API_URL = "https://api.douban.com/v2/book/isbn/";
	private static final String DOUBAN_API_KEY = "?apikey={0b71b06d4ed8d8a722551147ec8a89f5}";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SLBook theBook = getBookByDoubanAPI("9787308103459");
		
		theBook.getBookAddDate();
	}

	@SuppressWarnings("rawtypes")
	public static SLBook getBookByDoubanAPI(String bookISBN) {
		String strUrl = DOUBAN_API_URL + bookISBN + DOUBAN_API_KEY;
		BufferedReader in = null;
		
		SLBook retBook = null;
		
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setDoInput(true);
			in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), "UTF-8"));
			String line;
			String jsonResult = "";
			while ((line = in.readLine()) != null) {
				jsonResult += line;
			}
			in.close();
			if (jsonResult.length() == 0) {
				return null;
			}

			// 解析Json信息
			JSONReader reader = new JSONValidatingReader();
			HashMap result = null;
			result = (HashMap) reader.read(jsonResult);

			if (result == null || result.isEmpty()) {
				return null;
			}
			
			retBook = new SLBook();
			retBook.setBookName(result.containsKey("title")?result.get("title").toString():null);
			retBook.setBookAuthor(result.containsKey("author")?getStringWithoutBrcket(result.get("author").toString()):null);
			retBook.setBookISBN(bookISBN);
			retBook.setBookPublisher(result.containsKey("publisher")?result.get("publisher").toString():null);
			retBook.setBookPublishDate(result.containsKey("pubdate")?getDateFormString(result.get("pubdate").toString()):null);
			retBook.setBookClass("");
			retBook.setBookLanguage("");
			retBook.setBookContributor("公司采购");
			retBook.setBookPrice(result.containsKey("price")?getDouble(result.get("price").toString()):null);

			retBook.setBookIntro(result.containsKey("summary")?result.get("summary").toString():null);

			if (result.containsKey("images")) {
				HashMap imagesMap = (HashMap) result.get("images");
				retBook.setBookPicUrl(imagesMap.containsKey("large")?imagesMap.get("large").toString():
					(imagesMap.containsKey("medium")?imagesMap.get("medium").toString():
						(imagesMap.containsKey("medium")?imagesMap.get("medium").toString():"./images/upload/nopic.jpg")));
			} else {
				retBook.setBookPicUrl("./images/upload/nopic.jpg");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retBook;
	}
	
	/**
	 * 功能：将字符串 "yy-MM-dd" 转换为日期
	 * @param strDate "yy-MM-dd"
	 * @return Date 日期 
	 * */
	@SuppressWarnings("deprecation")
	public static Date getDateFormString(String strDate)
	{
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
		} else {
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
		// 只允数字
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return Double.parseDouble(m.replaceAll("").trim());
	}
	
	/**
	 * 过滤字符串中的[]
	 */
	public static String getStringWithoutBrcket(String str) {
		String regEx="[\\[\\]]";  
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
	}
}
