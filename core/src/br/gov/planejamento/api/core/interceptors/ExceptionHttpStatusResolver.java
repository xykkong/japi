package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;

/**
 * Este interceptor de Exceptions só é ativado caso uma Exceção seja lançada sem o controle
 * da framework, ou seja, geralmente uma exception lançada pelo próprio Resteasy.
 * 
 * Um exemplo, é quando tenta-se acessar um método da Request que não existe.
 * 
 * Um problema nesse exemplo é que, como o Resteasy lança a exceção antes mesmo do PreProcess ser chamado,
 * ainda não se tem dados preenchidos na RequestContext, inviabilizando a serialização no formato solicitado
 * e retornando sempre no formato da última solicitação processada, já que o RequestContext não é zerado pelo
 * PreProcess e repopulado.
 */
@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		
		ApiException apiException;
		if(!(exception instanceof ApiException)) {
			apiException = new CoreException("Houve um erro interno desconhecido.", exception);
		} else {
			apiException = (ApiException) exception;
		}
		
		ErrorResponse error = new ErrorResponse(apiException);
		return ServerResponseBuilder.build(new ServerResponse(), error);
	}	
}
