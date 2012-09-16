package com.successfactors.library.client.widget;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ToolBarWithOrderSearch extends ToolStrip {
	
	public enum ToolbarButtonType {
		Search_Button
	}

	private static final String TOOLBAR_HEIGHT = "25px";
	private static final String TOOLSTRIP_WIDTH = "100%";
	
	private static final String SEARCH_BUTTON = "toolbar/search.png";
	
	private static final String SEARCH_BUTTON_DISPLAY_NAME = "搜索";

	ToolStripButton searchButton;
	private DynamicForm searchForm;

	public ToolBarWithOrderSearch() {
		super();

		GWT.log("初始化：Toolbar", null);

		this.setHeight(TOOLBAR_HEIGHT);
		this.setWidth(TOOLSTRIP_WIDTH);
		
		//---------------------------------------------------
		// search part
			this.addSeparator();
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
					"预订人",
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

		this.addSeparator();
	}

	public void addButtonClickHandler(ToolbarButtonType butType, ClickHandler clickHandler) {
		
		if (butType == ToolbarButtonType.Search_Button) {
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