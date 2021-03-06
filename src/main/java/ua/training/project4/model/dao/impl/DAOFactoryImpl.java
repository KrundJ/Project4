package ua.training.project4.model.dao.impl;

import java.util.Objects;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.training.project4.Config;
import ua.training.project4.model.dao.BetDAO;
import ua.training.project4.model.dao.CoefficientsDAO;
import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.HorseDAO;
import ua.training.project4.model.dao.RaceDAO;
import ua.training.project4.model.dao.UserDAO;

public class DAOFactoryImpl extends DAOFactory {
	
	private static BasicDataSource connectionPool = null;

	private BasicDataSource getConnectionPool()  {
		
	    if (Objects.isNull(connectionPool)) {
	    	Config config = Config.getInstance(); 
	        BasicDataSource pool = new BasicDataSource();
	        pool.setUrl(config.getUrl());
	        pool.setPassword(config.getPass());
	        pool.setUsername(config.getUsername());
	        pool.setInitialSize(config.getPoolSize());
	        pool.setDefaultAutoCommit(true);
	        pool.setEnableAutoCommitOnReturn(true);
	        connectionPool = pool;
	    }
	    return connectionPool;
	}
	
	@Override
	public BetDAO getBetDAO() {
		return new BetDAOImpl(getConnectionPool());
	}
	
	@Override
	public CoefficientsDAO getCoefficientsDAO() {
		return new CoefficientsDAOImpl(getConnectionPool());
	}

	@Override
	public HorseDAO getHorseDAO() {
		return new HorseDAOImpl(getConnectionPool());
	}

	@Override
	public RaceDAO getRaceDAO() {
		return new RaceDAOImpl(getConnectionPool());
	}
	
	@Override
	public UserDAO getUserDAO() {
		return new UserDAOImpl(getConnectionPool());
	}
}
