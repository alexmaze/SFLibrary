package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@SuppressWarnings("serial")
@Entity
@Table(name="sl_recommend_history")
public class SLRecommendHistory implements Serializable {
	
	private int historyId;
	private String bookName;
	private String bookISBN;
	
	private String userName;
	private String userEmail;
	private Date recDate;

	@Id
	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	@Transient
	public Record getRecord() {

		Record record = new Record();

		record.setAttribute("historyId", historyId);
		record.setAttribute("bookName", bookName);
		record.setAttribute("bookISBN", bookISBN);

		record.setAttribute("userName", userName);
		record.setAttribute("userEmail", userEmail);
		record.setAttribute("recDate", recDate);
		
		return record;
	}
	
	@Transient
	public static SLRecommendHistory parse(Record record) {

		SLRecommendHistory ret = new SLRecommendHistory();

		ret.setHistoryId(record.getAttributeAsInt("historyId"));
		ret.setBookName(record.getAttribute("bookName"));
		ret.setBookISBN(record.getAttribute("bookISBN"));
		
		ret.setUserName(record.getAttribute("userName"));
		ret.setUserEmail(record.getAttribute("userEmail"));
		ret.setRecDate(record.getAttributeAsDate("recDate"));
		
		return ret;
	}
	
	@Transient
	public static SLRecommendHistory parse(SLRecommendedBook recBook) {

		SLRecommendHistory ret = new SLRecommendHistory();

		ret.setBookName(recBook.getBookName());
		ret.setBookISBN(recBook.getBookISBN());
		
		ret.setUserName(recBook.getRecUserName());
		ret.setUserEmail(recBook.getRecUserEmail());
		ret.setRecDate(recBook.getRecDate());
		
		return ret;
	}
	
}
