package ua.training.project4.model.dao;

import java.util.Set;

import ua.training.project4.model.entities.Horse;

public interface HorseDAO {
	
	public Set<Horse> getHorsesByNames(String... name);
	
	public Set<Horse> getAllHorses();
}
