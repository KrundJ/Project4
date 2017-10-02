package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.RaceDAO;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;

public class RaceDAOImpl implements RaceDAO {
	
	private BasicDataSource connectionPool;
		
	private static final String CREATE_RACE = "INSERT INTO races (r_distance, r_state, r_date) "
			+ "VALUES (?, ?, ?)";
	
	private static final String CREATE_HORSE_RACE_RECORD = "INSERT INTO horses_races (h_name, r_id) "
			+ "VALUES (?, ?)";
	
	private static final String DELETE_RACE = "DELETE r, hr FROM races r JOIN horses_races hr "
			+ "ON r.r_id = hr.r_id  "
			+ "WHERE r.r_id = ?"; 

	private static final String GET_RACE_BY_ID = "SELECT horses.h_name, horses.h_number, horses_races.h_place, "
			+ "races.r_id, races.r_distance, races.r_state, races.r_date, jockeys.j_name "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN races "             
			+ "ON horses_races.r_id = races.r_id LEFT JOIN jockeys "    
            + "ON horses.h_jockey = jockeys.j_id " 
            + "WHERE horses_races.r_id = ?";
	
	private static final String GET_ALL_RACES = "SELECT horses.h_name, horses.h_number, horses_races.h_place, "
			+ "races.r_id, races.r_distance, races.r_state, races.r_date, jockeys.j_name "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN races "             
			+ "ON horses_races.r_id = races.r_id LEFT JOIN jockeys "    
            + "ON horses.h_jockey = jockeys.j_id "
			+ "ORDER BY races.r_id";
	
	private static final String GET_CURRENT_RACES = "SELECT horses.h_name, horses.h_number, horses_races.h_place, "
			+ "races.r_id, races.r_distance, races.r_state, races.r_date, jockeys.j_name "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN races "             
			+ "ON horses_races.r_id = races.r_id LEFT JOIN jockeys "    
            + "ON horses.h_jockey = jockeys.j_id "
			+ "WHERE races.r_state = 'FINISHED' "
			+ "ORDER BY races.r_id";
	
	private static final String UPDATE_RACE = "UPDATE races JOIN horses_races "
			+ "ON races.r_id = horses_races.r_id  "
			+ "SET races.r_distance = ?, races.r_state = ?, races.r_date = ?, horses_races.h_name = ?, horses_races.h_place = ? "
			+ "WHERE races.r_id = ? AND horses_races.h_name = ?";
		
	public RaceDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
		
	private List<Race> getListOfRaces(String query) {
		List<Race> races = new LinkedList<>();
		try (Connection conn = connectionPool.getConnection()) {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			Race race = null;
			Horse horse = null;
			while (rs.next()) {	
				
				if (Objects.nonNull(race) && rs.getInt("r_id") != race.getID()) { 
					race = null;
				}
				
				if (Objects.isNull(race)) { 				
					race = Race.builder()
					.ID(rs.getInt("r_id"))
					.distance(RaceDistance.valueOf(rs.getString("r_distance")))
					.state(RaceState.valueOf(rs.getString("r_state")))
					.date(rs.getDate("r_date"))
					.raceResults(new HashMap<>()).build();
					races.add(race);
				}

				horse = Horse.builder()
				.name(rs.getString("h_name"))
				.number(rs.getInt("h_number"))
				.jockey(rs.getString("j_name")).build();
				race.getRaceResults().put(horse, rs.getInt("h_place"));
			}
		} catch (SQLException ex){
	       	ex.printStackTrace();
	       	//!!!!
	       	throw new RuntimeException();
	    }
		return races;
	}
	
	@Override
	public List<Race> getAllRaces() {
		return getListOfRaces(GET_ALL_RACES);
	}
	
	@Override
	public List<Race> getCurrentRaces() {
		return getListOfRaces(GET_CURRENT_RACES);
	}

	@Override
	public void create(Race item) {
		try (Connection conn = connectionPool.getConnection();
				AutoCloseable endBlock = conn::rollback) {
			conn.setAutoCommit(false);
			PreparedStatement r_st = conn.prepareStatement(CREATE_RACE, Statement.RETURN_GENERATED_KEYS);
			r_st.setString(1, item.getDistance().name());
			r_st.setString(2, item.getState().name());
			r_st.setDate(3, new Date(item.getDate().getTime()));
			r_st.executeUpdate();
			ResultSet rs = r_st.getGeneratedKeys();
			int raceID = 0;
			if( rs.next()){
                raceID = rs.getInt(1);
            }
			PreparedStatement hr_st = conn.prepareStatement(CREATE_HORSE_RACE_RECORD);
			for (Horse horse : item.getRaceResults().keySet()) {
				hr_st.setString(1, horse.getName());
				hr_st.setInt(2, raceID);
				hr_st.addBatch();
			}
			hr_st.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);			
        } catch (Exception ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        }		
	}
	
	@Override
	public void update(Race item) {
		try (Connection conn = connectionPool.getConnection();
				AutoCloseable endBlock = conn::rollback) {
			conn.setAutoCommit(false);		
			PreparedStatement st = conn.prepareStatement(UPDATE_RACE);
			st.setString(1, item.getDistance().name());
			st.setString(2, item.getState().name());
			st.setDate(3,  new Date(item.getDate().getTime()));
			st.setInt(6,  item.getID());
			for (Horse horse : item.getRaceResults().keySet()) {
				st.setString(4, horse.getName());
				//prevent null pointer
				st.setObject(5, item.getRaceResults().get(horse));
				st.setString(7, horse.getName());
				st.addBatch();
			}
			st.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);			
        } catch (Exception ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
	}

	@Override
	public void delete(int id) {
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(DELETE_RACE);
			st.setInt(1, id);
			st.executeUpdate();			
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    	//!!!!
	    	throw new RuntimeException();
	    }
	}

	@Override
	public Race getRaceByID(int raceID) {
		Race race = new Race();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(GET_RACE_BY_ID);
			st.setInt(1, raceID);
			ResultSet rs = st.executeQuery();
					
			Map<Horse, Integer> raceResults = new HashMap<>();
			Horse horse = null;
			while (rs.next()) {
				if (rs.isFirst()) {
					race.setID(rs.getInt("r_id"));
					race.setDistance(RaceDistance.valueOf(rs.getString("r_distance")));
					race.setState(RaceState.valueOf(rs.getString("r_state")));
					race.setDate(rs.getDate("r_date"));
				}
				horse = Horse.builder()
				.name(rs.getString("h_name"))
	            .number(rs.getInt("h_number"))
	            .jockey(rs.getString("j_name")).build();
	            raceResults.put(horse, rs.getInt("h_place"));
			}
			race.setRaceResults(raceResults);
        } catch (Exception ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        }
		return race;
	}
}
