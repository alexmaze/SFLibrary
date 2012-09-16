package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ReaderBorrowListGrid;
import com.successfactors.library.client.widget.ToolBarWithBorrowSearch;
import com.successfactors.library.client.widget.ToolBarWithBorrowSearch.ToolbarButtonType;

public class ReaderBorrowView extends VLayout {
	
	private static final String DESCRIPTION = "我的借阅";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ToolBarWithBorrowSearch theToolbar;
	private ReaderBorrowListGrid theListGrid;
	private JumpBar theJumpBar;

	public ReaderBorrowView() {
		super();

		GWT.log("初始化: ReaderBorrowView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolBarWithBorrowSearch(false, true);
		theJumpBar = new JumpBar();
		theListGrid = new ReaderBorrowListGrid(theJumpBar);
		
		this.setMembers(theToolbar, theListGrid, theJumpBar);
		bind();
		
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.Need_Return, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.showNeedReturnWindow();
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Need_Tooken, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.showNeedTookenWindow();
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage(theToolbar.getSearchInfo());
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage(theToolbar.getSearchInfo());
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
