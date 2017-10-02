package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.service.AdministratorService;

public class FinishRace extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	
	public FinishRace(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		Validation v = Validation.getInstance();
		return v.checkRaceID(req, result);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		administratorService.finishRace((int) validValues.get("raceID"));
	}

}
