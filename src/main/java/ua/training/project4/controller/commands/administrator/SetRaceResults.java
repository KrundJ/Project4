package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;
import static ua.training.project4.view.Constants.*;

public class SetRaceResults extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public SetRaceResults(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		result.checkRaceID(req).checkRaceResults(req);
		if (result.hasErrors()) {
			Race race = administratorService.getStartedRace((int) result.getValidValues().get(RACE_ID));
			ShowRaceResultsEditor.setRequestAttributes(req, race);
		}
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		administratorService.saveRaceResults(
				(int) validValues.get(RACE_ID), 
				(Map<Integer, String>) validValues.get(RACE_RESULTS)); 
		//Set status code for redirect
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}

}
