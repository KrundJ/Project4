package ua.training.project4.model.service;

import java.util.Objects;
import java.util.Optional;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.User;

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
	
	private User getOrThrowOnEmptyOptional(Optional<User> userOptional) {
		if (! userOptional.isPresent()) {
			throw new RuntimeException("Add message here");
		}
		return userOptional.get();
	}
	
	public User getUserByLogin(String login) {
		return getOrThrowOnEmptyOptional(
				daoFactory.getUserDAO().getByLogin(login));
	}
	
	public void addUser(User user) {
		daoFactory.getUserDAO().create(user);
	}
}
