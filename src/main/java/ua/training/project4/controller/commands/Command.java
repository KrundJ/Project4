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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.training.project4.controller.Servlet;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Bet.BetType;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.service.AuthService;

import static ua.training.project4.view.Constants.*;

public abstract class Command {
	
	private static Logger log = Logger.getLogger(Command.class.getName());
	
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
				log.info("Invalid raceID");
				log.info(e);
				throw new RuntimeException(VALIDATION_ERR_RACE_ID);
			}
			return this;
		}
		
		public ValidationResult checkBetID(HttpServletRequest req) {	
			try {
				int betID = Integer.parseInt(req.getParameter(BET_ID));
				validValues.put(BET_ID, betID);
			} catch (Exception e) {
				log.info("Invalid betID");
				log.info(e);
				throw new RuntimeException(VALIDATION_ERR_BET_ID);
			}
			return this;
		}
			
		public ValidationResult checkRaceDistance(HttpServletRequest req) {
			try {
				RaceDistance distance = RaceDistance.valueOf(req.getParameter(DISTANCE));
				validValues.put(DISTANCE, distance);
			} catch (Exception e) {
				log.info("Invalid distance");
				log.info(e);
				errorMessages.put(DISTANCE, VALIDATION_ERR_DISTANCE); 
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
				log.info("Invalid date");
				log.info(e);
				errorMessages.put(DATE, VALIDATION_ERR_DATE); 
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
				log.info("Invalid race results");
				log.info(e);
				errorMessages.put(RACE_RESULTS, VALIDATION_ERR_RACE_RESULTS); 
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
				log.info("Invalid horses");
				log.info(e);
				errorMessages.put(HORSE_NAMES, VALIDATION_ERR_HORSE_NAMES); 
			}
			return this;
		}
		
		public ValidationResult checkHorseName(HttpServletRequest req) {
			try {
				String horseName = req.getParameter(HORSE_NAME);
				if (horseName.isEmpty()) throw new Exception();
				validValues.put(HORSE_NAME, horseName);
			} catch (Exception e) {
				log.info("Invalid horse name");
				log.info(e);
				errorMessages.put(HORSE_NAME, VALIDATION_ERR_HORSE_NAME); 
			}
			return this;
		}
		
		public ValidationResult checkBetAmount(HttpServletRequest req) {
			try {
				int amount = Integer.parseInt(req.getParameter(BET_AMOUNT));
				if (amount <= 0) throw new Exception();
				validValues.put(BET_AMOUNT, amount);
			} catch (Exception e) {
				log.info("Invalid amount");
				log.info(e);
				errorMessages.put(BET_AMOUNT, VALIDATION_ERR_AMOUNT); 
			}
			return this;
		}
		
		public ValidationResult checkBetType(HttpServletRequest req) {
			try {
				BetType betType = BetType.valueOf(req.getParameter(BET_TYPE));
				validValues.put(BET_TYPE, betType);
			} catch (Exception e) {
				log.info("Invalid bet type");
				log.info(e);
				errorMessages.put(BET_TYPE, VALIDATION_ERR_BET_TYPE); 
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
				log.info("Invalid coefficients");
				log.info(e);
				errorMessages.put(COEFFICIENTS, VALIDATION_ERR_COEFFICIENTS); 
			}
			return this;
		}
				
		public ValidationResult checkLogin(HttpServletRequest req) {
			try {
				String login = req.getParameter(LOGIN);
				if(! login.matches(LOGIN_PATTERN)) throw new Exception(login);
				validValues.put(LOGIN, login);
			} catch (Exception e) {
				System.err.println();
				log.info("Invalid login: " + e.getMessage());				
				errorMessages.put(LOGIN, VALIDATION_ERR_LOGIN); 
			}
			return this;
		}
		
		public ValidationResult checkPassword(HttpServletRequest req) {
			try {
				String password = req.getParameter(PASSWORD);
				if(! password.matches(PASSWORD_PATTERN)) throw new Exception(password);
				validValues.put(PASSWORD, password);
			} catch (Exception e) {
				log.info("Invalid password: " + e.getMessage());
				errorMessages.put(PASSWORD, VALIDATION_ERR_PASSWORD); 
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
