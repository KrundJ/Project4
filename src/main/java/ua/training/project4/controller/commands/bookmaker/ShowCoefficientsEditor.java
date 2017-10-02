package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.service.BookmakerService;

public class ShowCoefficientsEditor extends Command {
	
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public ShowCoefficientsEditor(String successPage) {
		super(successPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		Validation v = Validation.getInstance();
		v.checkRaceID(req, result);
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int raceID = (int) validValues.get("raceID");
		req.setAttribute("coefficients", bookmakerService.getCoefficients(raceID));
		req.setAttribute("raceID", raceID);
	}
}
