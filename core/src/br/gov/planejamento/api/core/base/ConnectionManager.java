package br.gov.planejamento.api.core.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	
	private static Connection connection = null;
	
	private static String username = "postgres";
	private static String password = "DBDAdmin2005";
	
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			Properties connectionProps = new Properties();
			connectionProps.put("user", username);
			connectionProps.put("password", password);
			connection = DriverManager.getConnection("jdbc:postgresql://146.164.34.74:5432/prototipo_api", connectionProps);
		}
		
		return connection;
	}
}
