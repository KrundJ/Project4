package ua.training.project4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import ua.training.project4.model.dao.DAOFactory;

public class Config {

    private String url;
    private String username;
    private String pass;
    private String factoryClassName;
    private int poolSize;

    private static Config INSTANCE = null;
    
    private Config() {
        load();
    }
    
    private void load() {
    	try( InputStream is = DAOFactory.class.getResourceAsStream("/WEB-INF/configuration.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            username = properties.getProperty("db.user");
            pass = properties.getProperty("db.pass");
            url = properties.getProperty("db.url");
            factoryClassName = properties.getProperty("db.factoryClassName");
            poolSize = Integer.parseInt(properties.getProperty("db.poolSize"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public static Config getInstance(){
    	if (INSTANCE == null) {
    		INSTANCE = new Config();
    		return INSTANCE;
    	}
    	return INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getFactoryClassName() {
        return factoryClassName;
    }

	public int getPoolSize() {
		return poolSize;
	}
}

