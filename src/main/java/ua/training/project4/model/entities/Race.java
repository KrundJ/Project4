package ua.training.project4.model.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Race {
	
	private int ID;
	private RaceDistance distance;
	private Date date;
	private RaceState state; 
	private Map<Horse, Integer> raceResults; 
	
	public enum RaceState { FINISHED, STARTED, PLANNED };
	
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
	
	public Race() {}
	
	public Race(Set<Horse> horses) {
		this.state = RaceState.PLANNED;
		Map<Horse, Integer> results = new HashMap<>();
		for (Horse h : horses) {
			results.put(h, null);
		}
		this.raceResults = results;
	}
	
	public int getID() {
		return ID;
	}
	
	public RaceDistance getDistance() {
		return distance;
	}

	public Date getDate() {
		return date;
	}

	public RaceState getState() {
		return state;
	}

	public Map<Horse, Integer> getRaceResults() {	
		return raceResults;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setDistance(RaceDistance distance) {
		this.distance = distance;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setState(RaceState state) {
		this.state = state;
	}

	public void setRaceResults(Map<Horse, Integer> raceResults) {
		this.raceResults = raceResults;
	}
	
	public boolean isResultsAvailable() {
		for (Entry<Horse, Integer> e : raceResults.entrySet()) {
			if (e.getValue() == null) return false;
		}
		return true;
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
		if (!(obj instanceof Race))
			return false;
		Race other = (Race) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Race [ID=" + ID + ", distance=" + distance + ", date=" + date + ", state=" + state + ", raceResults="
				+ raceResults + "]";
	}

}
