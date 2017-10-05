package ua.training.project4.model.dao;

import java.util.List;
import java.util.Optional;

import ua.training.project4.model.entities.Coefficients;

public interface CoefficientsDAO {

	public Optional<Coefficients> getByRaceID(int raceID);
	
	//public List<Coefficients> getCoefficientsForAllRaces();
	
	public List<Coefficients> getCoefficientsForCurrentRaces();
	
	public List<Coefficients> getCoefficientsForPlannedRaces();

	public void setCoefficients(Coefficients coef);
}
