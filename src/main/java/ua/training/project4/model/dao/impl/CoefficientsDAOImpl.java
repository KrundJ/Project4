package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.CoefficientsDAO;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;
 
public class CoefficientsDAOImpl implements CoefficientsDAO {
	
	private BasicDataSource connectionPool;
	
	private static final String GET_BY_RACE_ID = "SELECT horses.h_name, horses.h_number, jockeys.j_name, horses_races.r_id,  horses_races.h_coefficient "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN jockeys "
			+ "ON horses.h_jockey = jockeys.j_id "
            + "WHERE horses_races.r_id = ?";
	
	private static final String GET_FOR_ALL_RACES = "SELECT horses.h_name, horses.h_number, jockeys.j_name, horses_races.r_id,  horses_races.h_coefficient "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN jockeys "
			+ "ON horses.h_jockey = jockeys.j_id "
			+ "ORDER BY horses_races.r_id";
       
	private static final String UPDATE = "UPDATE horses_races " 
			+ "SET horses_races.h_coefficient = ? "
			+ "WHERE horses_races.r_id = ? AND horses_races.h_name = ?";
	
	public CoefficientsDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
			
	@Override
	public Coefficients getByRaceID(int raceID) {
		Coefficients result = Coefficients.builder().build();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(GET_BY_RACE_ID);
			ps.setInt(1, raceID);
            ResultSet rs = ps.executeQuery();
            Map<Horse, Double> values = new HashMap<>();
            Horse horse;
            while(rs.next()){
            	if (rs.isFirst()) {
					result.setRaceID(rs.getInt("r_id"));
				}
	            horse = new Horse();
	            horse.setName(rs.getString("h_name"));
	            horse.setNumber(rs.getInt("h_number"));
	            horse.setJockey(rs.getString("j_name"));
	            values.put(horse, rs.getDouble("h_coefficient"));
            }
            result.setValues(values);
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
		return result;
	}

	@Override
	public List<Coefficients> getCoefficientsForAllRaces() {
		List<Coefficients> result = new LinkedList<>();
		try (Connection conn = connectionPool.getConnection()) {
			Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(GET_FOR_ALL_RACES);
            Horse horse = null;
            Coefficients coef = null;
            while (rs.next()) {	

				if (coef != null && rs.getInt("r_id") != coef.getRaceID()) coef = null;
				
				if (coef == null) { 
					coef = Coefficients.builder().build();
					coef.setValues(new HashMap<>());
					coef.setRaceID(rs.getInt("r_id"));
					result.add(coef);
//					race.setID(rs.getInt("r_id"));
//					race.setDistance(RaceDistance.valueOf(rs.getString("r_distance")));
//					race.setState(RaceState.valueOf(rs.getString("r_state")));
//					race.setDate(rs.getDate("r_date"));
//					race.setRaceResults(raceResults);
//					races.add(race);
				}
				
				horse = new Horse();
				horse.setName(rs.getString("h_name"));
				horse.setNumber(rs.getInt("h_number"));
				horse.setJockey(rs.getString("j_name"));
				coef.getValues().put(horse, rs.getDouble("h_coefficient"));
			}
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
		return result;
	}

	@Override
	public void setCoefficients(Coefficients coef) {
		try (Connection conn = connectionPool.getConnection()) {
			conn.setAutoCommit(false);		
			PreparedStatement st = conn.prepareStatement(UPDATE);
			st.setInt(2, coef.getRaceID());
			for (Entry<Horse, Double> entry : coef.getValues().entrySet()) {
				st.setDouble(1, entry.getValue());
				st.setString(3, entry.getKey().getName());
				st.addBatch();
			}
			st.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);			
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
	}
}
