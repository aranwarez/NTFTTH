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
		if (url.equals("/postLogIn")) {
			// action to record attemp of the user login
		}

		// forward the site to home page if Session already exist
		else if (url.equals("/login") && session.getAttribute("UserList") != null) {
			System.out.println("Session already exist");
			request.getRequestDispatcher("/").forward(request, response);

		}

		else if (session == null || session.getAttribute("UserList") == null) {
			ServletContext servletContext = request.getServletContext();
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/login");
			dispatcher.forward(request, response);
		}

		try {
			chain.doFilter(request, response);

		} catch (Exception ex) {

			logger.info(ex.getMessage());
//			ServletContext servletContext = null;
//			RequestDispatcher dis1patcher = servletContext.getRequestDispatcher("/login");
//			dispatcher.forward(request, response);
			request.setAttribute("errorMessage", ex);
			request.getRequestDispatcher("/error").forward(request, response);
		}

	}

}
