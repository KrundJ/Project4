package ua.training.project4.model.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.impl.BetDAOImpl;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;
import static ua.training.project4.view.Constants.*;

public class AdministratorService {

	private ServiceFactory factory = ServiceFactory.getInstance();
	
	private static Logger log = Logger.getLogger(AdministratorService.class.getName());
	
	private static AdministratorService instance;
	
	private DAOFactory daoFactory;
	
	private AdministratorService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static AdministratorService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new AdministratorService();
		}
		return instance;
	}
	
	private Race getOrThrowOnEmptyOptional(int raceID) {
		Optional<Race> raceOptional = daoFactory.getRaceDAO().getRaceByID(raceID);
		if (! raceOptional.isPresent()) {
			log.info("race with ID " + raceID + " not found");
			throw new ServiceException(RACE_NOT_FOUND, new Integer(raceID).toString());
		}
		return raceOptional.get();
	}
	
	private void throwIfNotPlanned(Race race, String message) {
		if (! race.getState().equals(RaceState.PLANNED)) {
			log.info("race with ID " + race.getID() + " is not planned");
			throw new ServiceException(message, race.getState().name());
		}		
	}
	
	private void throwIfNotStarted(Race race, String message) {
		if (! race.getState().equals(RaceState.STARTED)) {
			log.info("race with ID " + race.getID() + " is not started");
			throw new ServiceException(message, race.getState().name());
		}		
	}
	
	private void throwIfNotFinished(Race race, String message) {
		if (! race.getState().equals(RaceState.FINISHED)) {
			log.info("race with ID " + race.getID() + " is not finished");
			throw new ServiceException(message, race.getState().name());
		}		
	}
		
	public void organizeRace(Race race) {
		daoFactory.getRaceDAO().create(race);
	}
		
	public void deletePlannedRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotPlanned(race, DELETE_ERROR);		
		daoFactory.getRaceDAO().delete(raceID);
	}
		
	public void startRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotPlanned(race, START_ERROR);
		Coefficients coef = factory.getBookmakerService().getCoefficients(raceID);
		if (coef.getValues().containsValue(0.0)) {
			log.info("Coefficients not set for race with ID " + raceID);
			throw new RuntimeException(NO_COEFFICIENTS); 
		}
		race.setState(RaceState.STARTED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void finishRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotStarted(race, FINISH_ERROR);
		race.setState(RaceState.FINISHED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceChanges(Race race) {
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceResults(int raceID, Map<Integer, String> raceResults) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotStarted(race, INVALID_STATE);
		Set<Horse> horsesInResults = factory.getHorseService().getHorsesByNames(
				raceResults.values().stream().toArray(String[]::new));
		if (! race.getRaceResults().keySet().containsAll(horsesInResults)) {
			log.info("race with ID " + race.getID() + " doesn't contains horses from raceResults");
			log.info(raceResults);
			throw new RuntimeException(HORSE_NOT_IN_RACE);
		}
		
		Map<Horse, Integer> results = raceResults.entrySet().stream()
		.collect(Collectors.toMap(e -> horsesInResults.stream()
				.filter(h -> h.getName().equals(e.getValue()))
				.findFirst().get(), e -> e.getKey()));
		
		race.setRaceResults(results);
		race.setState(RaceState.FINISHED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public Race getStartedRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotStarted(race, INVALID_STATE);		
		return race;
	}
	
	public Race getPlannedRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID);
		throwIfNotPlanned(race, INVALID_STATE);		
		return race;
	}

	public Race getFinishedRace(int raceID) {	
		Race race = getOrThrowOnEmptyOptional(raceID);
		throwIfNotFinished(race, INVALID_STATE);
		return race;
	}
	
	public List<Race> getAllRaces() {	
		return daoFactory.getRaceDAO().getAllRaces();
	}
	
	public List<Race> getCurrentRaces() {	
		return daoFactory.getRaceDAO().getCurrentRaces();
	}
	
	public List<Race> getPlannedRaces() {	
		return daoFactory.getRaceDAO().getPlannedRaces();
	}
}
