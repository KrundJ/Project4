package ua.training.project4.controller.commands;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.AuthFilter;
import ua.training.project4.view.Constants;

public class Logout extends Command {

	public Logout(String successPage, String failPage) {
		super(successPage, failPage);
	}

//	@Override
//	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
//		//Check if user logged in
//		if(Objects.isNull(req.getSession().getAttribute(AuthFilter.ROLE_ATTR))) {
//			result.makeInvalid();
//		}
//		return result;
//	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		req.getSession().setAttribute(AuthFilter.ROLE_ATTR, null);
		req.setAttribute(Constants.MESSAGE, "You have been logged out successfully");		
	}
}
