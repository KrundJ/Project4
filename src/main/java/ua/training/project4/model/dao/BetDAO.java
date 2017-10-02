package ua.training.project4.model.dao;

import java.util.Optional;

import ua.training.project4.model.entities.Bet;

public interface BetDAO {

	public Optional<Bet> create(Bet bet);
	
	public Optional<Bet> getBetByID(int betID);
	
	public void update(Bet bet);
}
