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
	private String failPage;

	public Command(String successPage) {
		this(successPage, null);
	}
		
	public Command(String successPage,
			String failPage) {
	this.successPage = successPage;
	
	this.failPage = failPage;

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
		
	protected abstract void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues);

	public final void execute(HttpServletRequest req, HttpServletResponse resp) 
				throws IOException, ServletException {

		ValidationResult result = validateInput(req, new ValidationResult());		
		if (result.hasErrors()) {
			req.setAttribute("errors", result.errors);
			req.getRequestDispatcher(getFailPage()).forward(req, resp);
		} else {
			peformAction(req, resp, result.values);
			//check forward or redirect to success page
			if (resp.getStatus() != HttpServletResponse.SC_OK) {
				resp.sendRedirect(getSuccessPage());
			} else {
				req.getRequestDispatcher(getSuccessPage()).forward(req, resp);
			}
		}
	}
}
