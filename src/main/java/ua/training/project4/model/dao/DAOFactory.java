package ua.training.project4.model.dao;

import static org.mockito.Mockito.mock;

import ua.training.project4.Config;

public abstract class DAOFactory {
	
	private static DAOFactory factoryMock = mock(DAOFactory.class);
	
	public abstract BetDAO getBetDAO();
	
	public abstract CoefficientsDAO getCoefficientsDAO();
    
    public abstract HorseDAO getHorseDAO();
    
    public abstract RaceDAO getRaceDAO();
    
    public abstract UserDAO getUserDAO();
	
    public static DAOFactory getInstance() {
        String className = Config.getInstance().getFactoryClassName();      
        DAOFactory factory;
        try {
            factory = (DAOFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("DAOFactory instantinatin error");
        }
        if (Config.getInstance().isTesting())
        	return factoryMock;
        return factory;
    }
}
