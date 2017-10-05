package ua.training.project4.model.dao;

import java.util.List;
import java.util.Optional;

import ua.training.project4.model.entities.Race;

public interface RaceDAO {

	public Optional<Race> getRaceByID(int raceID);
		
	public List<Race> getAllRaces();
	
	public List<Race> getCurrentRaces();
	
	public List<Race> getPlannedRaces();
	
	public void delete(int id);
	
	public void create(Race item);
	
	public void update(Race item);
}
