package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.BookmakerService;
import static ua.training.project4.view.Constants.*;

public class ShowBookmakerControlls extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public ShowBookmakerControlls(String successPage) {
		super(successPage);
	}
	

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {	
		req.setAttribute(RACES, administratorService.getPlannedRaces());
		req.setAttribute(COEFFICIENTS, 
				bookmakerService.getCoefficientsForPlannedRaces());
	}
}
