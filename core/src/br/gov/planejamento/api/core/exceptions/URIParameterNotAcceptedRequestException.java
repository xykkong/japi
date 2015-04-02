package br.gov.planejamento.api.core.exceptions;

public class URIParameterNotAcceptedRequestException extends RequestException {

	private static final long serialVersionUID = -7774631043125072450L;

	public URIParameterNotAcceptedRequestException(String foundParameter){
		super("O parametro "+foundParameter+" nao eh aceito nesta URI");
	}

}
