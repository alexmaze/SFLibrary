package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ReaderBookListGrid;
import com.successfactors.library.client.widget.RecommendedBookEditWindow;
import com.successfactors.library.client.widget.ToolbarWithBookSearch;
import com.successfactors.library.client.widget.ToolbarWithBookSearch.ToolbarButtonType;

public class ReaderBookView extends VLayout {
	
	private static final String DESCRIPTION = "图书查询";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ToolbarWithBookSearch theToolbar;
	private ReaderBookListGrid theListGrid;
	private JumpBar theJumpBar;
	
	private boolean isSearchMode = false;
	
	public ReaderBookView() {
		super();

		GWT.log("初始化: ReaderBookView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theToolbar = new ToolbarWithBookSearch(false);
		theJumpBar = new JumpBar();
		theListGrid = new ReaderBookListGrid(theJumpBar);
		
		this.addMember(theToolbar);
		this.addMember(theListGrid);
		this.addMember(theJumpBar);
		
		bind();
		
	}
	
	private void bind() {
		theToolbar.addButtonClickHandler(ToolbarButtonType.Search_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				isSearchMode = true;
				theListGrid.doSearchBook(theToolbar.getSearchInfo());
			}
		});
		theToolbar.addButtonClickHandler(ToolbarButtonType.Recommend_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RecommendedBookEditWindow recommendedBookEditWindow = new RecommendedBookEditWindow();
				recommendedBookEditWindow.show();
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.NEXT_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doNextPage(isSearchMode, theToolbar.getSearchInfo());
			}
		});
		theJumpBar.addLabelClickHandler(JumpbarLabelType.PRE_PAGE, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				theListGrid.doPrePage(isSearchMode, theToolbar.getSearchInfo());
			}
		});
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderBookView view = new ReaderBookView();
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
