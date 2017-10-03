package ua.training.project4.model.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.training.project4.model.dao.BetDAO;
import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.Race.RaceState;


public class BookmakerService {
	
	private ServiceFactory factory = ServiceFactory.getInstance();

	private static BookmakerService instance;
	
	private DAOFactory daoFactory;
	
	private BookmakerService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static BookmakerService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new BookmakerService();
		}
		return instance;
	}	
	
	private Coefficients getOrThrowOnEmptyOptional(Optional<Coefficients> coefOptional) {
		if (! coefOptional.isPresent()) {
			throw new RuntimeException("Add message here");
		}
		return coefOptional.get();
	}
	
	public List<Coefficients> getCoefficientsForAllRaces() {
		return daoFactory.getCoefficientsDAO().getCoefficientsForAllRaces();
	}
	
	public List<Coefficients> getCoefficientsForCurrentRaces() {
		return daoFactory.getCoefficientsDAO().getCoefficientsForCurrentRaces();
	}
	
	public Coefficients getCoefficients(int raceID) {
		return getOrThrowOnEmptyOptional(
				daoFactory.getCoefficientsDAO().getByRaceID(raceID));
	}
	
	private Coefficients createCoefficients(int raceID, Map<String, Double> horseNameAndValue) {		
		Race race = factory.getAdministratorService().getPlannedRace(raceID);
		
		horseNameAndValue.keySet().stream()
		.filter(name -> (! race.getRaceResults().keySet().stream()
			.map(Horse::getName).collect(Collectors.toSet()).contains(name)))
		.findAny().ifPresent(name -> { throw new RuntimeException("Horse with name " + name + "not in current race"); });
		
		Map<Horse, Double> values = race.getRaceResults()
				.keySet().stream()
				.collect(Collectors.toMap(horse -> horse, horse -> horseNameAndValue.get(horse.getName())));
		
		return Coefficients.builder().raceID(raceID).values(values).build();
	}
	
	public void setCoefficiets(int raceID, Map<String, Double> horseNameAndValue) {
		Coefficients coef = createCoefficients(raceID, horseNameAndValue);
		daoFactory.getCoefficientsDAO().setCoefficients(coef);
	}
}

