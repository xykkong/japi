package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.base.Value;

public class InvalidApiValueTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576767631094768626L;

	public InvalidApiValueTypeException(Value value) {
		super("O valor " + value + " já foi definido como do tipo: "
				+ value.getType());
	}
}
