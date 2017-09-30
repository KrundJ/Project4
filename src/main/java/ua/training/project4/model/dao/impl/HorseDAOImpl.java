package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.HorseDAO;
import ua.training.project4.model.entities.Horse;

public class HorseDAOImpl implements HorseDAO {
	
	private BasicDataSource connectionPool;
			
	private static final String FIND_HORSES_BY_NAMES = "SELECT h_name, h_number, j_name FROM horses JOIN jockeys "
			+ "ON horses.h_jockey = jockeys.j_id "
			+ "WHERE horses.h_name IN (?, ?, ?, ?, ?)";

	private static final String FIND_ALL_HORSES = "SELECT h_name, h_number, j_name FROM horses JOIN"
			+ " jockeys ON horses.h_jockey = jockeys.j_id";
			
	public HorseDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
		
	@Override
	public Set<Horse> getHorsesByNames(String... names) {
		Set<Horse> horses = new HashSet<>();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn
	                .prepareStatement(FIND_HORSES_BY_NAMES);
			String name;
			for (int i = 0; i < names.length; i++) {
				try {
					name = names[i];
				} catch (Exception e) {
					name = "";
				}
				 st.setString(i+1, name);
			}
			ResultSet rs = st.executeQuery();
            Horse horse;
            while(rs.next()){
	            horse = new Horse();
	            horse.setName(rs.getString("h_name"));
	            horse.setNumber(rs.getInt("h_number"));
	            horse.setJockey(rs.getString("j_name"));
	            horses.add(horse);
            }
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        }
		System.out.println("Horses size " + horses.size());
		return horses;
	}

	@Override
	public Set<Horse> getAllHorses() {
		Set<Horse> horses = new HashSet<>();
		try (Connection conn = connectionPool.getConnection()) {
			Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(FIND_ALL_HORSES);
            Horse horse;
            while(rs.next()){	
	            horse = new Horse();
	            horse.setName(rs.getString("h_name"));
	            horse.setNumber(rs.getInt("h_number"));
	            horse.setJockey(rs.getString("j_name"));
	            horses.add(horse);
            }
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
		return horses;
	}
	
}

