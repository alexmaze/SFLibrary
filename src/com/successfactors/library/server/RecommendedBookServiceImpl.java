package com.successfactors.library.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.RecommendedBookService;
import com.successfactors.library.server.dao.SLBookDao;
import com.successfactors.library.server.dao.SLRecommendedBookDao;
import com.successfactors.library.shared.model.RecommendedBookPage;
import com.successfactors.library.shared.model.SLRecommendedBook;

@SuppressWarnings("serial")
public class RecommendedBookServiceImpl extends RemoteServiceServlet implements RecommendedBookService {

	private SLBookDao bookDao = new SLBookDao();
	private SLRecommendedBookDao dao = new SLRecommendedBookDao();
	
	@Override
	public boolean recommendBook(SLRecommendedBook recommendedBook) {
		
		// 检查是否已有或已推荐
		if (bookDao.queryByISBN(recommendedBook.getBookISBN()) != null
				|| dao.queryByISBN(recommendedBook.getBookISBN()) != null) {
			return false;
		}
		
		if (dao.insertRecBook(recommendedBook)) {
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


}
