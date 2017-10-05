package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.service.UserService;
import static ua.training.project4.view.Constants.*;

public class WinningsMessage extends Command {
	
	UserService userService = UserService.getInstance();

	public WinningsMessage(String successPage) {
		super(successPage);
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result.checkRaceID(req);
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		int betID = (int) validValues.get(BET_ID);
		//Get message why winnings cannot be received
		try {
			int winnings = userService.calculateWinnings(betID);
			req.setAttribute(WINNINGS_AMOUNT, winnings);
		} catch (Exception e) {
			req.setAttribute(MESSAGE, e.getMessage());			
		}
		//Forward to message page
	}
}
