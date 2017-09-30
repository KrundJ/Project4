package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;

public class ShowAdministratorControls extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public ShowAdministratorControls(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {
		req.setAttribute("races", administratorService.getAllRaces());
	}
}
