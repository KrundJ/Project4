package ua.training.project4.model.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.RaceDAO;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;

public class AdministratorService {
	
	private static AdministratorService instance;
	
	private DAOFactory daoFactory;
	
	private AdministratorService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static AdministratorService getInstance() {
		if (instance == null) {
			instance = new AdministratorService();
		}
		return instance;
	}
		
	public void organizeRace(Race race) {
		daoFactory.getRaceDAO().create(race);
	}
		
	public void deletePlannedRace(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
		if (race.getState() != RaceState.PLANNED) {
			throw new IllegalStateException("Can't delete race with state " + race.getState());
		}		
		daoFactory.getRaceDAO().delete(raceID);
	}
		
	public void startRace(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
		if (race.getState() != RaceState.PLANNED) {
			throw new IllegalStateException("Can't start race with state " + race.getState());
		}
		race.setState(RaceState.STARTED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void finishRace(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
		if (race.getState() != RaceState.STARTED) {
			throw new IllegalStateException("Can't finish race with state " + race.getState());
		}
		race.setState(RaceState.FINISHED);
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceChanges(Race race) {
		daoFactory.getRaceDAO().update(race);
	}
	
	public void saveRaceResults(int raceID, Map<String, Integer> raceResults) {

	}
	
	public Race getRaceForMakingBet(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
//		if (race.getState() != RaceState.STARTED) {
//			throw new IllegalStateException("Can't make bet on race with state " + race.getState());
//		}		
		return race;
	}
	
	public Race getRaceForEditing(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
//		if (race.getState() != RaceState.PLANNED) {
//			throw new IllegalStateException("Can't edit race with state " + race.getState());
//		}		
		return race;
	}
	
	public Race getRaceForSettingResults(int raceID) {
		Race race = daoFactory.getRaceDAO().getRaceByID(raceID);
//		if (race.getState() != RaceState.FINISHED) {
//			throw new IllegalStateException("Can't edit race with state " + race.getState());
//		}	
//		
//		if (race.isResultsAvailable()) {
//			throw new IllegalStateException("Results already set");
//		}
		return race;
	}
	
	public List<Race> getAllRaces() {	
		return daoFactory.getRaceDAO().getAllRaces();
	}
	
	public List<Race> getCurrentRaces() {	
		return daoFactory.getRaceDAO().getCurrentRaces();
	}
}
