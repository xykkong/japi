package br.gov.planejamento.api.core.exceptions;


public class InvalidOffsetValueRequestException extends RequestException {

	public InvalidOffsetValueRequestException(String offset) {
		super("Valor do offset '"+ offset +"' deve ser um número inteiro");
	}
}
