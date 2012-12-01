package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.RecommendedBookService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLRecommendHistoryDao;
import com.successfactors.library.server.dao.SLRecommendedBookDao;
import com.successfactors.library.shared.SLEmailUtil;
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendHistory;
import com.successfactors.library.shared.model.SLRecommendedBook;

@SuppressWarnings("serial")
public class RecommendedBookServiceImpl extends RemoteServiceServlet implements RecommendedBookService {

	private SLBookDao bookDao = SLBookDao.getDao();
	private SLRecommendedBookDao dao = SLRecommendedBookDao.getDao();
	private SLRecommendHistoryDao recommendHistoryDao = SLRecommendHistoryDao.getDao();
	
	@Override
	public boolean recommendBook(SLRecommendedBook recommendedBook) {
		
		// 检查是否已有
		if (bookDao.queryByISBN(recommendedBook.getBookISBN()) != null) {
			return false;
		}
		// 检查其是否已推荐
		if (recommendHistoryDao.isRecommend(recommendedBook.getBookISBN(), recommendedBook.getRecUserEmail())) {
			return false;
		}
		
		SLRecommendedBook allready = dao.queryByISBN(recommendedBook.getBookISBN());
		SLRecommendHistory history = SLRecommendHistory.parse(recommendedBook);
		if (allready != null) {
			allready.setRecRate(allready.getRecRate() + 1);
			dao.updateRecBook(allready);
			recommendHistoryDao.insertRecHistory(history);
			return true;
		} else if (dao.insertRecBook(recommendedBook)) {
			recommendHistoryDao.insertRecHistory(history);
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public SLRecommendedBook getRecommendedBook(String bookISBN) {
		return dao.queryByISBN(bookISBN);
	}

	@Override
	public boolean updateRecommendedBookStatus(SLRecommendedBook recommendedBook) {
		return dao.updateRecBook(recommendedBook);
	}

	@Override
	public RecommendedBookPage getAllRecBookList(int itemsPerPage, int pageNum) {
		ArrayList<SLRecommendedBook> listBooks = (ArrayList<SLRecommendedBook>) dao.queryAll(
				itemsPerPage, pageNum);
		RecommendedBookPage bookPage = new RecommendedBookPage(itemsPerPage, pageNum);
		bookPage.setTheBooks(listBooks);
		long totalNum = dao.getCountAll();
		if (totalNum % itemsPerPage == 0) {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage);
		} else {
			bookPage.setTotalPageNum((int) totalNum / itemsPerPage + 1);
		}
		return bookPage;
	}

	@Override
	public boolean buyBookList(ArrayList<String> bookISBNList) {
		
		ArrayList<SLRecommendedBook> buyList = new ArrayList<SLRecommendedBook>();
		for (String bookISBN : bookISBNList) {
			SLRecommendedBook buyBook = dao.queryByISBN(bookISBN);
			buyBook.setRecStatus("已购买");
			dao.updateRecBook(buyBook);
			buyList.add(buyBook);
		}

		// 发送邮件
		SLEmailUtil emailUtil = new SLEmailUtil();
		emailUtil.sendBuyListEmail(buyList);
		
		return true;
	}

	@Override
	public RecommendedBookPage getNewRecBookList(int num) {

		RecommendedBookPage page = new RecommendedBookPage(num, 1);
		ArrayList<SLRecommendedBook> ret = (ArrayList<SLRecommendedBook>) dao.getLatestRecBooks(num);
		page.setTheBooks(ret);
		page.setTotalPageNum(1);
		return page;
		
	}


}
