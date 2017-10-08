package ua.training.project4.model.dao;

import java.util.Optional;

import ua.training.project4.model.entities.User;

public interface UserDAO {
	
	public void create(User item);
	
	public Optional<User> getByLogin(String login);
}
