package ua.training.project4.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static ua.training.project4.view.Constants.*;

public class LocaleFilter implements Filter {
	
	private static final Locale DEFAULT_LOCALE = Locale.US;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		if (Objects.isNull(
				((HttpServletRequest) req).getSession().getAttribute(LOCALE))) {
			
			((HttpServletRequest) req).getSession().setAttribute(LOCALE, DEFAULT_LOCALE);	
		}
		System.out.println("Locale");
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {}

}
