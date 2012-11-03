/**
 * 
 */
package com.successfactors.library.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.successfactors.library.shared.OrderSearchType;
import com.successfactors.library.shared.OrderStatusType;
import com.successfactors.library.shared.model.OrderPage;
import com.successfactors.library.shared.model.SLOrder;
import com.successfactors.library.shared.model.SLUser;

/**
 * @author Alex
 *
 */
public class OrderServiceImplTest {
	
	@SuppressWarnings("serial")
	private OrderServiceImpl orderService = new OrderServiceImpl() {
		@Override
		protected SLUser getCurrentUser() {
			return this.userDao.getSLUserByEmail(USER_EMAIL);
		}
	};
	private final static String USER_EMAIL = "ayan@successfactors.com";
	private final static String BOOK_ISBN = "123456987";

	/**
	 * 测试：预订书籍
	 */
	@Test
	public final void testOrderBook() {
		try {
			orderService.orderBook(BOOK_ISBN);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * 测试：取消预订
	 */
	@Test
	public final void testCancelOrder() {
		try {
			orderService.cancelOrder(2);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * 测试：获取预订信息
	 */
	@Test
	public final void testGetOrderInfo() {
		try {
			SLOrder order = orderService.getOrderInfo(2);
			assertTrue(order.getOrderId() == 2);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * 测试：获取预订列表
	 */
	@Test
	public final void testGetOrderList() {
		try {
			OrderPage page = orderService.getOrderList(OrderStatusType.ORDER_INQUEUE, USER_EMAIL, 10, 1);
			assertTrue(page.getTotalPageNum() == 1);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	/**
	 * 测试：获取搜索结果
	 */
	@Test
	public final void testSearchOrderList() {
		try {
			OrderPage page = orderService.searchOrderList(OrderStatusType.NOW, OrderSearchType.USER_EMAIL, "ayan", 10, 1);
			assertTrue(page.getTotalPageNum() == 1);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

}
