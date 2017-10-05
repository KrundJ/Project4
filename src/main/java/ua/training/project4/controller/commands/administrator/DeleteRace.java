package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;
import static ua.training.project4.view.Constants.*;

public class DeleteRace extends Command {

	AdministratorService service = AdministratorService.getInstance();
	
	public DeleteRace(String successPage) {
		super(successPage);
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {		
		return result.checkRaceID(req);
	}

	@Override
	protected void peformAction(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> validValues) {
		service.deletePlannedRace((int) validValues.get(RACE_ID));
		//Set status code for redirect
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
