package ac.za.dirisa.sst.bootstrapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.za.dirisa.sst.properties.ApplicationProperties;
import ac.za.dirisa.sst.properties.DataSourceProperties;

@Component
public class SQLinit {
	private static final Logger LOG = LoggerFactory.getLogger(SQLinit.class);
	
	@Autowired
	DataSourceProperties properties;
	
	@Autowired
	ApplicationProperties applicationProperties;
	
	// data source properties global variables
	public String username;
	public String password;
	public String uRL;
	
	// application properties global variables
    public String SQLdriver;
    
	@PostConstruct
	public void init() {

		// data source properties
		username = properties.getUsername();
		password = properties.getPassword();
		uRL = properties.getURL();

		// application properties
		SQLdriver = applicationProperties.getSqlDriver();

		// displaying data source properties
		LOG.info("Database username : {}", username);
		LOG.info("Database password : {}", password);
		LOG.info("Database uRL : {}", uRL);

		// displaying application properties
		LOG.info("Database Driver : {}", SQLdriver);

		// bootstraping - initializations
		initializer();
	}
    
    /**
	 * isDatabaseConnected function to check if database connected, exit the tool if
	 * not connected
	 * 
	 * @return
	 */
	public boolean isDatabaseConnected() {
		Connection connection = null;
		try {
			Class.forName(SQLdriver);
			connection = DriverManager.getConnection(uRL, username, password);
			LOG.info("Database connected Successfully...");
			connection.close();
			return true;
		} catch (Exception e) {
			LOG.error("Error Message: {}" + e.getMessage());
			System.exit(0);
			return false;
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("Error Message: {}" + e.getMessage());
			}
		}
	}

	/**
	 * initializer function to run the services
	 * 
	 * @return
	 */
	public boolean initializer() {
		if (!isDatabaseConnected()) {
			return false;
		}
		return true;
	}

}
