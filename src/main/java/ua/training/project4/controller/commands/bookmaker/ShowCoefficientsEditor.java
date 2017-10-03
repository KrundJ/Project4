package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.service.BookmakerService;
import static ua.training.project4.view.Constants.*;

public class ShowCoefficientsEditor extends Command {
	
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public ShowCoefficientsEditor(String successPage) {
		super(successPage);
	}
	
	public static void setRequestAttributes(HttpServletRequest req, 
			Coefficients coefficients, int raceID) {
		req.setAttribute(COEFFICIENTS, coefficients);
		req.setAttribute(RACE_ID, raceID);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result.checkRaceID(req);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int raceID = (int) validValues.get(RACE_ID);
		Coefficients coefficients = bookmakerService.getCoefficients(raceID);
		setRequestAttributes(req, coefficients, raceID);
	}
}
