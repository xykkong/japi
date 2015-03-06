package br.gov.planejamento.api.core.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.gov.planejamento.api.core.base.JapiConfigLoader;
import br.gov.planejamento.api.core.base.JapiConfigLoader.JapiConfig;
import br.gov.planejamento.api.core.exceptions.JapiException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ConnectionManager {

	private static Connection connection = null;
	
	public static Connection getConnection() throws JapiException, JsonSyntaxException, JsonIOException, FileNotFoundException, SQLException {
		if (connection == null){
			JapiConfig.DatabaseProperties dbProperties = JapiConfigLoader.getJapiConfig().getDatabaseProperties();
			Properties connectionProps = new Properties();
			connectionProps.put("user", dbProperties.getUser());
			connectionProps.put("password", dbProperties.getPassword());
	
			String connectionString = "jdbc:postgresql://" + dbProperties.getUrl() + ":"
					+ dbProperties.getPort() + "/" + dbProperties.getDatabaseName();
			connection = DriverManager.getConnection(connectionString,
					connectionProps);
		}
		return connection;
	}
}
