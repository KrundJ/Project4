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
		return result.checkBetID(req);
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		int betID = (int) validValues.get(BET_ID);
		req.setAttribute(MESSAGE, userService.calculateWinnings(betID));
		//Forward to message page
	}
}
