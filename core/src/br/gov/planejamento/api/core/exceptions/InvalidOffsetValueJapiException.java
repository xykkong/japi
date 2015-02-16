package br.gov.planejamento.api.core.exceptions;


public class InvalidOffsetValueJapiException extends JapiException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -410323160828204057L;

	public InvalidOffsetValueJapiException(String offset) {
		super("Valor do offset'"+ offset +"' deve ser um número inteiro");
		setUserShowable(true);
	}
}
