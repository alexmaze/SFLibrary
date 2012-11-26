/*
 * $Id$
 */
package com.successfactors.library.server.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.model.SLRecommendedBook;

@SuppressWarnings("rawtypes")
public class SLRecommendedBookDao {
	private static final Logger log = Logger.getLogger(SLRecommendedBookDao.class);

	private Session session = null;

	public SLRecommendedBookDao() {
		log.debug("SLRecommendedBookDao construct is running");
	}

	/**
	 * return the list of books.
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return the empty list if there are no books
	 */
	public List<SLRecommendedBook> queryAll(int pageSize, int pageNo) {
		try {
			log.debug("Start query all books");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLRecommendedBook as p where p.recStatus = '已推荐' order by recRate desc";
			Query q = session.createQuery(hql);
			q.setFirstResult((pageNo - 1) * pageSize);
			q.setMaxResults(pageSize);

			List<SLRecommendedBook> results = new ArrayList<SLRecommendedBook>();
			List list = q.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SLRecommendedBook bean = (SLRecommendedBook) it.next();
				results.add(bean);
			}
			log.debug("query all recommended books successfully");
			return results;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: SLRecommendedBookDao ; Method: queryAll");
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
	public long getCountAll() {
		try {
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "select count(*) from SLRecommendedBook as p where p.recStatus = '已推荐'";
			Query q = session.createQuery(hql);
			if (q.uniqueResult() != null)
				return (Long) q.uniqueResult();
			else
				return 0;
		} catch (HibernateException e) {
			e.printStackTrace();
			return -1;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public SLRecommendedBook queryByISBN(String ISBN) {
		try {
			log.debug("Start query by ISBN");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLRecommendedBook as p where p.bookISBN= ?";
			Query q = session.createQuery(hql);
			q.setString(0, ISBN);
			if (q.uniqueResult() != null)
				return (SLRecommendedBook) q.uniqueResult();
			else
				return null;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: SLRecommendedBookDao ; Method: queryByISBN");
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean insertRecBook(SLRecommendedBook bookBean) {
		try {
			log.debug("start insert recBook");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.save(bookBean);
			session.getTransaction().commit();
			log.debug("insert recBook successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: SLRecommendedBookDao ; Method: insertRecBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean deleteRecBook(SLRecommendedBook bookBean) {
		try {
			log.debug("start delete recBook");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.delete(bookBean);
			session.getTransaction().commit();
			log.debug("delete recBook successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: SLRecommendedBookDao ; Method: deleteRecBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
	
	public boolean updateRecBook(SLRecommendedBook bookBean) {
		try {
			log.debug("start update recBook");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.update(bookBean);
			session.getTransaction().commit();
			log.debug("update recBook successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: SLRecommendedBookDao ; Method: updateRecBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

}
