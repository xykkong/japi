package br.gov.planejamento.api.core.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ConnectionManager {

	private static Connection connection = null;


	public static Connection getConnection() throws SQLException,
			ParserConfigurationException, SAXException, IOException {
		if (connection == null) {
			DatabasePropertiesFileLoader loader = DatabasePropertiesFileLoader
					.getInstance("database-properties");
			Properties connectionProps = new Properties();
			connectionProps.put("user", loader.getUser());
			connectionProps.put("password", loader.getPassword());

			String connectionString = "jdbc:postgresql://" + loader.getUrl()
					+ ":" + loader.getPort() + "/" + loader.getDatabaseName();
			connection = DriverManager.getConnection(connectionString,
					connectionProps);
		}

		return connection;
	}
}
