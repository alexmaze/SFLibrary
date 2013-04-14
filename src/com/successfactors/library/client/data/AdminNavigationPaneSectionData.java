package com.successfactors.library.client.data;

import com.successfactors.library.client.view.AdminBookManagementView;
import com.successfactors.library.client.view.AdminBorrowHistoryView;
import com.successfactors.library.client.view.AdminBorrowManagementView;
import com.successfactors.library.client.view.AdminOrderHistoryView;
import com.successfactors.library.client.view.AdminOrderManagementView;
import com.successfactors.library.client.view.AdminRecBookManagementView;
import com.successfactors.library.client.view.AdminStaticsInformationView;
import com.successfactors.library.client.view.AdminUserManagementView;

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
				 new NavigationPaneRecord("reports", "读者管理", new AdminUserManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "书库管理", new AdminBookManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "借阅管理", new AdminBorrowManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "借阅历史", new AdminBorrowHistoryView.Factory(), null),
				 new NavigationPaneRecord("reports", "预订管理", new AdminOrderManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "预订历史", new AdminOrderHistoryView.Factory(), null),
				 new NavigationPaneRecord("reports", "推荐管理", new AdminRecBookManagementView.Factory(), null),
				 new NavigationPaneRecord("reports", "统计信息(Developing)", new AdminStaticsInformationView.Factory(), null),
		 
		};
	}
}