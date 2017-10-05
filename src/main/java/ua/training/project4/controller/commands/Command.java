package ua.training.project4.controller.commands;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Bet.BetType;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.service.AuthService;

import static ua.training.project4.view.Constants.*;

public abstract class Command {
	
	protected class ValidationResult {
		
		private static final String LOGIN_PATTERN = "^\\w{5,10}$";
		
		private static final String PASSWORD_PATTERN = "^[\\da-zA-Z]{5,10}$";
					
		private Map<String, String> errorMessages;
		private Map<String, Object> validValues;

		private boolean isInvalid = false;
		
		public ValidationResult() {
			this.errorMessages = new HashMap<>();
			this.validValues = new HashMap<>();
		}
		
		public ValidationResult checkRaceID(HttpServletRequest req) {	
			try {
				int raceID = Integer.parseInt(req.getParameter(RACE_ID));
				validValues.put(RACE_ID, raceID);
			} catch (Exception e) {
				throw new RuntimeException("Invalid race ID " + req.getParameter(RACE_ID));
			}
			return this;
		}
			
		public ValidationResult checkRaceDistance(HttpServletRequest req) {
			try {
				RaceDistance distance = RaceDistance.valueOf(req.getParameter(DISTANCE));
				validValues.put(DISTANCE, distance);
			} catch (Exception e) {
				System.err.println("Invalid distance");
				errorMessages.put(DISTANCE, "Invalid value: " + req.getParameter(DISTANCE)); 
			}
			return this;
		}
		
		public ValidationResult checkDate(HttpServletRequest req) {
			try {
				Locale loc = (Locale) req.getSession().getAttribute(LOCALE);
				DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);
				Date date = df.parse(req.getParameter(DATE)); 
				validValues.put(DATE, date);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Invalid date");
				errorMessages.put(DATE, "Invalid value: " + req.getParameter(DATE)); 
			}
			return this;
		}
		
		public ValidationResult checkRaceResults(HttpServletRequest req) {
			Map<Integer, String> raceResultsMap = new HashMap<>();
			try {
				for (Integer i = 1; i <= Race.NUMBER_OF_HORSES_IN_RACE; i++) {
					String horseName = (String) req.getParameter(i.toString());
					raceResultsMap.put(i, horseName);
				}
				if (new HashSet<>(raceResultsMap.values()).size() != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
				validValues.put(RACE_RESULTS, raceResultsMap);
			} catch (Exception e) {
				System.err.println("Invalid race results");
				errorMessages.put(RACE_RESULTS, "Invalid race results"); 
			}
			return this;
		}
		
		public ValidationResult checkHorseNames(HttpServletRequest req) {
			try {
				String[] horseNames = req.getParameterValues(HORSE_NAMES);
				if (horseNames.length != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
				if (horseNames.length != new HashSet<>(Arrays.asList(horseNames)).size()) {
					throw new Exception();
				}
				validValues.put(HORSE_NAMES, horseNames);
			} catch (Exception e) {
				System.err.println("Invalid horses");
				errorMessages.put(HORSE_NAMES, "Invalid horse set"); 
			}
			return this;
		}
		
		public ValidationResult checkHorseName(HttpServletRequest req) {
			try {
				String horseName = req.getParameter(HORSE_NAME);
				if (horseName.isEmpty()) throw new Exception();
				validValues.put(HORSE_NAME, horseName);
			} catch (Exception e) {
				System.err.println("Invalid horse name");
				errorMessages.put(HORSE_NAME, "Invalid horse name"); 
			}
			return this;
		}
		
		public ValidationResult checkBetAmount(HttpServletRequest req) {
			try {
				int amount = Integer.parseInt(req.getParameter(BET_AMOUNT));
				if (amount <= 0) throw new Exception();
				validValues.put(BET_AMOUNT, amount);
			} catch (Exception e) {
				System.err.println("Invalid amount");
				errorMessages.put(BET_AMOUNT, "Invalid amount: " + req.getParameter(BET_AMOUNT)); 
			}
			return this;
		}
		
		public ValidationResult checkBetType(HttpServletRequest req) {
			try {
				BetType betType = BetType.valueOf(req.getParameter(BET_TYPE));
				validValues.put(BET_TYPE, betType);
			} catch (Exception e) {
				System.err.println("Invalid bet type");
				errorMessages.put(BET_TYPE, "Invalid bet type"); 
			}
			return this;
		}
		
		public ValidationResult checkCoefficients(HttpServletRequest req) {
			Map<String, Double> coefficients = new HashMap<>();
			try {
				Enumeration<String> horseNames = req.getParameterNames();
				while (horseNames.hasMoreElements()) {
					String name = (String) horseNames.nextElement();
					if (! name.equals(RACE_ID)) {
						Double value = Double.parseDouble(req.getParameter(name));
						if (value <= 0) throw new Exception();
						coefficients.put(name, value);
					}
				}
				if (coefficients.size() != Race.NUMBER_OF_HORSES_IN_RACE) throw new Exception();
				validValues.put(COEFFICIENTS, coefficients);
			} catch (Exception e) {
				System.err.println("Invalid coefficients");
				errorMessages.put(COEFFICIENTS, "Invalid coefficients"); 
			}
			return this;
		}
		
		public ValidationResult checkLogin(HttpServletRequest req) {
			try {
				String login = req.getParameter(LOGIN);
				if(! login.matches(LOGIN_PATTERN)) throw new Exception();
				validValues.put(LOGIN, login);
			} catch (Exception e) {
				System.err.println("Invalid login");
				errorMessages.put(LOGIN, "Invalid login"); 
			}
			return this;
		}
		
		public ValidationResult checkPassword(HttpServletRequest req) {
			try {
				String password = req.getParameter(PASSWORD);
				if(! password.matches(PASSWORD_PATTERN)) throw new Exception();
				validValues.put(PASSWORD, password);
			} catch (Exception e) {
				System.err.println("Invalid password");
				errorMessages.put(PASSWORD, "Invalid password"); 
			}
			return this;
		}
				
		public boolean hasErrors() {
			return (! errorMessages.isEmpty()) || isInvalid ;
		}
		//manually make result invalid, without any messages
		public void makeInvalid() {
			isInvalid = true;
		}
		
		public void makeInvalid(String messageKey, String text) {
			errorMessages.put(messageKey, text);
		}
		
		public Map<String, String> getErrorMessages() {
			return errorMessages;
		}
		
		public Map<String, Object> getValidValues() {
			return validValues;
		}
	}
	
	private String successPage;
	private String failPage;

	public Command(String successPage) {
		this(successPage, null);
	}
		
	public Command(String successPage, String failPage) {
		this.successPage = successPage;
		this.failPage = failPage;
	}

	protected String getSuccessPage() {
		return successPage;
	}

	protected String getFailPage() {
		return failPage;
	}
		
	protected ValidationResult validateInput(HttpServletRequest req, ValidationResult result) {
		return result;
	}
		
	protected abstract void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues);

	public final void execute(HttpServletRequest req, HttpServletResponse resp) 
				throws IOException, ServletException {		
		ValidationResult result = validateInput(req, new ValidationResult());		
		if (result.hasErrors()) {
			req.setAttribute("errors", result.getErrorMessages());
			req.getRequestDispatcher(getFailPage()).forward(req, resp);
		} else {
			peformAction(req, resp, result.getValidValues());
			//check forward or redirect to success page
			if (resp.getStatus() != HttpServletResponse.SC_OK) {
				resp.sendRedirect(getSuccessPage());
			} else {
				req.getRequestDispatcher(getSuccessPage()).forward(req, resp);
			}
		}
	}
}
