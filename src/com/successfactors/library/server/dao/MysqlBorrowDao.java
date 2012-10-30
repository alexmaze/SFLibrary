package com.successfactors.library.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.BorrowPage;
import com.successfactors.library.shared.model.SLBorrow;



public class MysqlBorrowDao {
	private static final Logger log = Logger.getLogger(MysqlBorrowDao.class);  

	private Session session;

	public MysqlBorrowDao() {
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
	 * @param borrowId
	 * @return null if there is no borrow instance with this borrowId
	 */
	public SLBorrow getBorrowById(int borrowId) {
		log.debug("getting SLBorrow instance with borrowId: "  + borrowId);
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
		try{
			tran = session.beginTransaction();
			session.clear();
			session.merge(slBorrow);
			session.refresh(slBorrow);
			tran.commit();
			return true;
		}catch(RuntimeException re){
			log.error("update instance failed", re);
			try{
				if(tran != null)
					tran.rollback();
			}catch (HibernateException he) {
				log.error("update Transaction rollback is error"
						+ he.getMessage());

			}
		}
		
		return false;
	}
	
	/**
	 * not used temporarily
	 * @param qString
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SLBorrow> executeQuery(String qString, int itemsPerPage, int pageNum){
		session = HibernateSessionFactory.getSession();
		Transaction tran = null;
		ArrayList<SLBorrow> result = null;
		try{
			tran = session.beginTransaction();
			Query q = session.createQuery(qString);
			q.setFirstResult(itemsPerPage*pageNum);
			q.setMaxResults(itemsPerPage);
			result = (ArrayList<SLBorrow>) q.list();
		}catch(RuntimeException re){
			try{
				if(tran != null)
					tran.rollback();
			}catch (HibernateException he) {
				return null;
			}
		}
		return result;
	}
	
	/**
	 * will be refactored soon
	 * @param borrowType
	 * @param userEmail
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SLBorrow> searchBorrowList(BorrowStatusType borrowType,String userEmail,int itemsPerPage, int pageNum) {
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result = null;
		try{
			String strStatus = BorrowStatusType.parse(borrowType); 
			Criteria criteria=session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("status",strStatus));//eq是等于，gt是大于，lt是小于,or是或
			criteria.add(Restrictions.eq("userEmail", userEmail));
			criteria.addOrder(Order.desc("borrowDate")); 
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);// 最大显示记录数
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);// 从第几条开始
			}
			result = criteria.list();
		}catch(RuntimeException re){
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return result;

	}

	/**
	 * will be refactored soon
	 * @param borrowType
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SLBorrow> searchBorrowList(BorrowStatusType borrowType,int itemsPerPage, int pageNum) {
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result = null;
		try{
			String strStatus = BorrowStatusType.parse(borrowType); 
			Criteria criteria=session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("status",strStatus));//eq是等于，gt是大于，lt是小于,or是或
			criteria.addOrder(Order.desc("borrowDate")); 
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);// 最大显示记录数
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);// 从第几条开始
			}
			result = criteria.list();
		}catch(RuntimeException re){
			log.error("searchBorrowList execute error", re);
			throw re;
		}
		return result;

	}
	
	
	/**
	 * will be refactored soon
	 * @param borrowType
	 * @param searchType
	 * @param searchValue
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SLBorrow> searchBorrowList(BorrowStatusType borrowType, BorrowSearchType searchType,
			String searchValue, int itemsPerPage, int pageNum) {
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result = null;
		try{
			String strStatus = BorrowStatusType.parse(borrowType);
			String strSearch = BorrowSearchType.parse(searchType);
			Criteria criteria=session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("status",strStatus));//eq是等于，gt是大于，lt是小于,or是或
			criteria.add(Restrictions.eq(strSearch, searchValue));
			criteria.addOrder(Order.desc("borrowDate")); 
			if (itemsPerPage > 0 && pageNum > 0) {
				criteria.setMaxResults(itemsPerPage);// 最大显示记录数
				criteria.setFirstResult((pageNum - 1) * itemsPerPage);// 从第几条开始
			}
			result = criteria.list();
		}catch(RuntimeException re){
			log.error("searchBorrowList execute error",re);
			throw re;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<SLBorrow> searchBorrowList(BorrowStatusType statusType) {
		session = HibernateSessionFactory.getSession();
		List<SLBorrow> result = null;
		try{
			String strStatus = BorrowStatusType.parse(statusType);
			Criteria criteria=session.createCriteria(SLBorrow.class);
			criteria.add(Restrictions.eq("status",strStatus));//eq是等于，gt是大于，lt是小于,or是或
			criteria.addOrder(Order.desc("borrowDate")); 
			result = criteria.list();
		}catch(RuntimeException re){
			log.error("searchBorrowList execute error",re);
			throw re;
		}
		return result;
	}

}
