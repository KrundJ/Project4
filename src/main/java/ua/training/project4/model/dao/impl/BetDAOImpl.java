package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import ua.training.project4.model.dao.BetDAO;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Bet.BetType;

import static ua.training.project4.view.Constants.*;

public class BetDAOImpl implements BetDAO {
	
	private static Logger log = Logger.getLogger(BetDAOImpl.class.getName());
	
	private BasicDataSource connectionPool;
	
	private static final String CREATE_BET = "INSERT INTO bets (b_amount, b_winnings_received, b_type, b_horse_name, b_race_id) "
			+ "VALUES (?, FALSE, ?, ?, ?)";
	
	private static final String UPDATE_BET = "UPDATE bets SET b_amount = ?, b_winnings_received = ?, "
			+ "b_type = ?, b_horse_name = ?, b_race_id = ? "
			+ "WHERE b_id = ?";
		
	private static final String GET_BY_ID = "SELECT * FROM bets WHERE bets.b_id = ?";
	
	public static final String ID_FIELD = "b_id";
	public static final String AMOUNT_FIELD = "b_amount";
	public static final String WINNINGS_FIELD = "b_winnings_received";
	public static final String TYPE_FIELD = "b_type";
	public static final String HORSE_NAME_FIELD = "b_horse_name";
	public static final String RACE_ID_FIELD = "b_race_id";
	
	public BetDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}
	
	private Bet exractBetFromResultSet(ResultSet rs) throws SQLException {
		return Bet.builder()
      		   .ID(rs.getInt(ID_FIELD))
      		   .amount(rs.getInt(AMOUNT_FIELD))
      		   .winningsReceived(rs.getBoolean(WINNINGS_FIELD))
      		   .betType(BetType.valueOf(rs.getString(TYPE_FIELD)))
      		   .horseName(rs.getString(HORSE_NAME_FIELD))
      		   .raceID(rs.getInt(RACE_ID_FIELD)).build();
	}
	
	private void betToStatement(PreparedStatement ps, Bet bet) throws SQLException {
		ps.setInt(1, bet.getAmount());
		ps.setString(2, bet.getBetType().name());
		ps.setString(3, bet.getHorseName());
		ps.setInt(4, bet.getAmount());
	}

	@Override
	public Optional<Bet> create(Bet bet) {
		Optional<Bet> resultBet = Optional.empty();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(CREATE_BET, Statement.RETURN_GENERATED_KEYS);
			betToStatement(ps, bet);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int ID;
			if( rs.next()){
                ID = rs.getInt(1);
                bet.setID(ID);
            }
			resultBet = Optional.of(bet);
		} catch (Exception e) {
			log.info(e);
			throw new RuntimeException(CREATE_BET_ERR);
		}
		return resultBet;
	}

	@Override
	public Optional<Bet> getBetByID(int betID) {
		Optional<Bet> bet = Optional.empty();
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(GET_BY_ID);
			ps.setInt(1, betID);
			ResultSet rs = ps.executeQuery();
			if(rs.first()){
               bet = Optional.of(exractBetFromResultSet(rs));
            }
		} catch (Exception e) {
			log.info(e);
		}
		return bet;
	}

	@Override
	public void update(Bet bet) {
		try (Connection conn = connectionPool.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(UPDATE_BET);
			ps.setInt(1, bet.getAmount());
			ps.setBoolean(2, bet.isWinningsReceived());
			ps.setString(3, bet.getBetType().name());
			ps.setString(4, bet.getHorseName());
			ps.setInt(5, bet.getRaceID());
			ps.setInt(6, bet.getID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.info(e);
			throw new RuntimeException(UPDATE_BET_ERR);
		}
	}
}

