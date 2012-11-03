/**
 * 
 */
package com.successfactors.library.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLUser;

/**
 * @author Alex
 *
 */
public class BorrowServiceImplTest {

	/**
	 * Mock
	 * */
	@SuppressWarnings("serial")
	private BorrowServiceImpl borrowService = new BorrowServiceImpl() {
		@Override
		protected SLUser getCurrentUser() {
			return this.userDao.getSLUserByEmail(USER_EMAIL);
		}
	};
	
	private final static String USER_EMAIL = "cdeng@successfactors.com";
	private final static String BOOK_ISBN = "123456987";

	/**
	 * 测试：借书
	 */
	@Test
	public final void testBorrowBook() {
		assertTrue(borrowService.borrowBook(BOOK_ISBN));
	}

	/**
	 * 测试：还书
	 */
	@Test
	public final void testReturnBook() {
		assertTrue(borrowService.returnBook(4));
	}

	/**
	 * 测试：获取借阅信息
	 */
	@Test
	public final void testGetBorrowInfo() {
		SLBorrow borrow = borrowService.getBorrowInfo(3);
		assertEquals(borrow.getBorrowId(), 3);
	}

	/**
	 * 测试：借阅列表
	 */
	@Test
	public final void testGetBorrowList() {
		BorrowPage page = borrowService.getBorrowList(
				BorrowStatusType.BORROW_RETURNED, null, 
				10, 1);
		assertEquals(page.getPageNum(), 1);
	}

	/**
	 * 测试：搜索借阅列表
	 */
	@Test
	public final void testSearchBorrowList() {
		BorrowPage page = borrowService.searchBorrowList(BorrowStatusType.NOW, BorrowSearchType.USER_EMAIL, "ayan", 10, 1);
		assertEquals(page.getPageNum(), 1);
	}

	/**
	 * 测试：超期借阅列表
	 */
	@Test
	public final void testGetOverdueBorrowList() {
		ArrayList<SLBorrow> list = borrowService.getOverdueBorrowList();
		assertTrue(list.size() == 1);
	}

	/**
	 * 测试：图书出库
	 */
	@Test
	public final void testOutStoreBook() {
		assertTrue(borrowService.outStoreBook(6));
	}

}
