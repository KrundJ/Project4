package ua.training.project4.controller.commands.administrator;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

public class ShowRaceResultsEditor extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	
	public ShowRaceResultsEditor(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return Validation.getInstance().checkRaceID(req, result);
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		Race race = administratorService
				.getRaceForSettingResults((int) validValues.get("raceID"));
		req.setAttribute("race", race);
	}
}