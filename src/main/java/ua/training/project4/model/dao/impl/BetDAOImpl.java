package ua.training.project4.model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.model.dao.BetDAO;
import ua.training.project4.model.entities.Bet;

public class BetDAOImpl implements BetDAO {
	
	private BasicDataSource connectionPool;
	
	private static final String CREATE_BET = "INSERT INTO bets (b_amount, b_winnings_received, b_type, b_horse_name, b_race_id) "
			+ "VALUES (?, FALSE, ?, ?, ?)";
		
	public BetDAOImpl(BasicDataSource connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public Bet makeBet(Bet bet) {
		try (Connection conn = connectionPool.getConnection();
				AutoCloseable endBlock = conn::rollback) {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(CREATE_BET, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, bet.getAmount());
			ps.setString(2, bet.getBetType().name());
			ps.setString(3, bet.getHorseName());
			ps.setInt(4, bet.getAmount());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int ID;
			if( rs.next()){
                ID = rs.getInt(1);
                bet.setID(ID);
            }
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return bet;
	}

	@Override
	public Bet getBetByID(int betID) {
		
		return null;
	}
}

