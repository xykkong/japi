package br.gov.planejamento.api.core.exceptions;

import java.sql.PreparedStatement;

public class InvalidFilterValueTypeJapiException extends JapiException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4844806923271498604L;

	public InvalidFilterValueTypeJapiException(String value, int index,
			PreparedStatement pst, Class<? extends Object> type) {
		super("O valor " + value + " não pode ser convertido para o tipo "
				+ type.toString() + "\nPreparedStatement: " + pst.toString()
				+ "\níndice: " + index);
	}
}
