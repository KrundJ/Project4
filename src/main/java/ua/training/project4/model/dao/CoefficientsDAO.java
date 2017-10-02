package ua.training.project4.model.dao;

import java.util.List;

import ua.training.project4.model.entities.Coefficients;

public interface CoefficientsDAO {

	public Coefficients getByRaceID(int raceID);
	
	public List<Coefficients> getCoefficientsForAllRaces();
	
	public List<Coefficients> getCoefficientsForCurrentRaces();

	public void setCoefficients(Coefficients coef);
}
