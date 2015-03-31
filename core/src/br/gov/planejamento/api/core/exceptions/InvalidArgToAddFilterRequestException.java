package br.gov.planejamento.api.core.exceptions;

public class InvalidArgToAddFilterRequestException extends RequestException {

	public InvalidArgToAddFilterRequestException(Class type) {
		super("Ao tentar adicionar um filtro, foi passada uma List<"+type.getName()+">.\n"
				+ "Para que este erro não ocorra, a lista deve ser ou de String ou de DatabaseAlias");
	}
	public InvalidArgToAddFilterRequestException() {
		super("Ao tentar adicionar um filtro, foi passada uma List de DatabaseAlias e de String, o que é ilegal.");
	}
}
