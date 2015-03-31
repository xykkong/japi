package br.gov.planejamento.api.core.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooleanParam extends Param{
	
	public BooleanParam(String original) {
		super(original);
	}
	

	@Override
	public void parse(){
		switch(original){
			case "v":
			case "t":
			case "true":
			case "verdadeiro":
			case "sim":
			case "1":
				this.value="true";
				break;
			case "f":
			case "false":
			case "falso":
			case "nao":
			case "0":
			default:
				this.value="false";
				break;
		}
	}
	
	@Override
	public void setPreparedStatementValue(int i, PreparedStatement pst) throws SQLException {
		pst.setBoolean(i, Boolean.parseBoolean(getValue()));
	}
}
