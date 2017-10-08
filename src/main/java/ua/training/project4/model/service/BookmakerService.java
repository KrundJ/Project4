package ua.training.project4.model.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import static ua.training.project4.view.Constants.*;

public class BookmakerService {
	
	private ServiceFactory factory = ServiceFactory.getInstance();
	
	private static Logger log = Logger.getLogger(BookmakerService.class.getName());

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
			log.info("coefficients not found");
			throw new RuntimeException(COEFFICIENTS_NOT_FOUND);
		}
		return coefOptional.get();
	}
	
	public List<Coefficients> getCoefficientsForCurrentRaces() {
		return daoFactory.getCoefficientsDAO().getCoefficientsForCurrentRaces();
	}
	
	public List<Coefficients> getCoefficientsForPlannedRaces() {
		return daoFactory.getCoefficientsDAO().getCoefficientsForPlannedRaces();
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
		.findAny().ifPresent(name -> {
			log.info("Some horses from race results not in race with ID" + raceID);
			log.info(horseNameAndValue);
			throw new RuntimeException(HORSE_NOT_IN_RACE); });
		
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

