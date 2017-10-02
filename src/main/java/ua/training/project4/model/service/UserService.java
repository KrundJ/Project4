package ua.training.project4.model.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;

public class UserService {
	
	private static UserService instance;
	
	private DAOFactory daoFactory;
	
	private UserService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}	
	
	public Bet makeBet(Bet bet) {
		Race race = daoFactory.getInstance().getRaceDAO().getRaceByID(bet.getRaceID());
//		if (race.getState() != RaceState.STARTED) {
//			throw new RuntimeException("Can't make bet on race with state " + race.getState());
//		}
//		if (race.getRaceResults().keySet().stream()
//				.noneMatch(h -> h.getName().equals(bet.getHorseName()))) {
//			
//			throw new RuntimeException(String.format("Horse with name %s not present in race", bet.getHorseName()));			
//		}
		
		return daoFactory.getBetDAO().makeBet(bet);
	}
	
	private boolean isWin(Bet bet, Race race) {
		return race.getRaceResults().entrySet().stream()
			.filter(e -> e.getKey().getName().equals(bet.getHorseName()))
			.filter(e -> Arrays.stream(bet.getBetType().getWinPlaces())
					.anyMatch(p -> p == e.getValue().intValue()))
			.findAny().isPresent();
	}
	
	public int calculateWinnings(int betID) throws Exception {
		//REWRITE WITH OPTIONALS
		Bet bet = Objects.requireNonNull(
				daoFactory.getBetDAO().getBetByID(betID),
				"Bet with ID " + betID + " not found");
		Race race = daoFactory.getRaceDAO().getRaceByID(bet.getRaceID());
		if (! race.getState().equals(RaceState.FINISHED))
			throw new Exception("Can't get winnings for race with state " + race.getState());
		
		Coefficients coefficients = daoFactory.getCoefficientsDAO().getByRaceID(race.getID());
		double coef_value = coefficients.getValues().get(race.getRaceResults().keySet().stream()
				.filter(h -> h.getName().equals(bet.getHorseName())).findFirst());
		
		if (isWin(bet, race)) {
			//SET WINNINGS RECEIVED
			return (int) Math.round(
					bet.getAmount() 
					* bet.getBetType().getWinningsMultiplier()
					* coef_value); 
		} else {
			throw new Exception("Sorry, your bet didn't win");
		}
	}
}
