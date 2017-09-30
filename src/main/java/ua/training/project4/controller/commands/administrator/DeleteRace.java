package ua.training.project4.controller.commands.administrator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.service.AdministratorService;

public class DeleteRace extends Command {

	AdministratorService service = AdministratorService.getInstance();
	
	public DeleteRace(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return Validation.getInstance().checkRaceID(req, result);
	}

	@Override
	protected void peformAction(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> validValues) {
		service.deletePlannedRace((int) validValues.get("raceID"));
	}
}
