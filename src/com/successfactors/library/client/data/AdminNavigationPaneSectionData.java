package com.successfactors.library.client.data;

import com.successfactors.library.client.view.AdminBookManagementView;
import com.successfactors.library.client.view.AdminBorrowManagementView;
import com.successfactors.library.client.view.AdminOrderManagementView;
import com.successfactors.library.client.view.AdminStaticsInformationView;

public class AdminNavigationPaneSectionData {

	private static NavigationPaneRecord[] records;

	public static NavigationPaneRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static NavigationPaneRecord[] getNewRecords() {
		return new NavigationPaneRecord[] {
				 new NavigationPaneRecord("reports", "书库管理", new AdminBookManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "借阅管理", new AdminBorrowManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "预订管理", new AdminOrderManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "统计信息", new AdminStaticsInformationView.Factory(), null),
		 
		};
	}
}