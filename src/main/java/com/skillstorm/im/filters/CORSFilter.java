package com.skillstorm.im.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;
		
		res.addHeader("Access-Control-Allow-Origin", "*"); // Allows any domain to make a request
		res.addHeader("Access-Control-Allow-Methods", "*"); // Allows all HTTP methods
		res.addHeader("Access-Control-Allows-Credentials", "true");
		res.addHeader("Access-Control-Allow-Headers", "*"); // Allows all types of headers
		
		if (req.getMethod().equals("OPTIONS")) {
			res.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		chain.doFilter(req, res);
	}

}
