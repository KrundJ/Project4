package ua.training.project4.controller.commands.administrator;

import java.util.Date;

import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

public class OrganizeRace extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	HorseService horseService = HorseService.getInstance();
	
	public OrganizeRace(String successPage, ChangePageType successType, 
				String failPage, ChangePageType failType) {
		super(successPage, successType, failPage, failType);
	}

	@Override
	public ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
			
		ValidationResult vresult;
		Validation v = Validation.getInstance();
		vresult = v.checkRaceDistance(req, result);
		vresult = v.checkDate(req, vresult);
		vresult = v.checkHorseNames(req, vresult);
		
		req.setAttribute("horses", horseService.getAllHorses());
		req.setAttribute("commandURL", "/app/administrator/new");
		req.setAttribute("titleKey", "admin.newrace.title");
		
		return vresult;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		Set<Horse> horses = horseService.getHorsesByNames((String[]) validValues.get("horseNames"));
		Race race = new Race(horses);
		race.setDate((Date) validValues.get("date"));
		race.setDistance((RaceDistance) validValues.get("distance"));
		administratorService.organizeRace(race);
	}
}
