package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ToolbarWithBookSearch extends ToolStrip {
	
	public enum ToolbarButtonType {
		New_Button,
		Delete_Button,
		Update_Button,
		Search_Button,
	}

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "100%";

	private static final String NEW_BUTTON = "toolbar/new.png";
	private static final String DELETE_BUTTON = "toolbar/delete.png";

	private static final String APPLY_BUTTON = "toolbar/reports.png";
	private static final String SEARCH_BUTTON = "toolbar/search.png";

	private static final String NEW_BUTTON_DISPLAY_NAME = "新建";
	private static final String DELETE_BUTTON_DISPLAY_NAME = "删除";
	private static final String UPDATE_BUTTON_DISPLAY_NAME = "修改";
	private static final String SEARCH_BUTTON_DISPLAY_NAME = "搜索";

	ToolStripButton newButton;
	ToolStripButton deleteButton;
	ToolStripButton updateButton;
	ToolStripButton searchButton;

	private DynamicForm searchForm;

	public ToolbarWithBookSearch(boolean canEdit) {
		super();

		GWT.log("初始化：Toolbar", null);

		this.setHeight(TOOLBAR_HEIGHT);
		this.setWidth(TOOLSTRIP_WIDTH);

		if (canEdit) {
			
			newButton = new ToolStripButton();
			newButton.setIcon(NEW_BUTTON);
			newButton.setTitle(NEW_BUTTON_DISPLAY_NAME);
			this.addButton(newButton);
			this.addSeparator();

			deleteButton = new ToolStripButton();
			deleteButton.setIcon(DELETE_BUTTON);
			deleteButton.setTitle(DELETE_BUTTON_DISPLAY_NAME);
			this.addButton(deleteButton);

			this.addSeparator();
			
			updateButton = new ToolStripButton();
			updateButton.setIcon(APPLY_BUTTON);
			updateButton.setTitle(UPDATE_BUTTON_DISPLAY_NAME);
			this.addButton(updateButton);

			this.addSeparator();
			
		}
		
		//---------------------------------------------------
		// search part
		searchForm = new DynamicForm();
		searchForm.setWidth("300px");
		searchForm.setColWidths(100,"*",50,"*");
		searchForm.setNumCols(4);
		searchForm.setColWidths(100,100,100,100);
		TextItem searchKeyWordItem = new TextItem("searchKeyWord", "关键字");
		SelectItem searchClassItem = new SelectItem("searchClass", "分类");
		searchClassItem.setValueMap(
				"全部",
				"书名",
				"作者",
				"类别",
				"语言",
				"出版社",
				"贡献者"
				);
		searchClassItem.setDefaultValue("全部");
		searchKeyWordItem.setWidth(100);
		searchKeyWordItem.setHeight(20);
		searchClassItem.setHeight(20);
		searchClassItem.setWidth(100);
		searchForm.setFields(searchKeyWordItem, searchClassItem);
		this.addMember(searchForm);

		
		searchButton = new ToolStripButton();
		searchButton.setIcon(SEARCH_BUTTON);
		searchButton.setTitle(SEARCH_BUTTON_DISPLAY_NAME);
		this.addButton(searchButton);
	}

	public void addButtonClickHandler(ToolbarButtonType butType, ClickHandler clickHandler) {
		
		if (butType == ToolbarButtonType.New_Button) {
			newButton.addClickHandler(clickHandler);
		} else if (butType == ToolbarButtonType.Delete_Button) {
			deleteButton.addClickHandler(clickHandler);
		} else if (butType == ToolbarButtonType.Update_Button) {
			updateButton.addClickHandler(clickHandler);
		} else if (butType == ToolbarButtonType.Search_Button) {
			searchButton.addClickHandler(clickHandler);
		} else {
			GWT.log("Toolbar 按钮绑定单击事件错误 " + butType.name());
		}
		
	}
	
	public String[] getSearchInfo() {
		return new String[]{
				searchForm.getValueAsString("searchKeyWord"),
				searchForm.getValueAsString("searchClass")
		};
	}

}