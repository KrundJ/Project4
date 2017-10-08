package ua.training.project4.model.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.apache.log4j.Logger;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Winnings;

import static ua.training.project4.view.Constants.*;

public class UserService {

	private ServiceFactory factory = ServiceFactory.getInstance();
	
	private static Logger log = Logger.getLogger(UserService.class.getName());
	
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
			log.info("bet not found");
			throw new RuntimeException(BET_NOT_FOUND);
		}
		return betOptional.get();
	}
		
	public Bet makeBet(Bet bet) {
		Race race = factory.getAdministratorService().getStartedRace(bet.getRaceID());
		if (race.getRaceResults().keySet().stream()
				.noneMatch(h -> h.getName().equals(bet.getHorseName()))) {
			log.info("Some horses not in race");
			log.info(bet);
			throw new RuntimeException(HORSE_NOT_IN_RACE);			
		}
		return getOrThrowOnEmptyOptional(
				daoFactory.getBetDAO().create(bet));
	}
	
	private boolean isWin(Bet bet, Race race) {
		return race.getRaceResults().entrySet().stream()
			.filter(e -> e.getKey().getName().equals(bet.getHorseName()))
			.filter(e -> Arrays.stream(bet.getBetType().getWinPlaces())
					.anyMatch(p -> p == e.getValue().intValue()))
			.findAny().isPresent();
	}
	
	private int billBet(Bet bet, double horseCoefficient) {
		bet.setWinningsReceived(true);
		daoFactory.getBetDAO().update(bet);
		return (int) Math.round(
					bet.getAmount() 
					* bet.getBetType().getWinningsMultiplier()
					* horseCoefficient); 
	}
	
	public Winnings calculateWinnings(int betID) {
		Bet bet = getOrThrowOnEmptyOptional(
				daoFactory.getBetDAO().getBetByID(betID));
		Race race = factory.getAdministratorService().getFinishedRace(bet.getRaceID());
		
		if (! isWin(bet, race)) 
			return Winnings.builder().message(BET_NOT_WIN).build();
		if (bet.isWinningsReceived())
			return Winnings.builder().message(WINNINGS_RECEIVED).build();
		Coefficients coefficients = factory.getBookmakerService().getCoefficients(bet.getRaceID());
			
		double horseCoefficient = coefficients.getValues()
				.get(race.getRaceResults().keySet().stream()
						.filter(h -> h.getName().equals(bet.getHorseName()))
						.findFirst().get());
		int amount = billBet(bet, horseCoefficient);
		return Winnings.builder().message(WINNINGS_MESSAGE).amount(amount).build();
	}
}
