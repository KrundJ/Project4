package ua.training.project4.model.entities;

public class Bet {
	
	private int betID;
	private int amount;	
	private boolean winningsReceived;
	private BetType betType;
	private Race race;
	private Horse horse;

	//DONT STORE ENUM IN DATABASE
	
	public enum BetType { 
		
		WIN_BET(new int[] {1,}, 1d), 
		PLACE_BET(new int[] {1,2}, 0.8d),
		SHOW_BET(new int[] {1,2,3}, 0.7d);
				
		private int[] winPlaces;
		private double winningsMultiplier;

		private BetType(int[] winPlaces, double winningsMultiplier) {
			this.winPlaces = winPlaces;
			this.winningsMultiplier = winningsMultiplier;
		}
		
		public int[] getWinPlaces() {
			return winPlaces;
		}
		
		public double getWinningsMultiplier() {
			return winningsMultiplier;
		}
	};
		
	public int getBetID() {
		return betID;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setBetID(int betID) {
		this.betID = betID;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean isWinningsReceived() {
		return winningsReceived;
	}
	
	public void setBilled() {
		winningsReceived = true;
	}
	
	public void setUnbilled() {
		winningsReceived = false;
	}
	
	public boolean checkWinCondition() {
		return false;
	}

	public BetType getBetType() {
		return betType;
	}

	public Race getRace() {
		return race;
	}

	public Horse getHorse() {
		return horse;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}
		
}
