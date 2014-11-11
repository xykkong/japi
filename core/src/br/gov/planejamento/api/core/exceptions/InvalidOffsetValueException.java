package br.gov.planejamento.api.core.exceptions;


public class InvalidOffsetValueException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -410323160828204057L;

	public InvalidOffsetValueException(String offset) {
		super("Valor do offset'"+ offset +"' deve ser um número inteiro");
	}
}
