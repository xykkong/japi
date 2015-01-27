package br.gov.planejamento.api.core.database;

public class DatabaseAlias  {
	private String dbName;
	private String uriName;
	
	public DatabaseAlias(){
		
	}
	public DatabaseAlias(String defaultName){
		this.dbName = this.uriName = defaultName;
	}
	public DatabaseAlias(String dbName,String uriName){
		this.dbName = dbName;
		this.uriName = uriName;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUriName() {
		return uriName;
	}
	public void setUriName(String uriName) {
		this.uriName = uriName;
	}
}
