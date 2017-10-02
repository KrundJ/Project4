package ua.training.project4.model.dao;

import java.util.Optional;

import ua.training.project4.model.entities.Bet;

public interface BetDAO {

	public Bet makeBet(Bet bet);
	
	public Bet getBetByID(int betID);
}
