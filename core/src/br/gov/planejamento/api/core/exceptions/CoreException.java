package br.gov.planejamento.api.core.exceptions;

import br.gov.planejamento.api.core.constants.Constants;

public class CoreException extends ApiException {

	private static final long serialVersionUID = -2754316985104319081L;
	
	private static int defaultHttpStatusCode = Constants.HttpStatusCodes.INTERNAL_ERROR;
	
	public CoreException(int errorCode, String message, int httpStatusCode,
			Exception originalException) {
		super(errorCode, message, httpStatusCode, originalException);
	}
	
	public CoreException(int errorCode, String message, int httpStatusCode) {
		this(errorCode, message, httpStatusCode, null);
	}
	
	public CoreException(int errorCode, String message) {
		this(errorCode, message, defaultHttpStatusCode);
	}

	public CoreException(int errorCode, String message, Exception originalException) {
		this(errorCode, message, defaultHttpStatusCode, originalException);
	}	
}
