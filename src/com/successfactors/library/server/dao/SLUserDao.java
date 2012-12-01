/**
 * SLUserDao 用于操作SLUser对应数据库
 */
package com.successfactors.library.server.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.model.SLUser;

/**
 * @author alex
 */
public class SLUserDao {

	private static final Logger log = Logger.getLogger(SLUserDao.class);
	private Session session;
	private static SLUserDao singleton = null;

	/**
	 * SLUserDao 默认构造函数
	 * */
	private SLUserDao() {
		log.info("SLUserDao construct is running");
		session = HibernateSessionFactory.getSession();
		if (session == null) {
			log.debug("session is null");
		}
	}

	/**
	 * 保存一条SLUser记录
	 * */
	public boolean save(SLUser slUser) {
		log.info("SLUserDao save is running");
		session = HibernateSessionFactory.getSession();
		boolean flag = false;
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.save(slUser);
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
			// session.close();
			// 这里就不需要在关闭session直接交给filter来处理
		}
		return flag;
	}

	/**
	 * 删除一条SLUser记录
	 * */
	public boolean remove(SLUser slUser) {
		log.info("SLUserDao remove is running");
		session = HibernateSessionFactory.getSession();
		boolean flag = false;
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.delete(slUser);
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
			// session.close();
			// 这里就不需要在关闭session直接交给filter来处理
		}
		return flag;
	}

	/**
	 * 更新一条SLUser记录
	 * */
	public boolean update(SLUser slUser) {
		log.debug("SLUserDao update, email: " + slUser.getUserEmail());
		session = HibernateSessionFactory.getSession();
		boolean flag = false;
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.clear();
			session.merge(slUser);
			session.refresh(slUser);
			tran.commit();
			flag = true;
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update instance failed", re);
			flag = false;
			try {
				if (tran != null)
					tran.rollback();
			} catch (HibernateException he) {
				log.error("update Transaction rollback is error"
						+ he.getMessage());
			}
		} finally {
			// session.close();
			// 这里就不需要在关闭session直接交给filter来处理
		}
		return flag;
	}

	/**
	 * 通过Email获取个人信息
	 * */
	public SLUser getSLUserByEmail(String strEmail) {
		log.debug("SLUserDao getSLUserByEmail, email: " + strEmail);
		session = HibernateSessionFactory.getSession();
		SLUser slUser = new SLUser();
		slUser = (SLUser) session.get(SLUser.class, strEmail);
		return slUser;
	}
	public static SLUserDao getDao() {
		if (singleton == null) {
			singleton = new SLUserDao();
		}
		return singleton;
	}
}
