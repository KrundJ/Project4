package ua.training.project4.controller.commands;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.AuthFilter;

public class ShowLoginForm extends Command {

	public ShowLoginForm(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		//Redirect to main page if logged in user requests login form
		if(Objects.nonNull(req.getSession().getAttribute(AuthFilter.ROLE_ATTR))) {
			result.makeInvalid();
		}
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		//DO NOTHING
	}
}
