package br.gov.planejamento.api.core.exceptions;

public class InvalidArgToAddFilterJapiException extends JapiException {

	public InvalidArgToAddFilterJapiException(Class type) {
		super("Ao tentar adicionar um filtro, foi passada uma List<"+type.getName()+">.\n"
				+ "Para que este erro não ocorra, a Lista deve ser ou de String ou de DatabaseAlias");
	}
	public InvalidArgToAddFilterJapiException() {
		super("Ao tentar adicionar um filtro, foi passada tanto List de DatabaseAlias quanto de String, o que é ilegal.");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9138182323853377066L;

}
