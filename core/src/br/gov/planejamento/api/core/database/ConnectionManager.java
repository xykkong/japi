package br.gov.planejamento.api.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.gov.planejamento.api.core.base.JapiConfigLoader.JapiConfig;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public class ConnectionManager {

	private static Connection connection = null;
	private static JapiConfig.DatabaseProperties dbProperties;
	
	public JapiConfig.DatabaseProperties getDbProperties() {
		return dbProperties;
	}

	public static void setDbProperties(JapiConfig.DatabaseProperties dbProperties) {
		ConnectionManager.dbProperties = dbProperties;
	}

	public static Connection getConnection() throws ApiException {
		if (connection == null){
			Properties connectionProps = new Properties();
			connectionProps.put("user", dbProperties.getUser());
			connectionProps.put("password", dbProperties.getPassword());
	
			String connectionString = "jdbc:postgresql://" + dbProperties.getUrl() + ":"
					+ dbProperties.getPort() + "/" + dbProperties.getDatabaseName();
			try {
				connection = DriverManager.getConnection(connectionString,
						connectionProps);
			} catch (SQLException e) {
				throw new CoreException("Erro ao conectar com a base de dados. Verifique se os dados de conexão informados na configuração da API estão corretos e se é possível estabelecer uma conexão com o banco a partir deste servidor.", e);
			}
		}
		return connection;
	}
}
