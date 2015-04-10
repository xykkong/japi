package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import br.gov.planejamento.api.core.base.JapiConfigLoader;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;

@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		//TODO: Pensar se existe uma forma melhor de trabalhar isso. É necessário, para carregar os resources no 
		//template de erro, que eu saiba a rootURL, mas ela é setada apenas no pre-process e não chega até lá quando
		//o erro ocorre.
		try {
			RequestContext.getContext().setRootURL(JapiConfigLoader.getJapiConfig().getRootUrl());
		} catch (ApiException e) {
			//TODO: redirecionar para método que retorne um erro.
			//OBS: como aqui não é possível lançar exceção e subir pro postprocess
			//o jeito é redirecionar para uma página de erro
			//Talvez seja interessante o próprio postprocess também fazer isso.
			return new ServerResponse(e, 400, new Headers<Object>());
		}
		
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
