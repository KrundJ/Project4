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
import java.util.Objects;
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
	
	private static final String GET_FOR_CURRENT_RACES = "SELECT horses.h_name, horses.h_number, jockeys.j_name, horses_races.r_id,  horses_races.h_coefficient "   
			+ "FROM horses JOIN horses_races "      
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN races "
            + "ON horses_races.r_id = races.r_id LEFT JOIN jockeys " 
			+ "ON horses.h_jockey = jockeys.j_id " 
            + "WHERE races.r_state = 'FINISHED' "
			+ "ORDER BY horses_races.r_id";
       
	private static final String UPDATE = "UPDATE horses_races " 
			+ "SET horses_races.h_coefficient = ? "
			+ "WHERE horses_races.r_id = ? AND horses_races.h_name = ?";
			
	public CoefficientsDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
			
	@Override
	public Coefficients getByRaceID(int raceID) {
		Coefficients result = new Coefficients();
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
	            horse = Horse.builder()
	            .name(rs.getString("h_name"))
	            .number(rs.getInt("h_number"))
	            .jockey(rs.getString("j_name")).build();
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
	
	
	private List<Coefficients> getListOfCoefficients(String query) {
		List<Coefficients> result = new LinkedList<>();
		try (Connection conn = connectionPool.getConnection()) {
			Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Horse horse = null;
            Coefficients coef = null;
            while (rs.next()) {	

				if (Objects.nonNull(coef) && rs.getInt("r_id") != coef.getRaceID()) { 
					coef = null;
				}
	
				if (Objects.isNull(coef)) { 
					coef = Coefficients.builder()
					.values(new HashMap<>())
					.raceID(rs.getInt("r_id")).build();
					result.add(coef);
				}
				
				horse = Horse.builder()
				.name(rs.getString("h_name"))
				.number(rs.getInt("h_number"))
				.jockey(rs.getString("j_name")).build();
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
	public List<Coefficients> getCoefficientsForAllRaces() {
		return getListOfCoefficients(GET_FOR_ALL_RACES);
	}
	
	@Override
	public List<Coefficients> getCoefficientsForCurrentRaces() {
		return getListOfCoefficients(GET_FOR_CURRENT_RACES);
	}

	@Override
	public void setCoefficients(Coefficients coef) {
		try (Connection conn = connectionPool.getConnection();
				AutoCloseable endBlock = conn::rollback) {
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
        } catch (Exception ex){
        	ex.printStackTrace();
        	//!!!!
        	throw new RuntimeException();
        } 
	}
}
