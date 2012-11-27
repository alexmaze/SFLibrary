package com.successfactors.library.client.data;

import com.successfactors.library.client.view.ReaderBookView;
import com.successfactors.library.client.view.ReaderBorrowHistoryView;
import com.successfactors.library.client.view.ReaderBorrowView;
import com.successfactors.library.client.view.ReaderOrdeHistoryView;
import com.successfactors.library.client.view.ReaderOrderView;
import com.successfactors.library.client.view.ReaderInfoView;
import com.successfactors.library.client.view.ReaderMainView;
import com.successfactors.library.client.view.ReaderRecBookView;

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
				 new NavigationPaneRecord("reports", "当前借阅", new ReaderBorrowView.Factory(), null),
				 new NavigationPaneRecord("reports", "借阅历史", new ReaderBorrowHistoryView.Factory(), null),
				 new NavigationPaneRecord("reports", "当前预订", new ReaderOrderView.Factory(), null),
				 new NavigationPaneRecord("reports", "预定历史", new ReaderOrdeHistoryView.Factory(), null),
				 new NavigationPaneRecord("reports", "推荐列表", new ReaderRecBookView.Factory(), null),
		};
	}

}