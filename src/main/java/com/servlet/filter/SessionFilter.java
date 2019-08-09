package com.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;

public class SessionFilter implements Filter {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionFilter.class);

	@Override
	public void destroy() {
		// ...
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		String url = ((HttpServletRequest) request).getServletPath();
		System.out.println("URL:" + url);
		if (url.equals("/error")) {
			// action to record attemp of the user login
			System.out.println("error page");
		}
		if (url != null && url.length() > 19 && url.substring(0, 19).equals("/resources/adminltd")) {
			// action to record attemp of the user login
			System.out.println("Getting resource :" + url);
		}

		// forward the site to home page if Session already exist
		else if (url.equals("/login") && session.getAttribute("UserList") != null) {
			System.out.println("Session already exist");
			request.getRequestDispatcher("/").forward(request, response);
			return;

		}

		else if (url.equals("/postLogIn") || url.equals("/login")) {
			// action to record attemp of the user login
			System.out.println("login page");
		}

		else if (session == null || session.getAttribute("UserList") == null) {
			ServletContext servletContext = request.getServletContext();
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/login");
			dispatcher.forward(request, response);
			return;
		}

		try {
			chain.doFilter(request, response);

		} catch (Exception ex) {

			logger.info(ex.getMessage());
//			ServletContext servletContext = request.getServletContext();
//			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/login");
//			dispatcher.forward(request, response);
			request.setAttribute("errorMessage", ex);
			request.getRequestDispatcher("/error").forward(request, response);
		}

	}

}
