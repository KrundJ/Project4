package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.UserDAO;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;

public class UserDAOImpl implements UserDAO {
	
//	private static final String UPDATE_USER = "UPDATE users "
//			+ "SET u_pass_hash = ?, u_role = ? "
//			+ "WHERE u_login = ?";
	
	private static final String ADD_USER = "INSERT INTO users (u_login, u_pass_hash, u_role) "
			+ "VALUES (?, ?, ?)";
	
	private static final String DELETE_USER = "DELETE FROM users "
			+ "WHERE u_login = ?";
	
	private static final String GET_USER = "SELECT * FROM users "
			+ "WHERE u_login = ?";
	
	private BasicDataSource connectionPool;

	public UserDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}

//	@Override
//	public boolean update(User item) {
//		try (Connection conn = connectionPool.getConnection()) {
//			PreparedStatement st = conn.prepareStatement(UPDATE_USER);
//			st.setString(1, item.getPassHash());
//			st.setBoolean(2, item.isBlocked());
//			st.setString(3, item.getRole().name());
//			st.setString(4, item.getLogin());
//			st.executeUpdate();
//			
//		} catch (SQLException ex){
//	        ex.printStackTrace();
//	        //!!!!
//	        throw new RuntimeException();
//		}
//		return true;
//	}

	@Override
	public boolean delete(String login) {
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(DELETE_USER);
			st.setString(1, login);
			st.execute();
		} catch (SQLException ex){
	        ex.printStackTrace();
	        //!!!!
	        throw new RuntimeException();
		}
		return true;	
	}

	@Override
	public boolean create(User item) {
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(ADD_USER);
			st.setString(1, item.getLogin());
			st.setString(2, item.getPassHash());
			st.setString(3, item.getRole().name());
			st.executeUpdate();
		} catch (SQLException ex){
	        ex.printStackTrace();
	        //!!!!
	        throw new RuntimeException();
		}
		return true;	
	}

	@Override
	public User getByLogin(String login) {
		User user = null;
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(GET_USER);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			rs.first();
			user = new User();
			user.setLogin(rs.getString("u_login"));
			user.setPassHash(rs.getString("u_pass_hash"));			
			user.setRole(Role.valueOf(rs.getString("u_role")));
		} catch (SQLException ex){
	        ex.printStackTrace();
	        //!!!!
	        throw new RuntimeException();
		}
		return user;
	}
}
