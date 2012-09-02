package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class JumpBar extends HLayout implements Refreshable {
	
	private static final String TOOLBAR_HEIGHT = "22px";

	public enum JumpbarLabelType {
		PRE_PAGE,
		NEXT_PAGE,
	}

	private int nowPageNum;
	private int totalPageNum;
	private Label nextLabel;
	private Label preLabel;
	private Label infoLabel;
	
	public JumpBar() {
		super();

		GWT.log("初始化：JumpBar", null);

		nowPageNum = 0;
		totalPageNum = 0;
		
		this.setStyleName("crm-ToolBar");
		this.setHeight(TOOLBAR_HEIGHT);
		
		infoLabel = new Label("&nbsp&nbsp第"+nowPageNum+"页"+"&nbsp&nbsp共"+totalPageNum+"页&nbsp&nbsp");
		
		nextLabel = new Label("下一页");
		preLabel = new Label("上一页");
		nextLabel.setWidth(60);
		preLabel.setWidth(60);

		infoLabel.setStyleName("alex_jumpbar_label");
		nextLabel.setStyleName("alex_jumpbar_linklabel");
		preLabel.setStyleName("alex_jumpbar_linklabel");

		this.addMember(infoLabel);
		this.addMember(preLabel);
		this.addMember(nextLabel);
		this.setAlign(Alignment.RIGHT);
	}

	public void addLabelClickHandler(JumpbarLabelType labelType, ClickHandler clickHandler) {
		
		if (labelType == JumpbarLabelType.PRE_PAGE) {
			preLabel.addClickHandler(clickHandler);
		} else if (labelType == JumpbarLabelType.NEXT_PAGE) {
			nextLabel.addClickHandler(clickHandler);
		} else {
			GWT.log("JumpBar 按钮绑定单击事件错误 " + labelType.name());
		}
		
	}

	public void refreshView(int nowPage, int totalPage) {

		nowPageNum = nowPage;
		totalPageNum = totalPage;

		infoLabel.setContents("&nbsp&nbsp第"+nowPageNum+"页"+"&nbsp&nbsp共"+totalPageNum+"页&nbsp&nbsp");
	}

	public int getNowPageNum() {
		return nowPageNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}
	
}