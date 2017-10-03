package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Bet.BetType;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.UserService;
import static ua.training.project4.view.Constants.*;

public class MakeBet extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	UserService userService = UserService.getInstance();
	
	public MakeBet(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
	
		result.checkRaceID(req).checkHorseName(req)
			.checkBetAmount(req).checkBetType(req);
		if (result.hasErrors()) {
			ShowBetEditor.setRequestAttributes(req, administratorService
					.getStartedRace((int) result.getValidValues().get(RACE_ID)));
		}
		return result;
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {

		Bet bet = userService.makeBet(
				Bet.builder()
				.raceID((int) validValues.get(RACE_ID))
				.amount((int) validValues.get(BET_AMOUNT))
				.betType((BetType) validValues.get(BET_TYPE))
				.horseName((String) validValues.get(HORSE_NAME)).build()
		);
		req.setAttribute("bet", bet);
		//Forward to message page
	}
}
