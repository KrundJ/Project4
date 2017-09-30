package ua.training.project4.model.dao;

import java.util.List;
import java.util.Map;

import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;

public interface RaceDAO {
//	
//	public Set<Horse> getAllHorses();
//		
//	public void addNewRace(Race race);
//	
//	public void setStateStartedOnRace(int raceId);
//	
//	
//	
//	public void editRace(Race race);
//	
//	public void saveRaceResults(int raceID, Map<Integer, String> placeHorseName);

		
	public Race getRaceByID(int raceID);
		
	List<Race> getAllRaces();
	
	public boolean delete(int id);
	
	public boolean create(Race item);
	
	public boolean update(Race item);
}
