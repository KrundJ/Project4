package ua.training.project4.model.entities;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Coefficients {

	private int raceID;
	
	private Map<Horse, Double> values;
/*
	public int getRaceID() {
		return raceID;
	}

	public Map<Horse, Double> getValues() {
		return values;
	}

	public void setRaceID(int raceID) {
		this.raceID = raceID;
	}

	public void setValues(Map<Horse, Double> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Coefficients [raceID=" + raceID + ", values=" + values + "]";
	}	
*/	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + raceID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coefficients))
			return false;
		Coefficients other = (Coefficients) obj;
		if (raceID != other.raceID)
			return false;
		return true;
	}
}
