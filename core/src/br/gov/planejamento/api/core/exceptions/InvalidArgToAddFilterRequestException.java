package br.gov.planejamento.api.core.exceptions;

public class InvalidArgToAddFilterRequestException extends RequestException {

	private static final long serialVersionUID = 6797261700966528914L;
	
	public InvalidArgToAddFilterRequestException(Class<? extends Object> type) {
		super("Ao tentar adicionar um filtro, foi passada uma List<"+type.getName()+">.\n"
				+ "Para que este erro não ocorra, a Lista deve ser ou de String ou de DatabaseAlias");
	}
	public InvalidArgToAddFilterRequestException() {
		super("Ao tentar adicionar um filtro, foi passada tanto List de DatabaseAlias quanto de String, o que é ilegal.");
	}
}
