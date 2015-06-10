package br.gov.planejamento.api.core.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

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

	public void setPreparedStatementValue(int i, PreparedStatement pst) throws ApiException {
		try {
			pst.setString(i, getValue());
		} catch (SQLException e) {
			throw new CoreException(Errors.PARAM_ERRO_PROCESSAMENTO, "Houve um erro ao processar o parâmetro recebido.", e);
		}
	}
}
