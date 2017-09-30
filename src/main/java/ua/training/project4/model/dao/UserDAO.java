package ua.training.project4.model.dao;

import ua.training.project4.model.entities.User;

public interface UserDAO {
	
	public boolean create(User item);

	public boolean delete(String login);
	
	public User getByLogin(String login);
}
