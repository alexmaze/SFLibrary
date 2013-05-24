package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.successfactors.library.client.datasource.SLUserDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.client.service.UserServiceAsync;
import com.successfactors.library.client.widget.UserInfoPanel.FinishUserInfoEdit;
import com.successfactors.library.shared.model.SLUser;
import com.successfactors.library.shared.model.UserPage;

public class UserListGrid extends ListGrid implements FinishUserInfoEdit {

	
	
	public UserServiceAsync userService = GWT.create(UserService.class);
	
	public static final int DEFAULT_RECORDS_EACH_PAGE = 12;
	
	public static final int DEFAULT_IMG_HEIGHT = 40;
	public static final int DEFAULT_IMG_WIDTH = 40;
	public static final int DEFAULT_CELL_HEIGHT = 42;
	
	private Refreshable jumpBar;
	private int pageNowNum = 1;
	private int pageTotalNum = 1;
	
	private SLUserDS userListDS = new SLUserDS();
	
	public UserListGrid(Refreshable theJumpBar) {
		super();
		this.jumpBar = theJumpBar;
		
		this.setShowAllRecords(true);
		this.setCellHeight(DEFAULT_CELL_HEIGHT);
		
		ListGridField avatarSmallUrlField = new ListGridField("avatarUrl", "头像", 45);
		avatarSmallUrlField.setType(ListGridFieldType.IMAGE);
		avatarSmallUrlField.setImageHeight(DEFAULT_IMG_HEIGHT);
		avatarSmallUrlField.setImageWidth(DEFAULT_IMG_WIDTH);
		
		ListGridField nameField = new ListGridField("userName", "用户名", 145);
		
		ListGridField prorityField = new ListGridField("userType", "权限", 55);
		ListGridField categoryField = new ListGridField("userDepartment", "部门", 100);
		
		this.setFields(avatarSmallUrlField, nameField, prorityField, categoryField);
		
		updateDS(DEFAULT_RECORDS_EACH_PAGE, 1);
		this.setDataSource(userListDS);
		this.setAutoFetchData(true);
		
		
	}

	private void updateDS(final int numPerPage, final int pageNum) {
		new RPCCall<UserPage>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(UserPage result) {
				if (result == null) {
					SC.say("警告", "查询出错，请刷新页面后重试！");
					return;
				}
				
				for (SLUser user : result.getTheUsers()) {
					userListDS.addData(user.toRecord());
				}
				
				pageNowNum = result.getPageNum();
				pageTotalNum = result.getTotalPageNum();
				jumpBar.refreshView(pageNowNum, pageTotalNum);
			}

			@Override
			protected void callService(
					AsyncCallback<UserPage> cb) {
				userService.searchUserList(null, null, null, null, null, null, numPerPage, pageNum, cb);
			}
		}.retry(3);
	}

	public void doNextPage() {
		if (pageNowNum >= pageTotalNum) {
			SC.say("已到最后一页！");
			return;
		}
		for (Record record : this.getRecords()) {
			userListDS.removeData(record);
		}
		updateDS(DEFAULT_RECORDS_EACH_PAGE, pageNowNum + 1);
	}

	public void doPrePage() {
		if (pageNowNum <= 1) {
			SC.say("已到第一页！");
			return;
		}
		for (Record record : this.getRecords()) {
			userListDS.removeData(record);
		}

		updateDS(DEFAULT_RECORDS_EACH_PAGE, pageNowNum - 1);
	}

	/**
	 * 删除用户
	 * */
	public void deleteUser() {
		SC.ask("删除用户", "您确定要删除这个用户么？", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {

					new RPCCall<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							SC.say("网络繁忙，请稍后重试！");
						}

						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								userListDS.removeData(getSelectedRecord());
								SC.say("删除成功！");
							} else {
								SC.say("删除失败，请稍后重试！");
							}
						}

						@Override
						protected void callService(
								AsyncCallback<Boolean> cb) {
							userService.deleteUserByEmail(getSelectedRecord().getAttribute("userEmail"), cb);
						}
					}.retry(3);
					
				}
			}
		});
	}

	@Override
	public void doRefreshPage() {
		for (Record record : this.getRecords()) {
			userListDS.removeData(record);
		}

		updateDS(DEFAULT_RECORDS_EACH_PAGE, pageNowNum);
	}

}
