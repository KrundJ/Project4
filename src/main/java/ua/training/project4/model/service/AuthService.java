package ua.training.project4.model.service;

import java.util.Objects;
import java.util.Optional;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;

public class AuthService {
			
	private static AuthService instance;
	
	private DAOFactory daoFactory;
	
	private AuthService() {
		daoFactory = DAOFactory.getInstance();
	}
			
	public static AuthService getInstance() {
		if (Objects.isNull(instance)) {
			instance = new AuthService();
		}
		return instance;
	}
	
	public Optional<User> getUserByLogin(String login) {
		return daoFactory.getUserDAO().getByLogin(login);
	}
	
	public void addUser(String login, String password) {
		daoFactory.getUserDAO().create(
				User.builder()
				.login(login)
				.password(password)
				.role(Role.USER).build());
	}
	
	public boolean isLoginUnique(String login) {
		Optional<User> user = daoFactory.getUserDAO().getByLogin(login);
		return (! user.isPresent());
	}
	
	public boolean checkUserCredentials(String login, String password) {
		Optional<User> user = getUserByLogin(login);
		if (! user.isPresent()) 
			return false;
		return (user.get().getPassword().equals(password)) ? true : false;
	}
}
