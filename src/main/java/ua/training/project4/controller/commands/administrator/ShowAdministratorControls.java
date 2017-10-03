package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;
import static ua.training.project4.view.Constants.*;

public class ShowAdministratorControls extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public ShowAdministratorControls(String successPage) {
		super(successPage);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {
		req.setAttribute(RACES, administratorService.getAllRaces());
	}
}
