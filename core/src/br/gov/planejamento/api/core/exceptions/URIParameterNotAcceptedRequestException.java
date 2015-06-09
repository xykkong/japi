package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Errors;

public class URIParameterNotAcceptedRequestException extends RequestException {

	private static final long serialVersionUID = -7774631043125072450L;

	public URIParameterNotAcceptedRequestException(String foundParameter){
		super(Errors.PARAMETRO_INVALIDO, "O parâmetro "+foundParameter+" não é aceito nesta URI");
	}

}
