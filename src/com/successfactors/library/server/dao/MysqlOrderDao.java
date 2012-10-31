package com.successfactors.library.server.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.BorrowSearchType;
import com.successfactors.library.shared.BorrowStatusType;
import com.successfactors.library.shared.model.SLBorrow;
import com.successfactors.library.shared.model.SLOrder;

/**
 * 
 * @author haimingli
 *
 */
public class MysqlOrderDao {
	private static final Logger log = Logger.getLogger(MysqlOrderDao.class);
	private Session session = null;
	public static final String SEARCH_ALL ="all";
    private MysqlBookDao bookDao = new MysqlBookDao();
	private SLUserDao userDao = new SLUserDao();

	/**
	 * Construct(do nothing)
	 */
	public MysqlOrderDao() {
		log.debug("MysqlOrderDao construct is running");
	}

	/**
	 * Add a record in database when order a book
	 * @param slOrder
	 * @return whether this action is successful
	 */
	public boolean orderBook(SLOrder slOrder){
		try{
			log.debug("Start: Add order record");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.save(slOrder);
			session.getTransaction().commit();
			log.debug("Successful: Add order record");
			return true;
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("FAILED class:MysqlDao method:addOrderRecord");
			return false;
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	/**
	 * Get the SLOrder in the database according to the given orderId
	 * @param orderId
	 * @return SLOrder When exception happens or orderId is wrong the slOrder is null
	 */
	public SLOrder getSLOrderByOrderId(int orderId){
		try {
		      log.debug("Get order record by order id.");
		      session = HibernateSessionFactory.getSession();
		      String hql = null;
		      hql = "from SLOrder as p where p.orderId= ?";
		      Query q = session.createQuery(hql);
		      q.setLong(0, orderId);
		        return (SLOrder) q.uniqueResult();
		    } catch (HibernateException e) {
		      e.printStackTrace();
		      log.error("FAILED class: MysqlOrderDao method: getSLOrderByOrderId");
		      return null;
		    } finally {
		      HibernateSessionFactory.closeSession();
		    }
	}
	
	/**
	 * Update the record of the given slOrder
	 * @param slOrder
	 * @return Whether this action is successful
	 */
	public boolean updateOrder(SLOrder slOrder){
	    try {
	        log.debug("Start: update order");
	        session = HibernateSessionFactory.getSession();
	        session.beginTransaction();
	        session.update(slOrder);
	        session.getTransaction().commit();
	        log.debug("Successful: update order");
	        return true;
	      } catch (HibernateException e) {
	        e.printStackTrace();
	        session.getTransaction().rollback();
	        log.error("FAILED class: MysqlOrderDao method: updateOrder");
	        return false;
	      } finally {
	        HibernateSessionFactory.closeSession();
	      }
	}
	
	/**
	 * search order list
	 * @param firstType
	 * @param firstValue
	 * @param secondType
	 * @param secondValue
	 * @param itemsPerPage
	 * @param pageNum
	 * @return
	 */
	public List<SLOrder> searchOrderList(String firstType, String firstValue, 
			String secondType, String secondValue, int itemsPerPage, int pageNum, boolean isLike){
	    try {
	    	log.debug("Start: query SLOrder");
	        session = HibernateSessionFactory.getSession();
	        StringBuffer sb = new StringBuffer();
	        sb.append("from SLOrder as p ");
	        sb = getStringBuffer(firstType,firstValue,
	        		secondType,secondValue,isLike,sb);
	        
	        sb.append(" order by orderDate desc ");
	        Query q = session.createQuery(sb.toString());
	        q.setFirstResult((pageNum - 1) * itemsPerPage);
	        q.setMaxResults(itemsPerPage);

	        List<SLOrder> results = new ArrayList<SLOrder>();
	        List list = q.list();
	        Iterator it = list.iterator();
	        while (it.hasNext()) {
	          SLOrder bean = (SLOrder) it.next();
	          bean.setTheBook(bookDao.queryByISBN(bean.getBookISBN()));
	          bean.setTheUser(userDao.getSLUserByEmail(bean.getUserEmail()));
	          results.add(bean);
	        }
	        log.debug("Get SLOrders:" + results.size());
	        return results;
	      } catch (HibernateException e) {
	        e.printStackTrace();
	        log.error("FAILED: class MysqlOrderDao method searchOrderList");
	        return null;
	      } finally {
	        HibernateSessionFactory.closeSession();
	      }
	}
	
	/**
	 * Get the count of the searched orders
	 * @param firstType
	 * @param firstValue
	 * @param secondType
	 * @param secondValue
	 * @return -1 if there is any exception
	 */
	public long selectCount(String firstType, String firstValue, 
			String secondType, String secondValue, boolean isLike){
	    try {
	    	log.debug("Start: select count");
	        session = HibernateSessionFactory.getSession();
	        StringBuffer sb = new StringBuffer();
	        sb.append("select count(*) from SLOrder as p ");
	        sb=getStringBuffer(firstType,firstValue,
	        		secondType,secondValue,isLike,sb);
	        
	        Query q = session.createQuery(sb.toString());
	        if (null != q.uniqueResult()){
	        	return (Long) q.uniqueResult();
	        }else{
	        	return 0;
	        }
	      } catch (HibernateException e) {
	        e.printStackTrace();
	        log.error("FAILED: class MysqlOrderDao method selectCount");
	        return -1;
	      } finally {
	        HibernateSessionFactory.closeSession();
	      }
	}
	
	private StringBuffer getStringBuffer(String firstType,String firstValue,
			String secondType,String secondValue,boolean isLike,StringBuffer sb){
        if(isLike){
        	if(!firstType.equals(SEARCH_ALL)&&firstValue!=null){
        		if(firstValue.equals("历史记录")){
        			sb.append(" where p.status='已取消' or p.status='已借到' ");
        		}else if(firstType.equals("当前记录")){
        			sb.append(" where p.status='排队中' ");
        		}else{
        			sb.append(" where " + getSqlLike(firstType, firstValue));
        		}
	        	if(!secondType.equals(SEARCH_ALL)&&secondValue!=null){
	        		if(secondValue.equals("历史记录")){
	        			sb.append(" and p.status='已取消' or p.status='已借到' ");
	        		}else if(secondValue.equals("当前记录")){
	        			sb.append(" and p.status='排队中' ");
	        		}else{
	        			sb.append(" and " + getSqlLike(secondType, secondValue));
	        		}
	        	}
	        }
        }else{
        	if(!firstType.equals(SEARCH_ALL)&&firstValue!=null){
        		if(firstValue.equals("历史记录")){
        			sb.append(" where p.status='已取消' or p.status='已借到' ");
        		}else if(firstType.equals("当前记录")){
        			sb.append(" where p.status='排队中' ");
        		}else{
        			sb.append(" where " + getSql(firstType, firstValue));
        		}
	        	if(!secondType.equals(SEARCH_ALL)&&secondValue!=null){
	        		if(secondValue.equals("历史记录")){
	        			sb.append(" and p.status='已取消' or p.status='已借到' ");
	        		}else if(secondValue.equals("当前记录")){
	        			sb.append(" and p.status='排队中' ");
	        		}else{
	        			sb.append(" and " + getSql(secondType, secondValue));
	        		}
	        	}
	        }
        }
        return sb;
	}
	
	private String getSql(String type, String value){
		StringBuffer sb = new StringBuffer();
		sb.append(" p." + type + " = " + "'"+value+"'");
		return sb.toString();
	}
	
	private String getSqlLike(String type, String value){
		StringBuffer sb = new StringBuffer();
		sb.append(" p." + type + " like " + "'"+value+"'");
		return sb.toString();
	}
	
	/**
	 * only hack for borrowService
	 * @param bookISBN
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SLOrder getEarlistOrder(String bookISBN){
		session = HibernateSessionFactory.getSession();
		SLOrder slOrder = null;
		try {
			Criteria criteria = session.createCriteria(SLOrder.class);
			criteria.add(Restrictions.eq("bookISBN", bookISBN));// eq是等于，gt是大于，lt是小于,or是或
			criteria.addOrder(Order.asc("orderDate"));
			criteria.setMaxResults(1);
			slOrder = (SLOrder)criteria.uniqueResult();
		} catch (RuntimeException re) {
			log.error("getEarlistOrder execute error", re);
			throw re;
		}
		return slOrder;
	}
}
