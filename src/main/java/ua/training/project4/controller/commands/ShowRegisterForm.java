package ua.training.project4.controller.commands;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegisterForm extends Command {

	public ShowRegisterForm(String successPage) {
		super(successPage);
	}

	@Override
	protected void peformAction(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> validValues) {
		// TODO Auto-generated method stub

	}

}
