package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.ReaderOrderListGrid;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;

public class ReaderOrderView extends VLayout {
	
	private static final String DESCRIPTION = "我的当前预订";
	private static final String CONTEXT_AREA_WIDTH = "*";
	
	private ReaderOrderListGrid theListGrid;
	private JumpBar theJumpBar;

	public ReaderOrderView() {
		super();

		GWT.log("初始化: ReaderOrderView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theJumpBar = new JumpBar();
		theListGrid = new ReaderOrderListGrid(theJumpBar);
		
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
			ReaderOrderView view = new ReaderOrderView();
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
