package ua.training.project4.controller.commands.administrator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;
import static ua.training.project4.view.Constants.*;

public class OrganizeRace extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();
	HorseService horseService = HorseService.getInstance();
	
	public OrganizeRace(String successPage, String failPage) {
		super(successPage, failPage);
	}
	
	@Override
	public ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		result.checkRaceDistance(req).checkDate(req).checkHorseNames(req);	
		if(result.hasErrors()) {
			ShowNewRaceEditor.setRequestAttributes(req, horseService.getAllHorses());
		}		
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {	
		Map<Horse, Integer> raceResults = new HashMap<>();
		horseService.getHorsesByNames((String[]) 
				validValues.get(HORSE_NAMES)).forEach(h -> raceResults.put(h, null));
		
		Race race = Race.builder()
				.raceResults(raceResults)
				.date((Date) validValues.get(DATE))
				.distance((RaceDistance) validValues.get(DISTANCE))
				.state(RaceState.PLANNED).build();

		administratorService.organizeRace(race);
		//Set status code for redirect
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
