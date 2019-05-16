package com.iWen.survey.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

	public SecurityFilter() {
	}
	@Override
	public void destroy() {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		String reg = request.getParameter("reg");
		HttpSession session = ((HttpServletRequest) request).getSession();
		try {
			if (!reg.equals("1")) {
				if (session.getAttribute("username") != null
						&& (!"".equals(session.getAttribute("username")))) {
					chain.doFilter(request, response);
					return;
				}
				((HttpServletResponse) response).sendRedirect("../userlogin.jsp");
				return;

			}
			chain.doFilter(request, response);

		} catch (Exception e) {
			chain.doFilter(request, response);
		}

		return;
	}
	public void init(FilterConfig config) throws ServletException {

	}

}
