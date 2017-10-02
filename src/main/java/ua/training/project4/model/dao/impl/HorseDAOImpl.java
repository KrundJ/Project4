package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.HorseDAO;
import ua.training.project4.model.entities.Horse;

public class HorseDAOImpl implements HorseDAO {
	
	private BasicDataSource connectionPool;
			
	private static final String FIND_HORSES_BY_NAMES = "SELECT h_name, h_number, j_name FROM horses JOIN jockeys "
			+ "ON horses.h_jockey = jockeys.j_id "
			+ "WHERE horses.h_name IN "; //Names added in method
	
	private static final String FIND_ALL_HORSES = "SELECT h_name, h_number, j_name FROM horses JOIN"
			+ " jockeys ON horses.h_jockey = jockeys.j_id";
	
	public static final String NAME_FIELD = "h_name";
	public static final String NUMBER_FIELD = "h_number";
	public static final String JOCKEY_FIELD = "j_name";
			
	public HorseDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	public static Horse extractHorseFromResultSet(ResultSet rs) throws SQLException {
		return  Horse.builder()
	            .name(rs.getString(NAME_FIELD))
	            .number(rs.getInt(NUMBER_FIELD))
	            .jockey(rs.getString(JOCKEY_FIELD)).build();
	}
	
	private Set<Horse> queryForHorses(String query, String... names) {
		Set<Horse> horses = new HashSet<>();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement st = conn.prepareStatement(query);
			for (int j = 0; j < names.length; j++) {
				st.setString(j+1, names[j]);				
			}
			ResultSet rs = st.executeQuery();
            while(rs.next()){
	            horses.add(extractHorseFromResultSet(rs));
            }
        } catch (SQLException ex){
        	ex.printStackTrace();
        	//LOG
        	throw new RuntimeException();
        }
		return horses;
	}
		
	@Override
	public Set<Horse> getHorsesByNames(String... names) {
		StringBuilder sb = new StringBuilder();
		sb.append(FIND_HORSES_BY_NAMES);
		sb.append("(");
		for (int i = 0; i < names.length; i++) {
			sb.append("?");
			if (!(i == names.length - 1))
				sb.append(", ");
		}
		sb.append(")");
		return queryForHorses(sb.toString(), names);
	}

	@Override
	public Set<Horse> getAllHorses() {
		return queryForHorses(FIND_ALL_HORSES);
	}
}

