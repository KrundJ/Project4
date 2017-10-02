package ua.training.project4.controller.commands.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.service.UserService;

public class WinningsMessage extends Command {
	
	UserService userService = UserService.getInstance();

	public WinningsMessage(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}

	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		Validation v = Validation.getInstance();
		ValidationResult vresult = v.checkBetID(req, result);
		return vresult;
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		int betID = (int) validValues.get("betID");
		try {
			int winnings = userService.calculateWinnings(betID);
			req.setAttribute("winnings", winnings);
		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());			
		}
	}
}
