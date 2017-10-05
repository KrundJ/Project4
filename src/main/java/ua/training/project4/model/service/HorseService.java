package ua.training.project4.model.service;

import java.util.Objects;
import java.util.Set;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Horse;

public class HorseService {
	
	private DAOFactory daoFactory;
	
	private static HorseService instance;
			
	private HorseService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static HorseService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new HorseService();
		}
		return instance;
	}

	public Set<Horse> getAllHorses() {
		return daoFactory.getHorseDAO().getAllHorses();
	}
	
	public Set<Horse> getHorsesByNames(String... horseNames) {		
		return daoFactory.getHorseDAO().getHorsesByNames(horseNames);
	}
	
}
