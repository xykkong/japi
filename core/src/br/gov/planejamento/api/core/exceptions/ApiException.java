package br.gov.planejamento.api.core.exceptions;

public class ApiException extends Exception {
	
	private static final long serialVersionUID = 7744204513648250685L;
	
	private int errorCode;
	private int httpStatusCode;
	private String message;
	private Exception originalException;
	
	public ApiException(int errorCode, String message, int httpStatusCode, Exception originalException) {
		this.errorCode = errorCode;
		if(message!=null) this.message = message;
		if(httpStatusCode!=0) this.httpStatusCode = httpStatusCode;
		if(originalException!=null) this.originalException = originalException;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public String getMessage() {
		return "Erro " + errorCode + " - " + message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPublicMessage() {
		String retorno = "Erro " + errorCode;
		if(this instanceof RequestException) {
			retorno += " - " + message;
		}
		return retorno;
	}
	public Exception getOriginalException() {
		return originalException;
	}
	public void setOriginalException(Exception originalException) {
		this.originalException = originalException;
	}
	
	
}
