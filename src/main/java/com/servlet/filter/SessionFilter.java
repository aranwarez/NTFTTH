package com.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	private ArrayList<String> urlList;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

//		String completeURL = requestURL.toString();
//		

//		URL url = new URL(completeURL);
//		String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
//		String base = url.getProtocol() + "://" + url.getHost() + path;
//		
//	    System.out.println("jSessionId:"+completeURL);
//	    
//	    System.out.println("path:"+path);
//	    
//	   
//	    System.out.println("base:"+base);
//	   
		// --------
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//		String url = request.getServletPath();
//		boolean allowedRequest = false;
//		if (urlList.contains(url)) {
//			allowedRequest = true;
//		}
//		HttpSession session = request.getSession();
//		System.out.println("session invaliadtion"+session.getAttribute("Userlist"));
		
		
//		if (session.getAttribute("Userlist") == null) {
//			System.out.println("session null");
//			request.getRequestDispatcher("/login").forward(request, response);
////			return;
//
//		}

//		try {
//			chain.doFilter(request, response);
//		} catch (Exception ex) {
//			request.setAttribute("errorMessage", ex);
//			request.getRequestDispatcher("/VAST/login").forward(request, response);
//		}

	}

	public void init(FilterConfig config) throws ServletException {
//		String urls = config.getInitParameter("avoid-urls");
//		StringTokenizer token = new StringTokenizer(urls, ",");
//
//		urlList = new ArrayList<String>();
//
//		while (token.hasMoreTokens()) {
//			urlList.add(token.nextToken());
//
//		}
	}

}
