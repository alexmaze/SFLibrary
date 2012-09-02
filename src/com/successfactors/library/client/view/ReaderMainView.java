package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.HotBookShelf;
import com.successfactors.library.client.widget.NewBookShelf;
import com.successfactors.library.client.widget.RecommendBookList;

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
		
		NewBookShelf northShelf = new NewBookShelf();
		HotBookShelf southShelf = new HotBookShelf();
		RecommendBookList southList = new RecommendBookList();
		
		northShelf.setHeight("40%");
		southShelf.setWidth("60%");
		
		HLayout southLayout = new HLayout();
		southLayout.setMembers(southShelf, southList);
		
		this.setMembers(northShelf, southLayout);
		
	}


	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderMainView view = new ReaderMainView();
			id = view.getID();
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