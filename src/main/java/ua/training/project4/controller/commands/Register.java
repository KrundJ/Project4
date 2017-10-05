package ua.training.project4.controller.commands;

import static ua.training.project4.view.Constants.LOGIN;
import static ua.training.project4.view.Constants.PASSWORD;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.model.service.AuthService;
import ua.training.project4.view.Constants;

public class Register extends Command {
	
	private AuthService authService = AuthService.getInstance();

	public Register(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		result.checkLogin(req).checkPassword(req);
		if(result.hasErrors()) 
			return result;
		if (! authService.isLoginUnique(
				(String) result.getValidValues().get(LOGIN))) {
			result.makeInvalid();
		}
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		authService.addUser(
				(String) validValues.get(LOGIN), 
				(String) validValues.get(PASSWORD));
		req.setAttribute(Constants.MESSAGE, "Registration sucessfull");
	}
}
