package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Constants;

public class RequestException extends ApiException {

	private static final long serialVersionUID = -7476442124450087897L;
	
	private static int defaultHttpStatusCode = Constants.HttpStatusCodes.BAD_REQUEST;
	
	public RequestException(String message, int httpStatusCode,
			Exception originalException) {
		super(message, httpStatusCode, originalException);
	}
	
	public RequestException(String message, int httpStatusCode) {
		this(message, httpStatusCode, null);
	}
	
	public RequestException(String message) {
		this(message, defaultHttpStatusCode);
	}

	public RequestException(String message, Exception originalException) {
		this(message, defaultHttpStatusCode, originalException);
	}
}
