package ua.training.project4.controller.commands.administrator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

public class SubmitRaceChanges extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	HorseService horseService = HorseService.getInstance();

	public SubmitRaceChanges(String successPage, ChangePageType successType, 
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
		req.setAttribute("commandURL", "/app/administrator/edit");
		req.setAttribute("titleKey", "admin.editrace.title");
		
		return vresult;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		Map<Horse, Integer> raceResults = new HashMap<>();
		horseService.getHorsesByNames((String[]) 
				validValues.get("horseNames")).forEach(h -> raceResults.put(h, null));
		
		Race race = Race.builder()
				.raceResults(raceResults)
				.date((Date) validValues.get("date"))
				.distance((RaceDistance) validValues.get("distance"))
				.state(RaceState.PLANNED).build();

		administratorService.saveRaceChanges(race);
	}

}
