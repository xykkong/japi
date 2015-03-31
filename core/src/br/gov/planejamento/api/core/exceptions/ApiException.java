package br.gov.planejamento.api.core.exceptions;

public class ApiException extends Exception {
	
	private String httpStatusCode;
	private String message;
	private Exception originalException;
	
	public ApiException(String message, String httpStatusCode, Exception originalException) {
		if(message!=null) this.message = message;
		if(httpStatusCode!=null) this.httpStatusCode = httpStatusCode;
		if(originalException!=null) this.originalException = originalException;
	}
	
	public String getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getOriginalException() {
		return originalException;
	}
	public void setOriginalException(Exception originalException) {
		this.originalException = originalException;
	}
	
	
}
