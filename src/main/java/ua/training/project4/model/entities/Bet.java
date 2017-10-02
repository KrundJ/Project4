package ua.training.project4.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Bet {
	
	private int ID;
	private int amount;	
	private boolean winningsReceived;
	private BetType betType;
	private int raceID;
	private String horseName;
		
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
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Bet))
			return false;
		Bet other = (Bet) obj;
		if (ID != other.ID)
			return false;
		return true;
	};
}
