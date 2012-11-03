package com.successfactors.library.server;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.successfactors.library.client.service.UserService;
import com.successfactors.library.server.dao.SLUserDao;
import com.successfactors.library.shared.model.SLUser;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	private SLUserDao slUserDao = new SLUserDao();
	public final static String USER_SESSION_KEY = "SF_LIB_USER";

	/**
	 * 测试服务器连通
	 * */
	@Override
	public String helloServer(String strHello) {
		return "Hello " + strHello;
	}

	/**
	 * 用户登录，成功返回用户信息，失败返回NULL
	 * */
	@Override
	public SLUser login(String strEmail, String strPassword) {
		SLUser slUser = new SLUser();
		slUser = slUserDao.getSLUserByEmail(strEmail);
		if (slUser != null) {
			if (!strPassword.equals(slUser.getUserPassword())) {
				slUser = null;
			} else {
				HttpServletRequest request = getThreadLocalRequest();
				HttpSession session = request.getSession();
				session.setAttribute(USER_SESSION_KEY, slUser);
			}
		}
		return slUser;
	}

	/**
	 * 用户注销，删除相关Session
	 * */
	@Override
	public boolean logout() {
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(USER_SESSION_KEY);
		session.invalidate();
		return true;
	}

	/**
	 * 用户注册，成功返回注册信息，失败返回NULL
	 * */
	@Override
	public SLUser register(SLUser newUser) {
		SLUser result = getUserByEmail(newUser.getUserEmail());
		if (result == null) {
			if (slUserDao.save(newUser)) {
				return newUser;
			}
		}
		return null;
	}

	/**
	 * 更新用户信息
	 * */
	@Override
	public boolean saveUserInfo(SLUser slUser) {
		SLUser oldUser = getUserByEmail(slUser.getUserEmail());
		if (oldUser != null) {
			if (slUserDao.update(slUser)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据Email获取用户信息
	 * */
	@Override
	public SLUser getUserByEmail(String userEmail) {
		SLUser ret = slUserDao.getSLUserByEmail(userEmail);
		return ret;
	}

	/**
	 * 根据Email删除用户记录
	 * */
	@Override
	public boolean deleteUserByEmail(String userEmail) {
		SLUser slUser = getUserByEmail(userEmail);
		if (slUser == null) {
			return false;
		} else if (slUserDao.remove(slUser)) {
			return true;
		}
		return false;
	}

	/**
	 * TODO 获取系统用户列表
	 * */
	@Override
	public ArrayList<SLUser> getUserList(int itemsPerPage, int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
