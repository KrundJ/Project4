package ua.training.project4.model.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import ua.training.project4.model.entities.Horse.HorseBuilder;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class Race {
	
	private int ID;
	private RaceDistance distance;
	private Date date;
	private RaceState state; 
	private Map<Horse, Integer> raceResults; 
	
	public enum RaceState {
		//Winnings can be received
		FINISHED, 
		//Bets can be made
		STARTED, 
		//Still able to edit
		PLANNED 
	};
	
	public static final int NUMBER_OF_HORSES_IN_RACE = 5;
	
	public enum RaceDistance { 
		RACE_ON_1600m(1600), 
		RACE_ON_2400m(2400), 
		RACE_ON_3200m(3200);
		
		private int distance;

		private RaceDistance(int distance) {
			this.distance = distance;
		}
		
		public int getDistance() {
			return distance;
		}
	};
	
	@Tolerate
	public Race() {}
	
//	public boolean isResultsAvailable() {
//		return raceResults.entrySet().stream()
//				.allMatch(entry -> Objects.nonNull(entry.getValue()));
//	}
	
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
		if (!(obj instanceof Race))
			return false;
		Race other = (Race) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
}
