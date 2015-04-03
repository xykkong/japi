package br.gov.planejamento.api.core.exceptions;

import java.sql.PreparedStatement;

public class InvalidFilterValueTypeRequestException extends CoreException {

	private static final long serialVersionUID = -6829573212921999842L;

	public InvalidFilterValueTypeRequestException(String value, int index,
			PreparedStatement pst, Class<? extends Object> type) {
		super("O valor " + value + " não pode ser convertido para o tipo "
				+ type.toString());
	}
}
