package ua.training.project4.controller.commands;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListRaces extends Command {
	

	public ListRaces(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
	}
}
