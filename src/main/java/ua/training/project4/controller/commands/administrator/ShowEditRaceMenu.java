package ua.training.project4.controller.commands.administrator;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;
import static ua.training.project4.view.Constants.*;

public class ShowEditRaceMenu extends Command {

	AdministratorService administratorService = AdministratorService.getInstance();
	HorseService horseService = HorseService.getInstance();
	
	public ShowEditRaceMenu(String successPage) {
		super(successPage);
	}
	
	public static void setRequestAttributes(HttpServletRequest req, 
			Set<Horse> horses, Race raceToEdit) {
		req.setAttribute(HORSES, horses);
		req.setAttribute(RACE, raceToEdit);
		req.setAttribute(COMMAND_URL, "/app/administrator/edit");
		req.setAttribute(TITLE_KEY, "admin.editrace.title");
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result.checkRaceID(req);
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		Race raceToEdit = administratorService
					.getPlannedRace((int) validValues.get(RACE_ID));
		Set<Horse> horses = horseService.getAllHorses();
		setRequestAttributes(req, horses, raceToEdit);
	}
}
