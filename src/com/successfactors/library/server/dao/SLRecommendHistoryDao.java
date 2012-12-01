/*
 * $Id$
 */
package com.successfactors.library.server.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.model.SLRecommendHistory;

public class SLRecommendHistoryDao {
	private static final Logger log = Logger.getLogger(SLRecommendHistoryDao.class);

	private Session session = null;

	public SLRecommendHistoryDao() {
		log.debug("SLRecommendHistoryDao construct is running");
	}

	public boolean isRecommend(String bookISBN, String userEmail) {
		try {
			log.debug("Start query by bookISBN and userEmail");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLRecommendHistory as p where p.bookISBN= ? and p.userEmail= ? ";
			Query q = session.createQuery(hql);
			q.setString(0, bookISBN);
			q.setString(1, userEmail);
			if (q.uniqueResult() != null)
				return true;
			else
				return false;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: SLRecommendHistoryDao ; Method: isRecommend");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean insertRecHistory(SLRecommendHistory historyBean) {
		try {
			log.debug("start insertRecHistory");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.save(historyBean);
			session.getTransaction().commit();
			log.debug("insertRecHistory successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: SLRecommendHistory ; Method: insertRecHistory");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

}
