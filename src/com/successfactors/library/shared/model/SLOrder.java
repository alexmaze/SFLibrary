package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;
import com.successfactors.library.client.helper.MyToolsInClient;

@SuppressWarnings("serial")
@Entity
@Table(name="sl_order")
public class SLOrder implements Serializable {
	
	private int orderId;
	private String userEmail;
	private String bookISBN;
	private Date orderDate;
	private String status;
	
	//关联实体
	private SLBook theBook;
	private SLUser theUser;
	
	@Id
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getBookISBN() {
		return bookISBN;
	}
	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public SLBook getTheBook() {
		return theBook;
	}
	public void setTheBook(SLBook theBook) {
		this.theBook = theBook;
	}
	@Transient
	public SLUser getTheUser() {
		return theUser;
	}
	public void setTheUser(SLUser theUser) {
		this.theUser = theUser;
	}
	
	@Transient
	public Record getRecord() {

		Record record = new Record();

		record.setAttribute("icon", "reports.png");
		record.setAttribute("orderId", orderId);
		record.setAttribute("userEmail", userEmail);
		record.setAttribute("bookISBN", bookISBN);
		record.setAttribute("bookISBN", bookISBN);
		record.setAttribute("orderDate", MyToolsInClient.formatDateTime(orderDate));
		record.setAttribute("status", status);
		
		//------------------------------------------------------
		if (theUser != null) {
			record.setAttribute("userName", theUser.getUserName());
		}
		if (theBook != null) {
			record.setAttribute("bookName", theBook.getBookName());
			record.setAttribute("bookPicUrl", theBook.getBookPicUrl());
		}
		
		return record;
	}
	
	@Transient
	public static SLOrder parse(Record record) {

		SLOrder ret = new SLOrder();
		
		ret.setOrderId(record.getAttributeAsInt("orderId"));
		ret.setUserEmail(record.getAttribute("userEmail"));
		ret.setBookISBN(record.getAttribute("bookISBN"));
		ret.setOrderDate(record.getAttributeAsDate("orderDate"));
		ret.setStatus(record.getAttribute("status"));

		//------------------------------------------------------
		SLUser newUser = new SLUser();
		newUser.setUserName(record.getAttribute("userName"));
		SLBook newBook = new SLBook();
		newBook.setBookName(record.getAttribute("bookName"));
		newBook.setBookPicUrl(record.getAttribute("bookPicUrl"));
		
		ret.setTheUser(newUser);
		ret.setTheBook(newBook);
		
		return ret;
	}
}
