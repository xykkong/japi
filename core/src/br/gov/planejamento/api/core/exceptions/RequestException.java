package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Constants;

public class RequestException extends ApiException {

	private static final long serialVersionUID = -7476442124450087897L;
	
	private static int defaultHttpStatusCode = Constants.HttpStatusCodes.BAD_REQUEST;
	
	public RequestException(int errorCode, String message, int httpStatusCode,
			Exception originalException) {
		super(errorCode, message, httpStatusCode, originalException);
	}
	
	public RequestException(int errorCode, String message, int httpStatusCode) {
		this(errorCode, message, httpStatusCode, null);
	}
	
	public RequestException(int errorCode, String message) {
		this(errorCode, message, defaultHttpStatusCode);
	}

	public RequestException(int errorCode, String message, Exception originalException) {
		this(errorCode, message, defaultHttpStatusCode, originalException);
	}
}
