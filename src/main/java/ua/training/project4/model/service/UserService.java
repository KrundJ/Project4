package ua.training.project4.model.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;

public class UserService {

	private ServiceFactory factory = ServiceFactory.getInstance();
	
	private static UserService instance;
	
	private DAOFactory daoFactory;
	
	private UserService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static UserService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new UserService();
		}
		return instance;
	}	
	
	private Bet getOrThrowOnEmptyOptional(Optional<Bet> betOptional) {
		if (! betOptional.isPresent()) {
			throw new RuntimeException("Bet not found");
		}
		return betOptional.get();
	}
		
	public Bet makeBet(Bet bet) {
		Race race = factory.getAdministratorService().getStartedRace(bet.getRaceID());
		//UNCOMMENT
//		if (race.getRaceResults().keySet().stream()
//				.noneMatch(h -> h.getName().equals(bet.getHorseName()))) {
//			throw new RuntimeException(String.format("Horse with name %s not present in race", bet.getHorseName()));			
//		}
		return getOrThrowOnEmptyOptional(
				daoFactory.getBetDAO().create(bet));
	}
	
	private void winOrException(Bet bet, Race race) {
		boolean win = race.getRaceResults().entrySet().stream()
			.filter(e -> e.getKey().getName().equals(bet.getHorseName()))
			.filter(e -> Arrays.stream(bet.getBetType().getWinPlaces())
					.anyMatch(p -> p == e.getValue().intValue()))
			.findAny().isPresent();
		
		if (! win) throw new RuntimeException("Sorry, your bet didn't win");
	}
	
	private int billBet(Bet bet, double horseCoefficient) {
		bet.setWinningsReceived(true);
		daoFactory.getBetDAO().update(bet);
		return (int) Math.round(
					bet.getAmount() 
					* bet.getBetType().getWinningsMultiplier()
					* horseCoefficient); 
	}
	
	public int calculateWinnings(int betID) throws Exception {
		Bet bet = getOrThrowOnEmptyOptional(
				daoFactory.getBetDAO().getBetByID(betID));
		Race race = factory.getAdministratorService().getFinishedRace(bet.getRaceID());
		
		winOrException(bet, race);
		Coefficients coefficients = factory.getBookmakerService().getCoefficients(bet.getRaceID());
		double horseCoefficient = coefficients.getValues()
				.get(race.getRaceResults().keySet().stream()
						.filter(h -> h.getName().equals(bet.getHorseName()))
						.findFirst());
		return billBet(bet, horseCoefficient);
	}
}
