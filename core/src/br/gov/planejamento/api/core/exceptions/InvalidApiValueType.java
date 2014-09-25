package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.base.Value;

public class InvalidApiValueType extends Exception {

	/**
	 * Generated 
	 */
	private static final long serialVersionUID = 6434424579413039673L;

	public InvalidApiValueType(Value value){
		super("O valor "+ value + " já foi definido como do tipo: "+value.getType());
	}
}
