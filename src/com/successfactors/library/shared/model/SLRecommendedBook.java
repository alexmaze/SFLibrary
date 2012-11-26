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
@Table(name="sl_recommended_book")
public class SLRecommendedBook implements Serializable {
	
	private String bookName;
	private String bookAuthor;
	private String bookISBN;
	private String bookPublisher;
	private Date bookPublishDate;
	private String bookLanguage;
	private double bookPrice;
	private String bookClass;
	private String bookContributor;
	private String bookIntro;
	private String bookPicUrl;
	
	private String recUserName;
	private String recUserEmail;
	private String recStatus;
	private Date recDate;
	private int recRate;
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	@Id
	public String getBookISBN() {
		return bookISBN;
	}
	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}
	public String getBookPublisher() {
		return bookPublisher;
	}
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	public Date getBookPublishDate() {
		return bookPublishDate;
	}
	public void setBookPublishDate(Date bookPublishDate) {
		this.bookPublishDate = bookPublishDate;
	}
	public String getBookLanguage() {
		return bookLanguage;
	}
	public void setBookLanguage(String bookLanguage) {
		this.bookLanguage = bookLanguage;
	}
	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}
	public String getBookClass() {
		return bookClass;
	}
	public void setBookClass(String bookClass) {
		this.bookClass = bookClass;
	}
	public String getBookContributor() {
		return bookContributor;
	}
	public void setBookContributor(String bookContributor) {
		this.bookContributor = bookContributor;
	}
	public String getBookIntro() {
		return bookIntro;
	}
	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}
	public String getBookPicUrl() {
		return bookPicUrl;
	}
	public void setBookPicUrl(String bookPicUrl) {
		this.bookPicUrl = bookPicUrl;
	}
	public String getRecUserEmail() {
		return recUserEmail;
	}
	public void setRecUserEmail(String recUserEmail) {
		this.recUserEmail = recUserEmail;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public Date getRecDate() {
		return recDate;
	}
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}
	
	public String getRecUserName() {
		return recUserName;
	}
	public void setRecUserName(String recUserName) {
		this.recUserName = recUserName;
	}
	public int getRecRate() {
		return recRate;
	}
	public void setRecRate(int recRate) {
		this.recRate = recRate;
	}
	
	@Transient
	public Record getRecord() {

		Record record = new Record();
		
		record.setAttribute("bookName", bookName);
		record.setAttribute("bookAuthor", bookAuthor);
		record.setAttribute("bookISBN", bookISBN);
		record.setAttribute("bookPublisher", bookPublisher);
		record.setAttribute("bookPublishDate", bookPublishDate);
		record.setAttribute("bookLanguage", bookLanguage);
		record.setAttribute("bookPrice", bookPrice);
		record.setAttribute("bookClass", bookClass);
		record.setAttribute("bookContributor", bookContributor);
		record.setAttribute("bookIntro", bookIntro);
		record.setAttribute("bookPicUrl", bookPicUrl);

		record.setAttribute("recUserName", "推荐人");
		record.setAttribute("recUserEmail", "推荐人邮箱");
		record.setAttribute("recStatus", "推荐状态");
		record.setAttribute("recDate", "推荐日期");
		record.setAttribute("recRate", "推荐热度");
		
		return record;
	}
	
	@Transient
	public static SLRecommendedBook parse(Record record) {

		SLRecommendedBook ret = new SLRecommendedBook();
		
		ret.setBookName(record.getAttribute("bookName"));
		ret.setBookAuthor(record.getAttribute("bookAuthor"));
		ret.setBookISBN(record.getAttribute("bookISBN"));
		ret.setBookPublisher(record.getAttribute("bookPublisher"));
		ret.setBookPublishDate(record.getAttributeAsDate("bookPublishDate"));
		ret.setBookLanguage(record.getAttribute("bookLanguage"));
		ret.setBookPrice(record.getAttributeAsDouble("bookPrice"));
		ret.setBookClass(record.getAttribute("bookClass"));
		ret.setBookContributor(record.getAttribute("bookContributor"));
		ret.setBookIntro(record.getAttribute("bookIntro"));
		ret.setBookPicUrl(record.getAttribute("bookPicUrl"));
		
		ret.setRecUserName(record.getAttribute("recUserName"));
		ret.setRecUserEmail(record.getAttribute("recUserEmail"));
		ret.setRecStatus(record.getAttribute("recStatus"));
		ret.setRecDate(record.getAttributeAsDate("recDate"));
		ret.setRecRate(record.getAttributeAsInt("recRate"));
		
		return ret;
	}
	
	@Transient
	public static SLRecommendedBook parse(SLBook slBook, SLUser slUser) {

		SLRecommendedBook ret = new SLRecommendedBook();
		
		ret.setBookName(slBook.getBookName());
		ret.setBookAuthor(slBook.getBookAuthor());
		ret.setBookISBN(slBook.getBookISBN());
		ret.setBookPublisher(slBook.getBookPublisher());
		ret.setBookPublishDate(slBook.getBookPublishDate());
		ret.setBookLanguage(slBook.getBookLanguage());
		ret.setBookPrice(slBook.getBookPrice());
		ret.setBookClass(slBook.getBookClass());
		ret.setBookContributor("公司采购");
		ret.setBookIntro(slBook.getBookIntro());
		ret.setBookPicUrl(slBook.getBookPicUrl());
		
		ret.setRecUserName(slUser.getUserName());
		ret.setRecUserEmail(slUser.getUserEmail());
		ret.setRecStatus("已推荐");
		ret.setRecRate(1);
		//ret.setRecDate(slBook.getAttributeAsDate("recDate"));
		
		return ret;
	}
}
