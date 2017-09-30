package ua.training.project4.controller.commands;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends Command {


	public Login(String successPage, ChangePageType successType,
				String failPage, ChangePageType failType) {
		super(successPage, successType, failPage, failType);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {	
	}	
}
