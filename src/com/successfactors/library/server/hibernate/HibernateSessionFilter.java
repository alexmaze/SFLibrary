package com.successfactors.library.server.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class HibernateSessionFilter implements Filter {
	private static final Logger log = Logger
			.getLogger(HibernateSessionFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("HibernateSessionFilter is inited");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		log.debug("HibernateSessionFilter start");

		try {
			// request 之前要处理的代码
			chain.doFilter(arg0, arg1);
			// response之后要要处理的代码

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public void destroy() {
		log.debug("HibernateSessionFilter is destroyed");
	}

}