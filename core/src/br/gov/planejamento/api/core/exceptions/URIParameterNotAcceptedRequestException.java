package br.gov.planejamento.api.core.exceptions;

public class URIParameterNotAcceptedRequestException extends RequestException {

	
	public URIParameterNotAcceptedRequestException(String foundParameter){
		super("O parametro "+foundParameter+" nao eh aceito nesta URI");
	}

}
