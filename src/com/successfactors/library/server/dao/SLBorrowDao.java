package com.successfactors.library.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;

public class SLBorrowDao {
	private static final Logger log = Logger.getLogger(SLBorrowDao.class);

	private Session session;
	private static SLBorrowDao singleton = null;
	
	private SLBorrowDao() {
		log.info("MysqlBorrowDao construct is running");
		session = HibernateSessionFactory.getSession();
		if (session == null) {
			log.debug("session is null");
		}
	}

	/**
	 * @Author:icelure
	 * @保留实现
	 */
	protected void initDao() {
		// do nothing
	}

	/**
	 * @Author:icelure
	 */
	public boolean save(SLBorrow slBorrow) {
		log.info("MysqlBorrowDao save is running");
		session = HibernateSessionFactory.getSession();
		boolean flag = false;
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.save(slBorrow);
			tran.commit();
			flag = true;
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			flag = false;
			if (tran != null) {
				try {
					tran.rollback();
				} catch (HibernateException he) {
					log.error("save Transaction rollback is error"
							+ he.getMessage());

				}
				re.printStackTrace();
			}

		} finally {
			// session.close() ;//这里就不需要在关闭session直接交给filter来处理
		}
		return flag;

	}

	public boolean remove(SLBorrow slBorrow) {
		log.info("MysqlBorrowDao remove is running");
		session = HibernateSessionFactory.getSession();
		boolean flag = false;
		Transaction tran = null;
		try {

			tran = session.beginTransaction();
			session.delete(slBorrow);
			tran.commit();
			flag = true;
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			flag = false;
			if (tran != null) {
				try {
					tran.rollback();
				} catch (HibernateException he) {
					log.error("delte Transaction rollback is error"
							+ he.getMessage());

				}
				re.printStackTrace();
			}

		} finally {
			// session.close() ;//这里就不需要在关闭session直接交给filter来处理
		}
		return flag;
	}

	/**
	 * Get the borrow instance
	 * 
	 * @param borrowId
	 * @return null if there is no borrow instance with this borrowId
	 */
	public SLBorrow getBorrowById(int borrowId) {
		log.debug("getting SLBorrow instance with borrowId: " + borrowId);
		session = HibernateSessionFactory.getSession();
		SLBorrow slBorrow = new SLBorrow();
		slBorrow = (SLBorrow) session.get(SLBorrow.class, borrowId);
		return slBorrow;
	}

	@SuppressWarnings("unchecked")
	public List<SLBorrow> findByProperty(String propertyName, Object value) {
		log.debug("finding borrow instance with property: " + propertyName
				+ ", value: " + value);
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result;
		try {
			String queryString = "from SLBorrow as model where model."
					+ propertyName + "= '" + value + "'";
			Query q = session.createQuery(queryString);
			result = q.list();
			return result;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public boolean update(SLBorrow slBorrow) {
		log.debug("updating SLBorrow instance ");
		session = HibernateSessionFactory.getSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.clear();
			session.merge(slBorrow);
			session.refresh(slBorrow);
			tran.commit();
			return true;
		} catch (RuntimeException re) {
			log.error("update instance failed", re);
			try {
				if (tran != null)
					tran.rollback();
			} catch (HibernateException he) {
				log.error("update Transaction rollback is error"
						+ he.getMessage());

			}
		}

		return false;
	}

	/**
	 * not used temporarily
	 * 
	 * @param qString
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SLBorrow> executeQuery(String qString, int itemsPerPage,
			int pageNum) {
		session = HibernateSessionFactory.getSession();
		Transaction tran = null;
		ArrayList<SLBorrow> result = null;
		try {
			tran = session.beginTransaction();
			Query q = session.createQuery(qString);
			q.setFirstResult(itemsPerPage * pageNum);
			q.setMaxResults(itemsPerPage);
			result = (ArrayList<SLBorrow>) q.list();
		} catch (RuntimeException re) {
			try {
				if (tran != null)
					tran.rollback();
			} catch (HibernateException he) {
				return null;
			}
		}
		return result;
	}

	/**
	 * will be refactored soon
	 * 
	 * @param borrowType
	 * @param userEmail
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BorrowPage searchBorrowList(BorrowStatusType borrowType,
			String userEmail, int itemsPerPage, int pageNum) {
		session = HibernateSessionFactory.getSession();
		
		BorrowPage retBorrowPage = new BorrowPage();
		List<SLBorrow> result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("userEmail", userEmail));
			String strStatus = BorrowStatusType.parse(borrowType);
			if (strStatus != null) {
				criteria.add(Restrictions.eq("status", strStatus));
			} else {
				this.extend_borrowStatusType(criteria, borrowType);
			}
			criteria.setProjection(Projections.rowCount());

			int totalNum = ((Long)criteria.uniqueResult()).intValue();
			if (totalNum % itemsPerPage == 0) {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage);
			} else {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
			}
			
			criteria.setProjection(null);
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);
			}
			criteria.addOrder(Order.desc("borrowDate"));
			result = criteria.list();
			retBorrowPage.setTheBorrows((ArrayList<SLBorrow>) result);
			
		} catch (RuntimeException re) {
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return retBorrowPage;

	}

	/**
	 * will be refactored soon
	 * 
	 * @param borrowType
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BorrowPage searchBorrowList(BorrowStatusType borrowType,
			int itemsPerPage, int pageNum) {
		session = HibernateSessionFactory.getSession();
		BorrowPage retBorrowPage = new BorrowPage();
		List<SLBorrow> result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			String strStatus = BorrowStatusType.parse(borrowType);
			if (strStatus != null) {
				criteria.add(Restrictions.eq("status", strStatus));
			} else {
				this.extend_borrowStatusType(criteria, borrowType);
			}
			//hack for get count(*)
			criteria.setProjection(Projections.rowCount());

			int totalNum = ((Long)criteria.uniqueResult()).intValue();
			if (totalNum % itemsPerPage == 0) {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage);
			} else {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
			}
			
			criteria.setProjection(null);
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);// 最大显示记录数
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);// 从第几条开始
			}
			criteria.addOrder(Order.desc("borrowDate"));
			result = criteria.list();
			retBorrowPage.setTheBorrows((ArrayList<SLBorrow>) result);
		} catch (RuntimeException re) {
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return retBorrowPage;

	}

	/**
	 * will be refactored soon
	 * 
	 * @param borrowType
	 * @param searchType
	 * @param searchValue
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BorrowPage searchBorrowList(BorrowStatusType borrowType,
			BorrowSearchType searchType, String searchValue, int itemsPerPage,
			int pageNum) {
		session = HibernateSessionFactory.getSession();
		BorrowPage retBorrowPage = new BorrowPage();
		List<SLBorrow> result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			String strStatus = BorrowStatusType.parse(borrowType);
			String strSearch = BorrowSearchType.parse(searchType);
			if (strStatus != null) {
				criteria.add(Restrictions.eq("status", strStatus));
			} else {
				this.extend_borrowStatusType(criteria, borrowType);
			}	
			criteria.add(Restrictions.like(strSearch, searchValue, MatchMode.ANYWHERE));
			criteria.setProjection(Projections.rowCount());
			
			int totalNum = ((Long)criteria.uniqueResult()).intValue();
			if (totalNum % itemsPerPage == 0) {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage);
			} else {
				retBorrowPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
			}
			
			criteria.setProjection(null);
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);// 最大显示记录数
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);// 从第几条开始
			}
			criteria.addOrder(Order.desc("borrowDate"));
			result = criteria.list();
			retBorrowPage.setTheBorrows((ArrayList<SLBorrow>) result);
			
		} catch (RuntimeException re) {
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return retBorrowPage;
	}

	@SuppressWarnings("unchecked")
	public List<SLBorrow> searchBorrowList(BorrowStatusType borrowType) {
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			String strStatus = BorrowStatusType.parse(borrowType);
			if (strStatus != null) {
				criteria.add(Restrictions.eq("status", strStatus));
			} else {
				this.extend_borrowStatusType(criteria, borrowType);
			}
			criteria.addOrder(Order.desc("borrowDate"));
			result = criteria.list();
		} catch (RuntimeException re) {
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return result;
	}

	/**
	 * will refactor soon
	 * 
	 * @param criteria
	 * @param borrowType
	 */
	private void extend_borrowStatusType(Criteria criteria,
			BorrowStatusType borrowType) {
		if (borrowType.equals(BorrowStatusType.BORROW_NEED_TAKE)) {
			criteria.add(Restrictions.and(Restrictions.or(Restrictions.eq("status", "未归还"),
					Restrictions.eq("status", "已超期")), Restrictions.eq("inStore", true)));
			//criteria.add(Restrictions.eq("inStore", "1"));
		} else if (borrowType.equals(BorrowStatusType.HISTORY)) {
			criteria.add(Restrictions.eq("status", "已归还"));
		} else if (borrowType.equals(BorrowStatusType.NOW)) {
			criteria.add(Restrictions.or(Restrictions.eq("status", "未归还"),
					Restrictions.eq("status", "已超期")));
		}
	}

	/**
	 * 获取某本书籍的当前借阅队列，按时间升序排列
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SLBorrow> getNowBorrowListByISBN(String bookISBN) {
		
		session = HibernateSessionFactory.getSession();
		ArrayList<SLBorrow> result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("bookISBN", bookISBN));
			criteria.add(Restrictions.or(Restrictions.eq("status", "未归还"),
					Restrictions.eq("status", "已超期")));
			
			criteria.addOrder(Order.asc("borrowDate"));
			result = (ArrayList<SLBorrow>) criteria.list();
		} catch (RuntimeException re) {
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return result;
	}

	/**
	 * 检验某用户是否已借阅某书
	 * */
	public boolean isUserBookBorrowed(String userEmail, String bookISBN) {
		
		session = HibernateSessionFactory.getSession();
		@SuppressWarnings("rawtypes")
		List result = null;
		try {
			Criteria criteria = session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("userEmail", userEmail));
			criteria.add(Restrictions.eq("bookISBN", bookISBN));
			criteria.add(Restrictions.or(Restrictions.eq("status", "未归还"), Restrictions.eq("status", "已超期")));
			result = criteria.list();
		} catch (RuntimeException re) {
			log.error("isUserBookBorrowed execute error", re);
			throw re;
		}
		
		if (result.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static SLBorrowDao getDao() {
		if (singleton == null) {
			singleton = new SLBorrowDao();
		}
		return singleton;
	}
}
