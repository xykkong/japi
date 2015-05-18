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
	
	public static DatabaseAlias fromSpecialString(String special){
		DatabaseAlias alias = null;
		String[] explodedString = special.toUpperCase().split(" AS ");
		if(explodedString.length==1)
			alias = new DatabaseAlias(special.trim());
		else if(explodedString.length==2)
			alias = new DatabaseAlias(explodedString[0].trim(), explodedString[1].trim());
		else
			throw new IllegalArgumentException("Para criar um databaseAlias é esperado um parâmetro ou uma string 'dbName as uriName', encontrado "+special);
		return alias;
	}
}
