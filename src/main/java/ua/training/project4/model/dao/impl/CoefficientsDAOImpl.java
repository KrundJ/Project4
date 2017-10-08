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
import java.util.Optional;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import ua.training.project4.model.dao.CoefficientsDAO;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.entities.Race.RaceState;

import static ua.training.project4.view.Constants.*;
 
public class CoefficientsDAOImpl implements CoefficientsDAO {
	
	private static Logger log = Logger.getLogger(CoefficientsDAOImpl.class.getName());
	
	private BasicDataSource connectionPool;
	
	private static final String GET_BY_RACE_ID = "SELECT horses.h_name, horses.h_number, jockeys.j_name, horses_races.r_id,  horses_races.h_coefficient "   
			+ "FROM horses JOIN horses_races "     
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN jockeys "
			+ "ON horses.h_jockey = jockeys.j_id "
            + "WHERE horses_races.r_id = ?";
	
	private static final String GET_FOR_RACES_WITH_STATE = "SELECT horses.h_name, horses.h_number, jockeys.j_name, horses_races.r_id,  horses_races.h_coefficient "   
			+ "FROM horses JOIN horses_races "      
			+ "ON horses.h_name = horses_races.h_name LEFT JOIN races "
            + "ON horses_races.r_id = races.r_id LEFT JOIN jockeys " 
			+ "ON horses.h_jockey = jockeys.j_id " 
            + "WHERE races.r_state = '%s' "
			+ "ORDER BY horses_races.r_id";
       
	private static final String UPDATE = "UPDATE horses_races " 
			+ "SET horses_races.h_coefficient = ? "
			+ "WHERE horses_races.r_id = ? AND horses_races.h_name = ?";
	
	public static final String COEF_FIELD = "h_coefficient";
			
	public CoefficientsDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
			
	@Override
	public Optional<Coefficients> getByRaceID(int raceID) {
		Optional<Coefficients> result = Optional.empty();
		try (Connection conn = connectionPool.getConnection()) {
			Coefficients coefficients = new Coefficients();
			PreparedStatement ps = conn.prepareStatement(GET_BY_RACE_ID);
			ps.setInt(1, raceID);
            ResultSet rs = ps.executeQuery();
            Map<Horse, Double> values = new HashMap<>();
            Horse horse;
            while(rs.next()){
            	if (rs.isFirst()) {
					coefficients.setRaceID(rs.getInt(RaceDAOImpl.ID_FIELD));
				}
            	horse = HorseDAOImpl.extractHorseFromResultSet(rs);
            	//Returns 0.0 on null column 
	            values.put(horse, rs.getDouble(COEF_FIELD));
            }
            coefficients.setValues(values);
            result = Optional.of(coefficients);
        } catch (Exception e){
        	log.info(e);
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
				if (Objects.nonNull(coef) && rs.getInt(RaceDAOImpl.ID_FIELD) != coef.getRaceID()) { 
					coef = null;
				}	
				if (Objects.isNull(coef)) { 
					coef = Coefficients.builder()
					.values(new HashMap<>())
					.raceID(rs.getInt(RaceDAOImpl.ID_FIELD)).build();
					result.add(coef);
				}
				horse = HorseDAOImpl.extractHorseFromResultSet(rs);
				coef.getValues().put(horse, rs.getDouble(COEF_FIELD));
			}
        } catch (Exception e){
        	log.info(e);
        	throw new RuntimeException(COEFFICIENTS_GET_ERR);
        } 
		return result;
	}
	
	@Override
	public List<Coefficients> getCoefficientsForCurrentRaces() {
		return getListOfCoefficients(
				String.format(GET_FOR_RACES_WITH_STATE, RaceState.STARTED.name()));
	}
	
	@Override
	public List<Coefficients> getCoefficientsForPlannedRaces() {
		return getListOfCoefficients(
				String.format(GET_FOR_RACES_WITH_STATE, RaceState.PLANNED.name()));
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
        } catch (Exception e){
        	log.info(e); 
        	throw new RuntimeException(COEFFICIENTS_SET_ERR);
        } 
	}
}
