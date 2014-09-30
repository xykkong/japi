package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.base.Value;

public class InvalidApiValueTypeException extends Exception {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = 6434424579413039673L;

	public InvalidApiValueTypeException(Value value){
		super("O valor "+ value + " já foi definido como do tipo: "+value.getType());
	}
}
