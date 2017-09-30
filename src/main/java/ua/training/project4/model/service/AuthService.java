package ua.training.project4.model.service;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.UserDAO;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;

public class AuthService {
	
	private static AuthService instance = null;
	
	private AuthService() {
		
	}

	public static AuthService getInstance() {
		if (instance != null) 
			return instance;
		
		instance = new AuthService();
		return instance;	
	}
	
	public User getUserByLogin(String login) {
		return null;
	}
	
	public void addUser(User user) {
		DAOFactory.getInstance().getUserDAO().create(user);
	}
	
	public void removeUser(String login) {
		DAOFactory.getInstance().getUserDAO().delete(login);
	}

//	public void assignRole(String login, Role role) {
//		UserDAO dao = DAOFactory.getInstance().getUserDAO();
//		User u = dao.getByLogin(login);
//		u.setRole(role);
//		dao.update(u);
//	}
	
}
