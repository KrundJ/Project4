package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Bet.BetType;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.UserService;

public class MakeBet extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	UserService userService = UserService.getInstance();
	
	public MakeBet(String successPage, ChangePageType successType, 
			String failPage, ChangePageType failType) {
		super(successPage, successType, failPage, failType);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
	
		Validation v = Validation.getInstance();
		ValidationResult vresult = v.checkRaceID(req, result);
		vresult = v.checkHorseName(req, vresult);
		vresult = v.checkBetAmount(req, vresult);
		vresult = v.checkBetType(req, vresult);
		
		if (vresult.hasErrors()) {
			req.setAttribute("race", administratorService
					.getRaceForMakingBet((int) vresult.getValidValues().get("raceID")));
		}
		
		return vresult;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {

		Bet bet = userService.makeBet(
				Bet.builder()
				.raceID((int) validValues.get("raceID"))
				.amount((int) validValues.get("amount"))
				.betType((BetType) validValues.get("betType"))
				.horseName((String) validValues.get("horseName")).build()
		);
		
		req.setAttribute("bet", bet);
	}
}
