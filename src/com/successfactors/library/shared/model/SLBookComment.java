package com.successfactors.library.shared.model;

import java.io.Serializable;
import java.util.Date;

public class SLBookComment implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9077928960918307344L;

	private String bookISBN;
	private String userEmail;
	
	private String comment;
	private Date postDate;
	
	public SLBookComment() {
		
	}

	public String getBookISBN() {
		return bookISBN;
	}

	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
}
