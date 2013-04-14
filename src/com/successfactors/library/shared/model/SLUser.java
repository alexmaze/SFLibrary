package com.successfactors.library.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@SuppressWarnings("serial")
@Entity
@Table(name="sl_user")
public class SLUser implements Serializable {
	
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userType;
	private String userDepartment;
	
	private String userFloor;
	private String userPosition;
	
	private String avatarUrl;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Id
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserDepartment() {
		return userDepartment;
	}
	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}
	public String getUserFloor() {
		return userFloor;
	}
	public void setUserFloor(String userFloor) {
		this.userFloor = userFloor;
	}
	public String getUserPosition() {
		return userPosition;
	}
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	@Transient
	public Record getRecord() {
		
		Record record = new Record();
		
		record.setAttribute("userName", userName);
		record.setAttribute("userEmail", userEmail);
		record.setAttribute("userPassword", userPassword);
		record.setAttribute("userType", userType);
		record.setAttribute("userDepartment", userDepartment);
		record.setAttribute("userFloor", userFloor);
		record.setAttribute("userPosition", userPosition);
		record.setAttribute("avatarUrl", avatarUrl);
		
		return record;
	}
	
	@Transient
	public static SLUser parse(Record record) {

		SLUser ret = new SLUser();
		
		ret.setUserName(record.getAttribute("userName"));
		ret.setUserEmail(record.getAttribute("userEmail"));
		ret.setUserPassword(record.getAttribute("userPassword"));
		ret.setUserType(record.getAttribute("userType"));
		ret.setUserDepartment(record.getAttribute("userDepartment"));
		ret.setUserFloor(record.getAttribute("userFloor"));
		ret.setUserPosition(record.getAttribute("userPosition"));
		ret.setAvatarUrl(record.getAttribute("avatarUrl"));
		
		return ret;
	}
}
