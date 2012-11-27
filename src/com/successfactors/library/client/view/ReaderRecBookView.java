package com.successfactors.library.client.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.ReaderRecBookListGrid;
import com.successfactors.library.client.widget.JumpBar;
import com.successfactors.library.client.widget.RecommendedBookWindow;
import com.successfactors.library.client.widget.ToolbarWithRecButton;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;
import com.successfactors.library.client.widget.ToolbarWithRecButton.ToolbarButtonType;

public class ReaderRecBookView extends VLayout {

	private static final String DESCRIPTION = "推荐列表";
	private static final String CONTEXT_AREA_WIDTH = "*";

	private ReaderRecBookListGrid theListGrid;
	private JumpBar theJumpBar;
	private ToolbarWithRecButton theToolbar;
	
	public ReaderRecBookView() {
		super();

		GWT.log("初始化: ReaderRecBookView", null);

		this.setStyleName("crm-ContextArea");
		this.setWidth(CONTEXT_AREA_WIDTH);
		
		theJumpBar = new JumpBar();
		theToolbar = new ToolbarWithRecButton();
		theListGrid = new ReaderRecBookListGrid(theJumpBar);
		
		this.addMember(theToolbar);
		this.addMember(theListGrid);
		this.addMember(theJumpBar);
		
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
		theToolbar.addButtonClickHandler(ToolbarButtonType.Recommend_Button, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RecommendedBookWindow recommendedBookEditWindow = new RecommendedBookWindow();
				recommendedBookEditWindow.show();
			}
		});
	}
	
	public static class Factory implements ContextAreaFactory {

		private String id;

		public Canvas create() {
			ReaderRecBookView view = new ReaderRecBookView();
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
