package br.gov.planejamento.api.core.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ConnectionManager {

	private static Connection connection = null;
	
	/**
	 * Este método deve ser private, está como public para podermos fazer testes,
	 * veja mais nas classes de service do módulo de licitações 
	 * 
	 * @param filename
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void loadConfiguration(String filename)
			throws ParserConfigurationException, SAXException, IOException,
			SQLException {
		DatabasePropertiesFileLoader loader = DatabasePropertiesFileLoader
				.getInstance(filename);
		Properties connectionProps = new Properties();
		connectionProps.put("user", loader.getUser());
		connectionProps.put("password", loader.getPassword());

		String connectionString = "jdbc:postgresql://" + loader.getUrl() + ":"
				+ loader.getPort() + "/" + loader.getDatabaseName();
		connection = DriverManager.getConnection(connectionString,
				connectionProps);
	}
	
	/**
	 * Este método n�o deveria existir, da mesma maneira que o m�todo acima deveria ser private 
	 */
	public static void removeConfiguration(){
		connection = null;
	}

	public static Connection getConnection() throws SQLException,
			ParserConfigurationException, SAXException, IOException {
		if (connection == null)
			loadConfiguration("database-properties");
		return connection;
	}
}
