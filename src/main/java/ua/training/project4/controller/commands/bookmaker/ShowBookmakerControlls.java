package ua.training.project4.controller.commands.bookmaker;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.BookmakerService;

public class ShowBookmakerControlls extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	BookmakerService bookmakerService = BookmakerService.getInstance();

	public ShowBookmakerControlls(String successPage) {
		super(successPage);
	}
	

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {
		
		req.setAttribute("races", administratorService.getAllRaces());
		req.setAttribute("coefficients", bookmakerService.getCoefficientsForAllRaces());
	}
}
