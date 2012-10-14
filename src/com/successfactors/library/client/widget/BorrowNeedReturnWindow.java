package com.successfactors.library.client.widget;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.widget.JumpBar.JumpbarLabelType;

public class BorrowNeedReturnWindow  extends Window {

	private static final String WINDOW_WIDTH = "580px";
	private static final String WINDOW_HEIGHT = "340px";

	private BorrowNeedReturnListGrid theListGrid;
	private JumpBar theJumpBar;
	
	public BorrowNeedReturnWindow() {
		super();
		initWindow();
	}
	
	private void initWindow() {
		
		this.setAutoSize(true);
		this.setTitle("超期未还记录");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VLayout vLayout = new VLayout();
		
		theJumpBar = new JumpBar();
		theListGrid = new BorrowNeedReturnListGrid(theJumpBar);
		
		vLayout.setMembers(theListGrid, theJumpBar);
		vLayout.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.addItem(vLayout);

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
}
