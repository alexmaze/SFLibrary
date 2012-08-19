package com.successfactors.library.client.data;

import com.successfactors.library.client.view.ReaderBookView;
import com.successfactors.library.client.view.ReaderBorrowView;
import com.successfactors.library.client.view.ReaderOrderView;
import com.successfactors.library.client.view.ReaderInfoView;
import com.successfactors.library.client.view.ReaderMainView;

public class ReaderNavigationPaneSectionData {

	private static NavigationPaneRecord[] records;

	public static NavigationPaneRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static NavigationPaneRecord[] getNewRecords() {
		return new NavigationPaneRecord[] {
				 new NavigationPaneRecord("dashboards", "首页", new ReaderMainView.Factory(), null),
				 new NavigationPaneRecord("reports", "书目列表", new ReaderBookView.Factory(), null),
				 new NavigationPaneRecord("contact", "我的信息", new ReaderInfoView.Factory(), null),
				 new NavigationPaneRecord("reports", "我的借阅", new ReaderBorrowView.Factory(), null),
				 new NavigationPaneRecord("reports", "我的预定", new ReaderOrderView.Factory(), null),
		};
	}

}