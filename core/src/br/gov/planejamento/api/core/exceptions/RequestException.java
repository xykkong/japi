package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Constants;

public class RequestException extends ApiException {

private static String defaultHttpStatusCode = Constants.HttpStatusCodes.BAD_REQUEST;
	
	public RequestException(String message, String httpStatusCode,
			Exception originalException) {
		super(message, httpStatusCode, originalException);
	}
	
	public RequestException(String message, String httpStatusCode) {
		this(message, httpStatusCode, null);
	}
	
	public RequestException(String message) {
		this(message, defaultHttpStatusCode);
	}

	public RequestException(String message, Exception originalException) {
		this(message, defaultHttpStatusCode, originalException);
	}
}
