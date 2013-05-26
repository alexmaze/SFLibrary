package com.successfactors.library.server.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.successfactors.library.server.hibernate.HibernateSessionFactory;
import com.successfactors.library.shared.model.PageDataWrapper;
import com.successfactors.library.shared.model.SLBookReview;

@SuppressWarnings("unchecked")
public class SLBookReviewDao extends BaseHibernateDao<SLBookReview, Long> {

	private static final Logger log = Logger.getLogger(SLBookReviewDao.class);

	public PageDataWrapper<ArrayList<SLBookReview>> getReviewListByBookISBN(
			String bookISBN, int numberPerPage, int pageNumber) {

		PageDataWrapper<ArrayList<SLBookReview>> retDataWrapper = new PageDataWrapper<ArrayList<SLBookReview>>();

		ArrayList<SLBookReview> ret = new ArrayList<SLBookReview>();

		log.debug("Start getListByBookISBN");
		Session session = HibernateSessionFactory.getSession();

		Criteria criteria = session.createCriteria(SLBookReview.class);
		criteria.add(Restrictions.eq("bookISBN", bookISBN));

		// 取总页数
		criteria.setProjection(Projections.rowCount());
		int totalItemNum = ((Long) criteria.uniqueResult()).intValue();
		int totalPageNum = getTotalPageNumber(totalItemNum, numberPerPage);

		// 真正取值
		criteria.setProjection(null);
		if (numberPerPage > 0 && pageNumber > 0) {
			criteria.setMaxResults(numberPerPage);// 最大显示记录数
			criteria.setFirstResult((pageNumber - 1) * numberPerPage);// 从第几条开始
		}
		criteria.addOrder(Order.asc("reviewId"));

		ret = (ArrayList<SLBookReview>) criteria.list();

		retDataWrapper.setData(ret);
		retDataWrapper.setNumberPerPage(numberPerPage);
		retDataWrapper.setPageNumber(pageNumber);
		retDataWrapper.setTotalItemNumber(totalItemNum);
		retDataWrapper.setTotalPageNumber(totalPageNum);

		return retDataWrapper;
	}

	public PageDataWrapper<ArrayList<SLBookReview>> getReviewListByUserEmail(String userEmail, int numberPerPage, int pageNumber) {

		PageDataWrapper<ArrayList<SLBookReview>> retDataWrapper = new PageDataWrapper<ArrayList<SLBookReview>>();

		ArrayList<SLBookReview> ret = new ArrayList<SLBookReview>();

		log.debug("Start getReviewListByUserEmail");
		Session session = HibernateSessionFactory.getSession();

		Criteria criteria = session.createCriteria(SLBookReview.class);
		criteria.add(Restrictions.eq("userEmail", userEmail));

		// 取总页数
		criteria.setProjection(Projections.rowCount());
		int totalItemNum = ((Long) criteria.uniqueResult()).intValue();
		int totalPageNum = getTotalPageNumber(totalItemNum, numberPerPage);

		// 真正取值
		criteria.setProjection(null);
		if (numberPerPage > 0 && pageNumber > 0) {
			criteria.setMaxResults(numberPerPage);// 最大显示记录数
			criteria.setFirstResult((pageNumber - 1) * numberPerPage);// 从第几条开始
		}
		criteria.addOrder(Order.asc("reviewId"));

		ret = (ArrayList<SLBookReview>) criteria.list();

		retDataWrapper.setData(ret);
		retDataWrapper.setNumberPerPage(numberPerPage);
		retDataWrapper.setPageNumber(pageNumber);
		retDataWrapper.setTotalItemNumber(totalItemNum);
		retDataWrapper.setTotalPageNumber(totalPageNum);

		return retDataWrapper;
	}

	public static int getTotalPageNumber(int totalItemNum, int numberPerPage) {
		if (totalItemNum % numberPerPage == 0) {
			return (int) totalItemNum / numberPerPage;
		} else {
			return (int) totalItemNum / numberPerPage + 1;
		}
	}
}
