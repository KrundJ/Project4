package ua.training.project4.model.entities;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
@ToString
public class Coefficients {

	private int raceID;
	
	private Map<Horse, Double> values;
	
	@Tolerate
	public Coefficients() {}
	
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
