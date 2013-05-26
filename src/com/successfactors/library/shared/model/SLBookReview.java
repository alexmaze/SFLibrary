package com.successfactors.library.shared.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Entity
@Table(name="sl_book_review")
public class SLBookReview implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4688656576934914502L;

	private Long reviewId;
	private String bookISBN;
	private String userEmail;
	
	private String title;
	private String subTitle;
	private String content;
	private Date postDate;
	
	public SLBookReview() {
		
	}

	@Id
	@GeneratedValue
	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	
	@Transient
	public Record toRecord() {

		Record record = new Record();
		
		record.setAttribute("reviewId", reviewId);
		record.setAttribute("bookISBN", bookISBN);
		record.setAttribute("userEmail", userEmail);
		record.setAttribute("title", title);
		record.setAttribute("subTitle", subTitle);
		record.setAttribute("content", content);
		record.setAttribute("postDate", postDate);

		record.setAttribute("icon", "icons/16/reports.png");
		
		return record;
	}
	
	@Transient
	public static SLBookReview parse(Record record) {

		SLBookReview ret = new SLBookReview();
		
		ret.setReviewId(record.getAttributeAsLong("reviewId"));
		ret.setBookISBN(record.getAttribute("bookISBN"));
		ret.setUserEmail(record.getAttribute("userEmail"));
		ret.setTitle(record.getAttribute("title"));
		ret.setSubTitle(record.getAttribute("subTitle"));
		ret.setContent(record.getAttribute("content"));
		ret.setPostDate(record.getAttributeAsDate("postDate"));
		
		return ret;
	}
}
