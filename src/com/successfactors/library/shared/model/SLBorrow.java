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
@Table(name="sl_borrow")
public class SLBorrow implements Serializable {
	
	private int borrowId;
	private String userEmail;
	private String bookISBN;
	private Date borrowDate;
	private Date shouldReturnDate;
	private Date returnDate;
	private boolean inStore;
	private boolean overdue;
	private String status;

	//关联实体
	private SLBook theBook;
	private SLUser theUser;
	
	@Id
	public int getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
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
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public Date getShouldReturnDate() {
		return shouldReturnDate;
	}
	public void setShouldReturnDate(Date shouldReturnDate) {
		this.shouldReturnDate = shouldReturnDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isInStore() {
		return inStore;
	}
	public void setInStore(boolean inStore) {
		this.inStore = inStore;
	}
	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
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
	public Record toRecord() {

		Record record = new Record();
		
		record.setAttribute("icon", "reports.png");
		record.setAttribute("borrowId", borrowId);
		record.setAttribute("userEmail", userEmail);
		record.setAttribute("bookISBN", bookISBN);
		record.setAttribute("borrowDate", borrowDate);
		record.setAttribute("shouldReturnDate", shouldReturnDate);
		record.setAttribute("returnDate", returnDate);
		record.setAttribute("inStore", inStore);
		record.setAttribute("overdue", overdue);
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
	public static SLBorrow parse(Record record) {

		SLBorrow ret = new SLBorrow();
		
		ret.setBorrowId(record.getAttributeAsInt("borrowId"));
		ret.setUserEmail(record.getAttributeAsString("userEmail"));
		ret.setBookISBN(record.getAttributeAsString("bookISBN"));
		ret.setBorrowDate(record.getAttributeAsDate("borrowDate"));
		ret.setShouldReturnDate(record.getAttributeAsDate("shouldReturnDate"));
		ret.setReturnDate(record.getAttributeAsDate("returnDate"));
		ret.setInStore(record.getAttributeAsBoolean("inStore"));
		ret.setOverdue(record.getAttributeAsBoolean("overdue"));
		ret.setStatus(record.getAttributeAsString("status"));	

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
