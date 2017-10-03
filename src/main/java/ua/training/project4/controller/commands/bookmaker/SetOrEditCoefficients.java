package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.service.BookmakerService;
import static ua.training.project4.view.Constants.*;

public class SetOrEditCoefficients extends Command {
	
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public SetOrEditCoefficients(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
			
		result.checkRaceID(req).checkCoefficients(req);
		if (result.hasErrors()) {
			int raceID = (int) result.getValidValues().get(RACE_ID);
			Coefficients coefficients = bookmakerService.getCoefficients(raceID);
			ShowCoefficientsEditor.setRequestAttributes(req, coefficients, raceID);
		}
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int raceID = (int) validValues.get(RACE_ID);
		Map<String, Double> coefficients = (Map<String, Double>) validValues.get(COEFFICIENTS);
		bookmakerService.setCoefficiets(raceID, coefficients);
		//Set status code for redirect
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
