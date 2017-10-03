package ua.training.project4.model.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.RaceDAO;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;

public class AdministratorService {

	private ServiceFactory factory = ServiceFactory.getInstance();
	
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
			throw new RuntimeException();
		}
		return raceOptional.get();
	}
	
	private void throwIfNotPlanned(Race race, String message) {
		if (! race.getState().equals(RaceState.PLANNED)) {
			throw new IllegalStateException(message);
		}		
	}
	
	private void throwIfNotStarted(Race race, String message) {
		if (! race.getState().equals(RaceState.STARTED)) {
			throw new IllegalStateException(message);
		}		
	}
	
	private void throwIfNotFinished(Race race, String message) {
		if (! race.getState().equals(RaceState.FINISHED)) {
			throw new IllegalStateException(message);
		}		
	}
		
	public void organizeRace(Race race) {
		daoFactory.getRaceDAO().create(race);
	}
		
	public void deletePlannedRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
//		throwIfNotPlanned(race, "Can't delete race with state " + race.getState());		
		daoFactory.getRaceDAO().delete(raceID);
	}
		
	public void startRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotPlanned(race, "Can't start race with state " + race.getState());
		Coefficients coef = factory.getBookmakerService().getCoefficients(raceID);
		for (Entry<Horse, Double> c : coef.getValues().entrySet()) {
			System.out.println(c.getKey());
			System.out.println(c.getValue());
		}
		if (coef.getValues().containsValue(0.0)) {
			throw new RuntimeException("Can't start race, coefficients not set"); 
		}
		race.setState(RaceState.STARTED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void finishRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
		throwIfNotStarted(race, "Can't finish race with state " + race.getState());
		race.setState(RaceState.FINISHED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceChanges(Race race) {
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceResults(int raceID, Map<Integer, String> raceResults) {
		Race race = getOrThrowOnEmptyOptional(raceID); 
//		throwIfNotStarted(race, "Error race in state " + race.getState());
		Set<Horse> horsesInResults = factory.getHorseService().getHorsesByNames(
				raceResults.values().stream().toArray(String[]::new));
		if (! race.getRaceResults().keySet().containsAll(horsesInResults)) {
			throw new RuntimeException("Some horses not present in race");
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
//		throwIfNotStarted(race, "Error race in state " + race.getState());		
		return race;
	}
	
	public Race getPlannedRace(int raceID) {
		Race race = getOrThrowOnEmptyOptional(raceID);
//		throwIfNotPlanned(race, "Error race in state " + race.getState());		
		return race;
	}

	public Race getFinishedRace(int raceID) {	
		Race race = getOrThrowOnEmptyOptional(raceID);
		throwIfNotFinished(race, "Error race in state " + race.getState());
		return race;
	}
	
	public List<Race> getAllRaces() {	
		return daoFactory.getRaceDAO().getAllRaces();
	}
	
	public List<Race> getCurrentRaces() {	
		return daoFactory.getRaceDAO().getCurrentRaces();
	}
}
