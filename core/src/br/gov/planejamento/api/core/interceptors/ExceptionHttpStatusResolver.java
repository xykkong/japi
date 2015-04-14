package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;

import org.jboss.resteasy.core.Headers;

import br.gov.planejamento.api.core.base.JapiConfigLoader;
import br.gov.planejamento.api.core.base.RequestContext;
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
		if(!(exception instanceof ApiException)) {
			apiException = new CoreException("Houve um erro interno desconhecido.", exception);
		} else {
			apiException = (ApiException) exception;
		}
		
		ErrorResponse error = new ErrorResponse(apiException);
		return ServerResponseBuilder.build(new ServerResponse(), error);
	}	
}
