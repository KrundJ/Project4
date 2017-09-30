package ua.training.project4.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;

class Auth {
	
	private Map<String, Role> permissions = new HashMap<>();
	
	private String pattern;
	
	private Auth() {}
	
	private static Auth instance = null;
	
	public static Auth getInstance() {
		if (instance == null) {
			instance = new Auth();
		}
		return instance;
	}
				
	public Auth urlPattern(String pattern) {
		this.permissions.put(pattern, null); 
		this.pattern = pattern;
		return this;
	}
	
	public Auth hasRole(Role role) {
		this.permissions.put(pattern, role);
		return this;
	}
	
	public ServletResponse check(ServletRequest req, ServletResponse resp) throws IOException {
		for (Map.Entry<String, Role> entry : permissions.entrySet()) {
			if (((HttpServletRequest) req).getRequestURI().matches(entry.getKey()) 
							&& entry.getValue() != null) {
				Principal p = ((HttpServletRequest) req).getUserPrincipal();
				if (p == null) {
					//login
					System.out.println("Auth redirect");
					((HttpServletResponse) resp).sendRedirect("/app/login");
					return resp;
				}
				User user = DAOFactory.getInstance().getUserDAO()
							.getByLogin(p.getName());
				if (user.getRole().equals(entry.getValue())) {
					//OK
					System.out.println("Auth match");
					return resp;
				} else {
					//403
					System.out.println("Auth forbidden");
					((HttpServletResponse) resp).sendError(HttpServletResponse.SC_FORBIDDEN);
					return resp;
				}
			}
		}
		//OK
		System.out.println("Auth allowed");
		return resp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Total entries: " + permissions.size());
		sb.append("\n");
		for (Map.Entry<String, Role> e: permissions.entrySet()) {
			sb.append("[urlPattern=" + e.getKey() + ", requireRole=" + e.getValue() + "]");
			sb.append("\n");
		}
		return sb.toString();
	}
}

//@WebFilter(filterName="auth", urlPatterns={"/*"})
public class AuthFilter implements Filter {
	
	private Auth auth;
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("auth");
//		System.out.println("Status" + ((HttpServletResponse) response).getStatus());
		ServletResponse processedResp = auth.check(req, resp);
		//if request response status redirect(in case of login) or forbidden(not OK) - stop filtering
		if (((HttpServletResponse) processedResp).getStatus() != HttpServletResponse.SC_OK)
			return;
		chain.doFilter(req, processedResp);
	}
	
	public void destroy() {}
	
	public void init(FilterConfig fConfig) throws ServletException {
		 auth = Auth.getInstance()
				 .urlPattern("/app/administrator").hasRole(Role.ADMINISTRATOR)
				 .urlPattern("/app/bookmaker").hasRole(Role.BOOKMAKER);
	}	
}
