package ua.training.project4.controller.commands;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import ua.training.project4.model.entities.Horse;

public abstract class Command {
	
	protected class ValidationResult {
		
		private Map<String, String> errors;
		private Map<String, Object> values;

		private ValidationResult() {
			this.errors = new HashMap<>();
			this.values = new HashMap<>();
		}
		
		public boolean hasErrors() {
			return (! errors.isEmpty());
		}
		
		public Map<String, String> getErrorMessages() {
			return errors;
		}
		
		public Map<String, Object> getValidValues() {
			return values;
		}
	}
	
	private String successPage;
	private ChangePageType successType;
	private String failPage;
	private ChangePageType failType;
	
	//REMOVE AND SET REDIRECT IN COMMAND, IF NOT SET FORWARD
	public enum ChangePageType { FORWARD, REDIRECT };	

	public Command(String successPage, ChangePageType successType) {
		this(successPage, successType, null, null);
	}
	
	public Command(String successPage, ChangePageType successType, 
				String failPage, ChangePageType failType) {
		this.successPage = successPage;
		this.successType = successType;
		this.failPage = failPage;
		this.failType = failType;
	}

	protected String getSuccessPage() {
		return successPage;
	}

	protected String getFailPage() {
		return failPage;
	}
	
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result;
	}

	private Locale getLocaleOrUS(String newLang) {
		Locale loc;
			switch (newLang) {
				case "en": {
					loc = Locale.US;
					break;
				}
				case "ru": {
					loc = Locale.forLanguageTag("ru-RU");
					break;
				}
				default: {
					loc = Locale.US;
				}
			}
		return loc;
	}
	
	private void manageLocale(HttpServletRequest req) {
		//if locale changed
		Locale newLocale = null;
		if (req.getParameter("new_lang") != null) {
			newLocale = getLocaleOrUS(req.getParameter("new_lang"));
			req.getSession().setAttribute("locale", newLocale);
		}
		Locale loc = (Locale) req.getSession().getAttribute("locale");
		if (loc == null) 
			loc = Locale.US;
		//put locale specific values
		req.setAttribute("locale", loc);
		req.setAttribute("dateFormat", 
					((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, loc))
							.toLocalizedPattern());
	}
	
	protected void setAttibutesForBothFailAndSuccess(HttpServletRequest req, 
			ValidationResult result) {
		//TODO
	}
	
	protected abstract void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues);

	public final void execute(HttpServletRequest req, HttpServletResponse resp) 
				throws IOException, ServletException {
		
		manageLocale(req);

		ValidationResult result = validateInput(req, new ValidationResult());
		
		if (result.hasErrors()) {
			req.setAttribute("errors", result.errors);
			if (ChangePageType.FORWARD.equals(this.failType)) {
				req.getRequestDispatcher(getFailPage()).forward(req, resp);
			} else {
				resp.sendRedirect(getFailPage());
			}
		} else {
			peformAction(req, resp, result.values);
			if (ChangePageType.FORWARD.equals(this.successType)) {
				req.getRequestDispatcher(getSuccessPage()).forward(req, resp);
			} else {
				resp.sendRedirect(getSuccessPage());
			}
		}
	}
}
