package br.gov.planejamento.api.core.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.gov.planejamento.api.core.annotations.CustomParam;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

@CustomParam(name="Boolean", description = "Aceita como true: v, t, true, verdadeiro, sim, yes, 1. Aceita como false: f, false, falso, nao, no, 0.")
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
			case "yes":
			case "1":
				this.value="true";
				break;
			case "f":
			case "false":
			case "falso":
			case "nao":
			case "no":
			case "0":
			default:
				this.value="false";
				break;
		}
	}
	
	@Override
	public void setPreparedStatementValue(int i, PreparedStatement pst) throws ApiException {
		try {
			pst.setBoolean(i, Boolean.parseBoolean(getValue()));
		} catch (SQLException e) {
			throw new CoreException(Errors.BOOLEAN_PARAM_ERRO_PROCESSAMENTO, "Houve um erro ao processar o parâmetro recebido.", e);
		}
	}
}
