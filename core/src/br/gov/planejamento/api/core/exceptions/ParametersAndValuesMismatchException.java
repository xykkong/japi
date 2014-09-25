package br.gov.planejamento.api.core.exceptions;

public class ParametersAndValuesMismatchException extends Exception {

	private static final long serialVersionUID = 8139745739703419599L;

	public ParametersAndValuesMismatchException(int lengthParameters, int lengthValues){
		super("O número de parâmetros ("+lengthParameters+") é diferente do número de valores("+lengthValues+")");
	}
}
