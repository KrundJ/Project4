package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;
import static ua.training.project4.view.Constants.*;

public class StartRace extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public StartRace(String successPage) {
		super(successPage);
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result.checkRaceID(req);
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		administratorService.startRace((int) validValues.get(RACE_ID));
		//Set status code for redirect
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
