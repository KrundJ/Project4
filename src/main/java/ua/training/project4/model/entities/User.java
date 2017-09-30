package ua.training.project4.model.entities;

public class User {
	
	private String login;
	
	private String passHash;
		
	private Role role;
	
	public enum Role { ADMINISTRATOR, BOOKMAKER, USER }

	public String getLogin() {
		return login;
	}

	public String getPassHash() {
		return passHash;
	}

	public Role getRole() {
		return role;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", passHash=" + passHash  + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	};
}
