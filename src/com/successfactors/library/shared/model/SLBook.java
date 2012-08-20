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
@Table(name="sl_book")
public class SLBook implements Serializable {
	
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
	private int bookTotalQuantity;
	private int bookInStoreQuantity;
	private int bookAvailableQuantity;
	private String bookPicUrl;
	
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
	public int getBookTotalQuantity() {
		return bookTotalQuantity;
	}
	public void setBookTotalQuantity(int bookTotalQuantity) {
		this.bookTotalQuantity = bookTotalQuantity;
	}
	public int getBookInStoreQuantity() {
		return bookInStoreQuantity;
	}
	public void setBookInStoreQuantity(int bookInStoreQuantity) {
		this.bookInStoreQuantity = bookInStoreQuantity;
	}
	public int getBookAvailableQuantity() {
		return bookAvailableQuantity;
	}
	public void setBookAvailableQuantity(int bookAvailableQuantity) {
		this.bookAvailableQuantity = bookAvailableQuantity;
	}
	public String getBookPicUrl() {
		return bookPicUrl;
	}
	public void setBookPicUrl(String bookPicUrl) {
		this.bookPicUrl = bookPicUrl;
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
		record.setAttribute("bookTotalQuantity", bookTotalQuantity);
		record.setAttribute("bookInStoreQuantity", bookInStoreQuantity);
		record.setAttribute("bookAvailableQuantity", bookAvailableQuantity);
		record.setAttribute("bookPicUrl", bookPicUrl);
		
		return record;
	}
}
