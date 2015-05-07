package br.gov.planejamento.api.core.database;

public class DatabaseAlias  {
	private String dbName;
	private String uriName;
	
	public DatabaseAlias(){
		
	}
	public DatabaseAlias(String defaultName){
		this.dbName = this.uriName = defaultName.toUpperCase();
	}
	public DatabaseAlias(String dbName,String uriName){
		this.dbName = dbName.toUpperCase();
		this.uriName = uriName.toUpperCase();
	}
	public String getDbName() {
		return dbName;
	}
	public String getUriName() {
		return uriName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
