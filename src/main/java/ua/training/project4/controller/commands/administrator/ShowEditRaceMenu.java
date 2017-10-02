package ua.training.project4.controller.commands.administrator;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

public class ShowEditRaceMenu extends Command {

	AdministratorService administratorService = AdministratorService.getInstance();
	HorseService horseService = HorseService.getInstance();
	
	public ShowEditRaceMenu(String successPage) {
		super(successPage);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return Validation.getInstance().checkRaceID(req, result);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		Race raceToEdit = administratorService
					.getRaceForEditing((int) validValues.get("raceID"));
		req.setAttribute("raceToEdit", raceToEdit);
		Set<Horse> horses = horseService.getAllHorses();
		req.setAttribute("horses", horses);
		req.setAttribute("commandURL", "/app/administrator/edit");
		req.setAttribute("titleKey", "admin.editrace.title");
	}
}
