package ua.training.project4.controller.commands.administrator;

import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Command.ChangePageType;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

public class ShowNewRaceEditor extends Command {
	
	HorseService horseService = HorseService.getInstance();

	public ShowNewRaceEditor(String successPage, ChangePageType successType) {
		super(successPage, successType);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {

		Set<Horse> horses = horseService.getAllHorses();
		req.setAttribute("horses", horses);
		req.setAttribute("commandURL", "/app/administrator/new");
		req.setAttribute("titleKey", "admin.newrace.title");
	}
}
