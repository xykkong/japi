package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Constants;

public class CoreException extends ApiException {

	private static final long serialVersionUID = -2754316985104319081L;
	
	private static int defaultHttpStatusCode = Constants.HttpStatusCodes.INTERNAL_ERROR;
	
	public CoreException(String message, int httpStatusCode,
			Exception originalException) {
		super(message, httpStatusCode, originalException);
	}
	
	public CoreException(String message, int httpStatusCode) {
		this(message, httpStatusCode, null);
	}
	
	public CoreException(String message) {
		this(message, defaultHttpStatusCode);
	}

	public CoreException(String message, Exception originalException) {
		this(message, defaultHttpStatusCode, originalException);
	}	
}
