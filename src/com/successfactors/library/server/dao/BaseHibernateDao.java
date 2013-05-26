package com.successfactors.library.server.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Alex
 *
 * @param <T> object class type
 * @param <PK> object's primary key class type
 */
public abstract class BaseHibernateDao<T extends Serializable, PK extends Serializable> {

	private static final Logger log = Logger.getLogger(BaseHibernateDao.class);
	
	private Class<T> entityClass;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseHibernateDao() {
		log.debug("BaseHibernateDao is created!");
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}
	
	public boolean save(T object) {
		log.debug("Start save " + entityClass);
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			flag = true;
			log.debug("Save " + entityClass + " successful");
		} catch (RuntimeException e) {
			flag = false;
			log.error("Save " + entityClass + " failed!", e);
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException e2) {
					log.error("Transaction rollback failed!", e2);
				}
			}
		}
		return flag;
	}
	
//	public T saveAndReturn(T object) {
//		log.debug("Start save " + entityClass);
//		Session session = HibernateSessionFactory.getSession();
//		Transaction transaction = null;
//		boolean flag = false;
//		try {
//			transaction = session.beginTransaction();
//			session.save(object);
//			transaction.commit();
//			flag = true;
//			log.debug("Save " + entityClass + " successful");
//		} catch (RuntimeException e) {
//			flag = false;
//			log.error("Save " + entityClass + " failed!", e);
//			if (transaction != null) {
//				try {
//					transaction.rollback();
//				} catch (HibernateException e2) {
//					log.error("Transaction rollback failed!", e2);
//				}
//			}
//		}
//		if (flag) {
//			return object;
//		} else {
//			return null;
//		}
//		
//	}
	
	public boolean remove(T object) {
		log.debug("Start remove " + entityClass);
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			session.delete(object);
			transaction.commit();
			flag = true;
			log.debug("Remove " + entityClass + " successful");
		} catch (RuntimeException e) {
			flag = false;
			log.error("Remove " + entityClass + " failed!", e);
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException e2) {
					log.error("Transaction rollback failed!", e2);
				}
			}
		}
		return flag;
	}
	
	public boolean update(T object) {
		log.debug("Start update " + entityClass);
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = null;
		boolean flag = false;
		try {
			transaction = session.beginTransaction();
			session.clear();
//			session.merge(object);
//			session.refresh(object);
			session.update(object);
			transaction.commit();
			flag = true;
			log.debug("update " + entityClass + " successful");
		} catch (RuntimeException e) {
			flag = false;
			log.error("update " + entityClass + " failed!", e);
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (HibernateException e2) {
					log.error("Transaction rollback failed!", e2);
				}
			}
		}
		return flag;
	}
	
//	public T updateAndReturn(T object) {
//		log.debug("Start update " + entityClass);
//		Session session = HibernateSessionFactory.getSession();
//		Transaction transaction = null;
//		boolean flag = false;
//		try {
//			transaction = session.beginTransaction();
//			//session.clear();
//			//session.merge(object);
//			//session.refresh(object);
//			session.update(object);
//			transaction.commit();
//			flag = true;
//			log.debug("update " + entityClass + " successful");
//		} catch (RuntimeException e) {
//			flag = false;
//			log.error("update " + entityClass + " failed!", e);
//			if (transaction != null) {
//				try {
//					transaction.rollback();
//				} catch (HibernateException e2) {
//					log.error("Transaction rollback failed!", e2);
//				}
//			}
//		}
//		if (flag) {
//			return object;
//		} else {
//			return null;
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public T getById(PK id) {
		log.debug("Start get instance by id");
		Session session = HibernateSessionFactory.getSession();
		return (T) session.get(entityClass, id);
	}
	
}
