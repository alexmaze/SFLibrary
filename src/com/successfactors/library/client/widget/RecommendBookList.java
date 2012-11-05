package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 主页展示推荐书目列表
 * */
public class RecommendBookList extends VLayout {

	public RecommendBookList() {
		super();

		GWT.log("初始化: RecommendBookList", null);
		this.setStyleName("alex_myDecoratorPanel");
		this.setMargin(10);

		Label headLabel = new Label(
				"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp最新推荐");
		headLabel.setStyleName("alex_header_label");
		headLabel.setHeight(20);

		this.addMember(headLabel);
		this.setMembersMargin(10);
		this.setPadding(10);

		fetchDataAndDisplay();
	}

	private void fetchDataAndDisplay() {
		// new RPCCall<ArrayList<SLBook>>() {
		// @Override
		// public void onFailure(Throwable caught) {
		// SC.say("通信失败，请检查您的网络连接！");
		// }
		// @Override
		// public void onSuccess(ArrayList<SLBook> result) {
		// if (result == null || result.isEmpty()) {
		// SC.say("暂无资料。。。囧rz");
		// return;
		// }
		// bookList = result;
		// refreshView();
		// }
		// @Override
		// protected void callService(AsyncCallback<ArrayList<SLBook>> cb) {
		// bookService.getHotBookList(BOOK_NUM, cb);
		// }
		// }.retry(3);
	}

}
