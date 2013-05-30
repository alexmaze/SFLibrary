/*
 * $Id$
 */
package com.successfactors.library.server.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.BookSearchType;
import com.successfactors.library.shared.model.SLBook;

@SuppressWarnings("rawtypes")
public class SLBookDao {
	private static final Logger log = Logger.getLogger(SLBookDao.class);

	private Session session = null;
	
	private static SLBookDao singleton = null;

	private SLBookDao() {
		log.debug("MysqlBookDao construct is running");
	}

	/**
	 * return the list of books.
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return the empty list if there are no books
	 */
	public List<SLBook> queryAll(int pageSize, int pageNo) {
		try {
			log.debug("Start query all books");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLBook as p order by bookAddDate desc";
			Query q = session.createQuery(hql);
			q.setFirstResult((pageNo - 1) * pageSize);
			q.setMaxResults(pageSize);

			List<SLBook> results = new ArrayList<SLBook>();
			List list = q.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SLBook bean = (SLBook) it.next();
				results.add(bean);
			}
			log.debug("query all books successfully");
			return results;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: MysqlBookDao ; Method: queryAll");
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<SLBook> getHotBooks(int num) {

		log.debug("Start getHotBooks");
		session = HibernateSessionFactory.getSession();

		Criteria criteria = session.createCriteria(SLBook.class);
		criteria.addOrder(Order.asc("borrowOrderTimes"));
		criteria.setProjection(null);
		criteria.setFirstResult(0);
		criteria.setMaxResults(num);
		
		ArrayList<SLBook> ret = (ArrayList<SLBook>) criteria.list();
		
		return ret;

	}

	public List<SLBook> getLatestBooks(int num) {
		try {
			log.debug("Start getLatestBooks");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLBook as p order by p.bookAddDate desc";
			Query q = session.createQuery(hql);
			q.setFirstResult(0);
			q.setMaxResults(num);

			List<SLBook> results = new ArrayList<SLBook>();
			List list = q.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SLBook bean = (SLBook) it.next();
				results.add(bean);
			}
			log.debug("getLatestBooks successfully");
			return results;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: MysqlBookDao ; Method: getLatestBooks");
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public List<SLBook> queryByCustomField(BookSearchType searchType,
			String searchValue, int pageSize, int pageNo) {
		try {
			session = HibernateSessionFactory.getSession();
			String hql = null;
			switch (searchType) {
			case BOOKNAME:
				hql = "from SLBook as p where p.bookName like ? order by bookAddDate desc";
				break;
			case BOOKAUTHOR:
				hql = "from SLBook as p where p.bookAuthor like ? order by bookAddDate desc";
				break;
			case BOOKPUBLISHER:
				hql = "from SLBook as p where p.bookPublisher like ? order by bookAddDate desc";
				break;
			case BOOKINTRO:
				hql = "from SLBook as p where p.bookIntro like ? order by bookAddDate desc";
				break;
			case BOOKCONTRIBUTOR:
				hql = "from SLBook as p where p.bookContributor like ? order by bookAddDate desc";
				break;
			case BOOKCLASS:
				hql = "from SLBook as p where p.bookClass like ? order by bookAddDate desc";
				break;
			case BOOKLANGUAGE:
				hql = "from SLBook as p where p.bookLanguage like ? order by bookAddDate desc";
				break;
			}
			Query q = session.createQuery(hql);
			q.setString(0, "%" + searchValue + "%");
			q.setFirstResult((pageNo - 1) * pageSize);
			q.setMaxResults(pageSize);

			List<SLBook> results = new ArrayList<SLBook>();
			List list = q.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				SLBook bean = (SLBook) it.next();
				results.add(bean);
			}
			return results;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public long getCountAll() {
		try {
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "select count(*) from SLBook as p ";
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
	

	public long getSumAll(String fieldName) {
		try {
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "select sum(" + fieldName+ ") from SLBook as p ";
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

	public long getCountByCustomField(BookSearchType searchType,
			String searchValue) {
		try {
			session = HibernateSessionFactory.getSession();
			String hql = null;
			switch (searchType) {
			case BOOKNAME:
				hql = "select count(*) from SLBook as p where p.bookName like ?";
				break;
			case BOOKAUTHOR:
				hql = "select count(*) from SLBook as p where p.bookAuthor like ?";
				break;
			case BOOKPUBLISHER:
				hql = "select count(*) from SLBook as p where p.bookPublisher like ?";
				break;
			case BOOKINTRO:
				hql = "select count(*) from SLBook as p where p.bookIntro like ?";
				break;
			case BOOKCONTRIBUTOR:
				hql = "select count(*) from SLBook as p where p.bookContributor like ?";
				break;
			case BOOKCLASS:
				hql = "select count(*) from SLBook as p where p.bookClass like ?";
				break;
			case BOOKLANGUAGE:
				hql = "select count(*) from SLBook as p where p.bookLanguage like ?";
				break;
			}
			Query q = session.createQuery(hql);
			q.setString(0, "%" + searchValue + "%");
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

	public SLBook queryByISBN(String ISBN) {
		try {
			log.debug("Start query by ISBN");
			session = HibernateSessionFactory.getSession();
			String hql = null;
			hql = "from SLBook as p where p.bookISBN= ?";
			Query q = session.createQuery(hql);
			q.setString(0, ISBN);
			if (q.uniqueResult() != null)
				return (SLBook) q.uniqueResult();
			else
				return null;
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Class: MysqlBookDao ; Method: queryByISBN");
			return null;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean insertBook(SLBook bookBean) {
		try {
			log.debug("start insert book");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.save(bookBean);
			session.getTransaction().commit();
			log.debug("insert book successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: MysqlBookDao ; Method: insertBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean updateBook(SLBook bookBean) {
		try {
			log.debug("start update book");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.update(bookBean);
			session.getTransaction().commit();
			log.debug("update book successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: MysqlBookDao ; Method: updateBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean deleteBook(SLBook bookBean) {
		try {
			log.debug("start delete book");
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.delete(bookBean);
			session.getTransaction().commit();
			log.debug("delete book successfully");
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Class: MysqlBookDao ; Method: deleteBook");
			return false;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
	
	public static SLBookDao getDao() {
		if (singleton == null) {
			singleton = new SLBookDao();
		}
		return singleton;
	}
}
