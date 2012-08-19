package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *  欢迎界面，推荐图书，新增图书
 * */
public class ReaderMainView extends VLayout {

	private static final String DESCRIPTION = "欢迎使用";
	private static final String CONTEXT_AREA_WIDTH = "*";

	public ReaderMainView() {
		super();

		GWT.log("初始化: ReaderMainView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
//		initNorthPart();
//		InitSouthPart();
		
	}

//	private void initNorthPart() {
//
//		VLayout northLayout = new HomeProjectIntroBox();
//		northLayout.setHeight("40%");
//		
//		this.addMember(northLayout);
//	}
//
//	private void InitSouthPart() {
//
//		HLayout southLayout = new HLayout();
//		
//		VLayout eastLayout = new HomeProjectListBox();
//		VLayout westLayout = new HomeShareListBox();
//		eastLayout.setWidth("40%");
//		
//		southLayout.setMembers(eastLayout, westLayout);
//		this.addMember(southLayout);
//	}

	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderMainView view = new ReaderMainView();
			id = view.getID();

//			GWT.log("WelcomeView.Factory.create()->view.getID() - " + id, null);
			return view;
		}

		public String getID() {
			return id;
		}

		public String getDescription() {
			return DESCRIPTION;
		}
	}
}