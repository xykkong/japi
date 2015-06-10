package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Errors;


public class InvalidOffsetValueRequestException extends RequestException {

	private static final long serialVersionUID = -4490218474943240324L;

	public InvalidOffsetValueRequestException(String offset) {
		super(Errors.OFFSET_INVALIDO, "Valor do offset '"+ offset +"' deve ser um número inteiro");
	}
}
