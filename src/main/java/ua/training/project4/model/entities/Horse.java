package ua.training.project4.model.entities;

public class Horse {
	
	private String name;
	private int number;
	private String jockey;
				
	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public String getJockey() {
		return jockey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setJockey(String jockey) {
		this.jockey = jockey;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Horse))
			return false;
		Horse other = (Horse) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Horse [name=" + name + ", number=" + number + ", jockey=" + jockey + "]";
	}
}


