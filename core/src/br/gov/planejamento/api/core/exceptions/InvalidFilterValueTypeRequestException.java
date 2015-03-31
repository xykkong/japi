package br.gov.planejamento.api.core.exceptions;

import java.sql.PreparedStatement;

public class InvalidFilterValueTypeRequestException extends CoreException {

	public InvalidFilterValueTypeRequestException(String value, int index,
			PreparedStatement pst, Class<? extends Object> type) {
		super("O valor " + value + " não pode ser convertido para o tipo "
				+ type.toString());
	}
}
