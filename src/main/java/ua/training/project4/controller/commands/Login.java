package ua.training.project4.controller.commands;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.AuthFilter;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.service.AuthService;
import ua.training.project4.view.Constants;

import static ua.training.project4.view.Constants.*;

public class Login extends Command {
	
	private AuthService authService = AuthService.getInstance();

	public Login(String successPage, String failPage) {
		super(successPage, failPage); 
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		result.checkLogin(req).checkPassword(req);
		if(result.hasErrors()) 
			return result;
		if (! authService.checkUserCredentials(
				(String) result.getValidValues().get(LOGIN), 
				(String) result.getValidValues().get(PASSWORD))) {
			result.makeInvalid(LOGIN_MAIN_ERROR, null);
		}
		return result;
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {	
		User user = authService.getUserByLogin(
				(String) validValues.get(Constants.LOGIN)).get();
		req.getSession().setAttribute(AuthFilter.ROLE_ATTR, user.getRole());
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
