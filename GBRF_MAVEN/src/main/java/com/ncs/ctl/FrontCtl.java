package com.ncs.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.util.ServletUtility;

public class FrontCtl implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(true);

		if (session.getAttribute("user") == null) {
			ServletUtility
					.setErrorMessage(
							"Oops! Your session has been expired. Please Login to access application.",
							request);
			ServletUtility.forward("login.jsp", request, response);
		} else {
			chain.doFilter(req, res);
		}

	}


	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
