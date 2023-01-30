package com.ncs.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ncs.utils.ServletUtility;


/**
 * @author Kartik Vijayvargiya
 *
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("FrontController doFilter Started");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			request.setAttribute("message", "Your session has been expired. Please re-Login.");
			System.out.println("Invalid Session, doFilter if() condition");
			// Set the URI
			String str = request.getRequestURI();
			System.out.println(str + ": URI From session in FC  ");
			session.setAttribute("URI", str);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
		} else {
			System.out.println("chain.doFilter(request, response) of frontController");
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig conf) throws ServletException {
	}

}