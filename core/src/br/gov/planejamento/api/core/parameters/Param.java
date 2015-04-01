package br.gov.planejamento.api.core.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Param {
	protected String original;
	protected String value;
	
	public abstract void parse();
	
	public Param(String original){
		this.original = original;
		parse();
	}
	
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getValue() {
		return value;
	}

	public void setPreparedStatementValue(int i, PreparedStatement pst) throws SQLException {
		pst.setString(i, getValue());
	}
}
