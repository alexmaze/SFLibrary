package com.successfactors.library.server.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			sessionFactory = configuration.configure(configFile)
					.buildSessionFactory();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public HibernateSessionFactory() {
	}

	public static Session getSession() throws HibernateException {

		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {

			if (sessionFactory == null) {

				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	public static void rebuildSessionFactory() {

		try {

			sessionFactory = configuration.configure(configFile)
					.buildSessionFactory();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void closeSession() throws HibernateException {

		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {

			if (!session.isOpen()) {
				System.out.println("该 session 已经关闭");
			}
			session.close();
		}
	}

	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

}