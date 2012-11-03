/**
 * 
 */
package com.successfactors.library.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.successfactors.library.shared.model.SLUser;

/**
 * @author Alex
 *
 */
public class BorrowServiceImplTest {

	
	private BorrowServiceImpl borrowService = new BorrowServiceImpl() {
		@Override
		protected SLUser getCurrentUser() {
			return this.userDao.getSLUserByEmail(USER_EMAIL);
		}
	};
	
	private final static String USER_EMAIL = "ayan@successfactors.com";
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
		fail("Not yet implemented"); // TODO
	}

	/**
	 * 测试：获取借阅信息
	 */
	@Test
	public final void testGetBorrowInfo() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * 测试：借阅列表
	 */
	@Test
	public final void testGetBorrowList() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * 测试：搜索借阅列表
	 */
	@Test
	public final void testSearchBorrowList() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * 测试：超期借阅列表
	 */
	@Test
	public final void testGetOverdueBorrowList() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * 测试：图书出库
	 */
	@Test
	public final void testOutStoreBook() {
		fail("Not yet implemented"); // TODO
	}

}
