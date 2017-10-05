package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;
import static ua.training.project4.view.Constants.*;

public class ShowBetEditor extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public ShowBetEditor(String successPage) {
		super(successPage);
	}
	
	public static void setRequestAttributes(HttpServletRequest req, Race race) {
		req.setAttribute(RACE, race);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result.checkRaceID(req);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {	
		int raceID = (int) validValues.get(RACE_ID);
		setRequestAttributes(req, administratorService.getStartedRace(raceID));
	}
}
