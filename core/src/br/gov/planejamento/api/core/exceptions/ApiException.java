package br.gov.planejamento.api.core.exceptions;

public class ApiException extends Exception {
	
	private static final long serialVersionUID = 7744204513648250685L;
	
	private int httpStatusCode;
	private String message;
	private Exception originalException;
	
	public ApiException(String message, int httpStatusCode, Exception originalException) {
		if(message!=null) this.message = message;
		if(httpStatusCode!=0) this.httpStatusCode = httpStatusCode;
		if(originalException!=null) this.originalException = originalException;
		if(originalException!=null)
			originalException.printStackTrace();
	}
	
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
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
