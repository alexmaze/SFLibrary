package com.successfactors.library.rest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.successfactors.library.shared.model.SLRecommendedBook;
import com.successfactors.library.shared.model.SLUser;

@SuppressWarnings("rawtypes")
public class JSONHelper {

	public static SLRecommendedBook parseMapToSLRecommendedBook(Map mapInfo) {
		SLRecommendedBook ret = new SLRecommendedBook();
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ret.setBookName(mapInfo.containsKey("bookName")?(String)mapInfo.get("bookName"):"");
		ret.setBookAuthor(mapInfo.containsKey("bookAuthor")?(String)mapInfo.get("bookAuthor"):"");
		ret.setBookISBN(mapInfo.containsKey("bookISBN")?(String)mapInfo.get("bookISBN"):"");
		ret.setBookPublisher(mapInfo.containsKey("bookPublisher")?(String)mapInfo.get("bookPublisher"):"");
		ret.setBookPublishDate(mapInfo.containsKey("bookPublishDate")?sdf.parse((String)mapInfo.get("bookPublishDate")):null);
		ret.setBookLanguage(mapInfo.containsKey("bookLanguage")?(String)mapInfo.get("bookLanguage"):"");
		ret.setBookPrice(mapInfo.containsKey("bookPrice")?(Double)mapInfo.get("bookPrice"):0.0);
		ret.setBookClass(mapInfo.containsKey("bookClass")?(String)mapInfo.get("bookClass"):"");
		ret.setBookContributor(mapInfo.containsKey("bookContributor")?(String)mapInfo.get("bookContributor"):"");
		ret.setBookIntro(mapInfo.containsKey("bookIntro")?(String)mapInfo.get("bookIntro"):"");
		ret.setBookPicUrl(mapInfo.containsKey("bookPicUrl")?(String)mapInfo.get("bookPicUrl"):"");
		
		ret.setRecUserName(mapInfo.containsKey("recUserName")?(String)mapInfo.get("recUserName"):"");
		ret.setRecUserEmail(mapInfo.containsKey("recUserEmail")?(String)mapInfo.get("recUserEmail"):"");
		ret.setRecStatus(mapInfo.containsKey("recStatus")?(String)mapInfo.get("recStatus"):"");
		ret.setRecDate(mapInfo.containsKey("recDate")?sdf.parse((String)mapInfo.get("recDate")):null);
		ret.setRecRate(mapInfo.containsKey("recRate")?Integer.valueOf(((Long)mapInfo.get("recRate")).intValue()):0);
		ret.setCountPrice(mapInfo.containsKey("countPrice")?(Double)mapInfo.get("countPrice"):0.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static SLUser parseMapToSLUser(Map mapInfo) {

		SLUser ret = new SLUser();
		
		ret.setUserName(mapInfo.containsKey("userName")?(String)mapInfo.get("userName"):"");
		ret.setUserEmail(mapInfo.containsKey("userEmail")?(String)mapInfo.get("userEmail"):"");
		ret.setUserPassword(mapInfo.containsKey("userPassword")?(String)mapInfo.get("userPassword"):"");
		ret.setUserType(mapInfo.containsKey("userType")?(String)mapInfo.get("userType"):"");
		ret.setUserDepartment(mapInfo.containsKey("userDepartment")?(String)mapInfo.get("userDepartment"):"");
		
		return ret;
	}
	
}
