package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.service.AdministratorService;

public class ShowBetEditor extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public ShowBetEditor(String successPage) {
		super(successPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		Validation v = Validation.getInstance();
		ValidationResult vresult = v.checkRaceID(req, result);
		return vresult;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int raceID = (int) validValues.get("raceID");
		req.setAttribute("race", administratorService.getRaceForMakingBet(raceID));
	}

}
