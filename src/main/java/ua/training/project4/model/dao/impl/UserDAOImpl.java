package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import ua.training.project4.model.dao.UserDAO;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;

import static ua.training.project4.view.Constants.*;

public class UserDAOImpl implements UserDAO {
	
	private static Logger log = Logger.getLogger(UserDAOImpl.class.getName());
		
	private static final String ADD_USER = "INSERT INTO users (u_login, u_pass, u_role) "
			+ "VALUES (?, ?, ?)";
		
	private static final String GET_USER = "SELECT * FROM users "
			+ "WHERE u_login = ?";
	
	public static final String LOGIN_FIELD = "u_login";
	public static final String PASS_FIELD = "u_pass";
	public static final String ROLE_FIELD = "u_role";
	
	private BasicDataSource connectionPool;

	public UserDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	private User extractUserFromResultSet(ResultSet rs) throws SQLException {
		return User.builder()
			.login(rs.getString(LOGIN_FIELD))
			.password(rs.getString(PASS_FIELD))					
			.role(Role.valueOf(rs.getString(ROLE_FIELD))).build();
	}

	@Override
	public void create(User item) {
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(ADD_USER);
			st.setString(1, item.getLogin());
			st.setString(2, item.getPassword());
			st.setString(3, item.getRole().name());
			st.executeUpdate();
		} catch (Exception e) {
	        log.info(e);
	        throw new RuntimeException(CREATE_USER_ERR);
		}
	}

	@Override
	public Optional<User> getByLogin(String login) {
		Optional<User> user = Optional.empty();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(GET_USER);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				user = Optional.of(extractUserFromResultSet(rs));
			}
		} catch (Exception e) {
			log.info(e);
		}
		return user;
	}
}
