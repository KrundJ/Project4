package ua.training.project4.controller.commands;

import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua.training.project4.controller.commands.Command.ValidationResult;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;

public class Validation {
	
	private static Validation instance = null;
	
	private Validation(){}
	
	public static Validation getInstance() {
		if (instance == null) {
			instance = new Validation();
		}
		return instance;
	}
	
	public ValidationResult checkRaceID(HttpServletRequest req, ValidationResult result) {
		int raceID;
		try {
			raceID = Integer.parseInt(req.getParameter("raceID"));
			result.getValidValues().put("raceID", raceID);
		} catch (Exception e) {
			/*
			 * Throw exception to error page
			 * Do not catch exception, throw - invalid raceID is critical error  
			 */ 
			throw new RuntimeException("Invalid race ID " + req.getParameter("raceID"));
		}
		return result;
	}
	
	public ValidationResult checkRaceDistance(HttpServletRequest req, ValidationResult result) {
		try {
			RaceDistance distance = RaceDistance.valueOf(req.getParameter("distance"));
			result.getValidValues().put("distance", distance);
		} catch (Exception e) {
			System.err.println("Invalid distance");
			result.getErrorMessages().put("distance", "Invalid value: " + req.getParameter("distance")); 
		}
		return result;
	}
	
	public ValidationResult checkDate(HttpServletRequest req, ValidationResult result) {
		try {
			//Set by Command
			Locale loc = (Locale) req.getAttribute("locale");
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);
			Date date = df.parse(req.getParameter("date")); 
			result.getValidValues().put("date", date);
		} catch (Exception e) {
			System.err.println("Invalid date");
			result.getErrorMessages().put("date", "Invalid value: " + req.getParameter("date")); 
		}
		return result;
	}
	
	public ValidationResult checkHorseNames(HttpServletRequest req, ValidationResult result) {
		try {
			String[] horseNames = req.getParameterValues("horse");
			if (horseNames.length != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
			result.getValidValues().put("horseNames", horseNames);
		} catch (Exception e) {
			System.err.println("Invalid horses");
			result.getErrorMessages().put("horseNames", "Invalid horse set"); 
		}
		return result;
	}

	public ValidationResult checkCoefficients(HttpServletRequest req, ValidationResult result) {
		Map<String, Double> coefficients = new HashMap<>();
		try {
			Enumeration<String> horseNames = req.getParameterNames();
			while (horseNames.hasMoreElements()) {
				String name = (String) horseNames.nextElement();
				if (! name.equals("raceID")) {
					Double value = Double.parseDouble(req.getParameter(name));
					if (value <= 0) throw new Exception();
					coefficients.put(name, value);
				}
			}
			if (coefficients.size() != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
			result.getValidValues().put("coefficients", coefficients);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Invalid coefficients");
			result.getErrorMessages().put("coefficients", "Invalid coefficients"); 
		}
		return result;
	}

}
