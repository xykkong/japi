package br.gov.planejamento.api.core.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import br.gov.planejamento.api.core.constants.Constants;


public class JapiConfigLoader {
	
	private static JapiConfig japiConfig;
	
	public static JapiConfig getJapiConfig() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		if(japiConfig==null)
			japiConfig = loadJapiConfigFile();
		return japiConfig;
	}
	
	private static JapiConfig loadJapiConfigFile() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		String fileName = System.getProperty("jboss.server.config.dir") +"/"+ Constants.Configuration.CONFIG_FILE_NAME;
		File f = new File(fileName);
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(f), JapiConfig.class);
	}

	public class JapiConfig {
		private DatabaseProperties databaseProperties;
		private String assetFolder; 
		private String htmlTemplate;
		private String docsHtmlFolder;
		
		public String getAssetFolder() {
			return assetFolder;
		}
		public void setAssetFolder(String assetFolder) {
			this.assetFolder = assetFolder;
		}
		public String getDocsHtmlFolder() {
			return docsHtmlFolder;
		}
		public void setDocsHtmlFolder(String docsFolder) {
			this.docsHtmlFolder = docsFolder;
		}
		public String getHtmlTemplate() {
			return htmlTemplate;
		}
		public void setHtmlTemplate(String htmlTemplate) {
			this.htmlTemplate = htmlTemplate;
		}
		public class DatabaseProperties{
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getPort() {
				return port;
			}
			public void setPort(String port) {
				this.port = port;
			}
			public String getDatabaseName() {
				return databaseName;
			}
			public void setDatabaseName(String databaseName) {
				this.databaseName = databaseName;
			}
			private String url;
			private String port;
			private String databaseName;
			private String user;
			private String password;
			public String getUser() {
				return user;
			}
			public void setUser(String user) {
				this.user = user;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
		}
		public DatabaseProperties getDatabaseProperties() {
			return databaseProperties;
		}
		public void setDatabaseProperties(DatabaseProperties databaseProperties) {
			this.databaseProperties = databaseProperties;
		}
	}
}
