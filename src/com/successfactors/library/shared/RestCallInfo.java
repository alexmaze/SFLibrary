package com.successfactors.library.shared;

import java.util.HashMap;

public class RestCallInfo {
	
	public static final String REST_STATUS = "restStatus";
	public static final String REST_ERROR_CODE = "restErrorCode";
	
	public enum RestCallStatus {
		success,
		fail
	}
	
	public enum RestCallErrorCode {
		no_error,
		unknown_error,
		db_operate_error,
		wrong_email_password,
		json_format_error,
		
		no_available_left,
		already_borrowed,
		already_ordered,
		already_recommended,
		already_instore,

		need_login,
		need_admin_authority,
		
		no_such_book,
		no_such_borrow,
		no_such_order,
		no_such_recommended_book,
		
		can_not_modify_other_person,
		
		can_not_order_while_you_can_borrow, 
		
		can_not_find_this_review,
		
		
	}

	
	public static HashMap<String, Object> getInitRestCallInfo(RestCallStatus restStatus, RestCallErrorCode errorCode) {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put(REST_STATUS, (restStatus == null)?"":restStatus.toString());
		ret.put(REST_ERROR_CODE, (errorCode == null)?"":errorCode.toString());
		return ret;
	}
	
	
}
