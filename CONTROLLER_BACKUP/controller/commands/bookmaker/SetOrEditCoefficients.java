package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.service.BookmakerService;

public class SetOrEditCoefficients extends Command {
	
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public SetOrEditCoefficients(String successPage, ChangePageType successType,
			String failPage, ChangePageType failType) {
		super(successPage, successType, failPage, failType);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		Validation v = Validation.getInstance();
		ValidationResult vresult = v.checkCoefficients(req, result);
		vresult = v.checkRaceID(req, vresult);
		
		if (vresult.hasErrors()) {
			int raceID = (int) vresult.getValidValues().get("raceID");		
			req.setAttribute("coefficients", bookmakerService.getCoefficients(raceID));
			req.setAttribute("raceID", raceID);
		}
		return vresult;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int raceID = (int) validValues.get("raceID");
		Map<String, Double> coefficients = (Map<String, Double>) validValues.get("coefficients");
		bookmakerService.setCoefficiets(raceID, coefficients);
	}

}
