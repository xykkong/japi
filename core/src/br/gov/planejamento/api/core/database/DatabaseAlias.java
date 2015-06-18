package br.gov.planejamento.api.core.database;

import br.gov.planejamento.api.core.utils.StringUtils;


public class DatabaseAlias  {
	private String dbName;
	private String uriName;
	private boolean isEscaped = false;
	private boolean hasTableAlias = false;
	
	public boolean hasTableAlias(){
		return hasTableAlias;
	}
	
	public void setHasTableAlias(boolean hasTableAlias) {
		this.hasTableAlias = hasTableAlias;
	}
	
	public boolean isEscaped() {
		return isEscaped;
	}
	
	public DatabaseAlias(){
		
	}
	public DatabaseAlias(String defaultName){
		this.dbName = this.uriName = defaultName;
	}
	public DatabaseAlias(String dbName,String uriName){
		this.dbName = dbName;
		this.uriName = uriName;
	}
	
	public String getEscapedDbName(){
		return StringUtils.escapeDB(dbName);
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
	
	private static DatabaseAlias fromSpecialStringWithoutEscape(String special){
		DatabaseAlias alias = null;
		String[] explodedString = special.toUpperCase().split(" AS ");
		if(explodedString.length==1)
			alias = new DatabaseAlias(special.trim());
		else if(explodedString.length==2)
			alias = new DatabaseAlias(explodedString[0].trim(), explodedString[1].trim());
		else
			throwErrorFromSpecialString(special);
		return alias;
	}

	private static void throwErrorFromSpecialString(String special) {
		throw new IllegalArgumentException("Para criar um databaseAlias é esperado um parâmetro,"
				+ " uma string <\"dbName\" as uriName> ou ainda uma string <\"dbName\" as uriName>,"
				+ " encontrado "+special);
	}
	
	public static DatabaseAlias fromSpecialString(String special){
		if(!special.contains("\""))
			return fromSpecialStringWithoutEscape(special);
		String[] explodedEscapeString = special.split("\"");
		if(explodedEscapeString.length < 3)
			throwErrorFromSpecialString(special);
		String dbName = explodedEscapeString[1];
		String[] explodedASString = explodedEscapeString[2].toLowerCase().split(" as ");
		if(explodedASString.length < 2)
			throwErrorFromSpecialString(special);
		String uriName = explodedASString[1];
		DatabaseAlias d = new DatabaseAlias(dbName, uriName);
		d.isEscaped = true;
		return d;
	}
}
