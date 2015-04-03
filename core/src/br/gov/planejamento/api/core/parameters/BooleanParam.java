package br.gov.planejamento.api.core.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

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
	public void setPreparedStatementValue(int i, PreparedStatement pst) throws ApiException {
		System.out.println("IT'S ALIVE");
		try {
			pst.setBoolean(i, Boolean.parseBoolean(getValue()));
		} catch (SQLException e) {
			throw new CoreException("Houve um erro ao processar o parâmetro recebido.", e);
		}
	}
}
