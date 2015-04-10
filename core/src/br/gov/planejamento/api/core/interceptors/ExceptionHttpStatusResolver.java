package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;

@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		//Se uma exception sobe sem estar encapsulada em uma ApiException, ela é encapsulada em uma CoreException
		if(!(exception instanceof ApiException)) {
			exception = new CoreException("Houve um erro interno desconhecido.", exception);
		}
		
		//Convertendo para ApiException
		ApiException apiException = (ApiException) exception;
		
		//Printando stackstrace
		System.out.println("-----------------------------------------------------------------");
		System.out.println("API EXCEPTION:");
		apiException.printStackTrace();
		if(apiException.getOriginalException() != null) {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("ORIGINAL EXCEPTION:");
			apiException.getOriginalException().printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------");
		
		ErrorResponse errorResponse = new ErrorResponse(apiException);
		
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setEntity(errorResponse);
		
		return ServerResponseBuilder.build(serverResponse, errorResponse);
	}	
}
