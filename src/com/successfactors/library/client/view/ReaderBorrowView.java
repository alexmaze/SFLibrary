package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ReaderBorrowListGrid;

public class ReaderBorrowView extends VLayout {
	
	private static final String DESCRIPTION = "我的当前借阅";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ReaderBorrowListGrid theListGrid;
	private JumpBar theJumpBar;

	public ReaderBorrowView() {
		super();

		GWT.log("初始化: ReaderBorrowView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theJumpBar = new JumpBar();
		theListGrid = new ReaderBorrowListGrid(theJumpBar);
		
		this.setMembers(theListGrid, theJumpBar);
		bind();
		
	}
	
	private void bind() {
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage();
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage();
			}
		});
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderBorrowView view = new ReaderBorrowView();
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
