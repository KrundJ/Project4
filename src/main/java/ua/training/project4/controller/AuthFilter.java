package ua.training.project4.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;
import ua.training.project4.model.service.AuthService;

class Auth {
			
	private Map<String, Role> permissions = new HashMap<>();
			
	private Auth() {}
	
	private static Auth instance = null;
	
	public static Auth getInstance() {
		if (Objects.isNull(instance)) {
			instance = new Auth();
		}
		return instance;
	}
				
	public Auth addConstraint(String pattern, Role role) {
		permissions.put(pattern, role);
		return this;
	}
	
	public ServletResponse check(ServletRequest req, ServletResponse resp) throws IOException {
		
		for (Map.Entry<String, Role> entry : permissions.entrySet()) {
			if (((HttpServletRequest) req).getRequestURI().matches(entry.getKey()) 
							&& Objects.nonNull(entry.getValue())) {
				
				Role userRole = (Role) ((HttpServletRequest) req)
						.getSession().getAttribute(AuthFilter.ROLE_ATTR);
				if (Objects.isNull(userRole)) {
					//login
					((HttpServletResponse) resp).sendRedirect("/app/login");
					return resp;
				}			
				if (userRole.equals(entry.getValue())) {
					//OK
					return resp;
				} else {
					//403
					((HttpServletResponse) resp).sendError(HttpServletResponse.SC_FORBIDDEN);
					return resp;
				}
			}
		}
		//Allow all
		return resp;
	}
}

public class AuthFilter implements Filter {
	
	private Auth auth;
	
	public static final String ROLE_ATTR = "role";
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		ServletResponse processedResp = auth.check(req, resp);
		//if request response status redirect(in case of login) or forbidden(not OK) - stop filtering
		if (((HttpServletResponse) processedResp).getStatus() != HttpServletResponse.SC_OK)
			return;
		chain.doFilter(req, processedResp);
	}
	
	public void destroy() {}
	
	public void init(FilterConfig fConfig) throws ServletException {
		 auth = Auth.getInstance()
				 .addConstraint("^/app/administrator.*", Role.ADMINISTRATOR)
				 .addConstraint("^/app/bookmaker.*", Role.BOOKMAKER)
		 		 .addConstraint("^/app/bet", Role.USER)
		 		 .addConstraint("^/app/winnings", Role.USER);
	}	
}
