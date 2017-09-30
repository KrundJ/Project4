package ua.training.project4.controller.commands.administrator;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.controller.commands.Validation;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.service.AdministratorService;

public class SetRaceResults extends Command {
	
	AdministratorService administratorService = AdministratorService.getInstance();

	public SetRaceResults(String successPage, ChangePageType successType, 
			String failPage, ChangePageType failType) {
		super(successPage, successType, failPage, failType);
	}
	
	@Override
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		
		//ADD TO COMMAND VALIDATION FIELD INVALID INPUTED VALUES
		
		Map<Integer, String> raceResultsMap = new HashMap<>();
		try {
			for (Integer i = 1; i <= Race.NUMBER_OF_HORSES_IN_RACE; i++) {
				String horseName = (String) req.getParameter(i.toString());
				raceResultsMap.put(i, horseName);
			}
			if (new HashSet(raceResultsMap.values()).size() != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
		} catch (Exception e) {
			System.err.println("Invalid race results");
			result.getErrorMessages().put("msg", "Invalid race results");
		}
		
		Validation v = Validation.getInstance();
		ValidationResult vr = v.checkRaceID(req, result);
		
		
		if (result.hasErrors()) {
			Race race = administratorService.getRaceForSettingResults((int) vr.getValidValues().get("raceID"));
			req.setAttribute("race", race);
			req.setAttribute("selection", raceResultsMap);
		}
			
		return result;
	}

	@Override
	protected void peformAction(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> validValues) {
		
	}

}
