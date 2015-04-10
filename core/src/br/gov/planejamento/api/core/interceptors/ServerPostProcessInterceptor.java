package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.ResponseNotRequired;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.responses.ErrorResponse;

@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor {

	@Override
	public void postProcess(ServerResponse serverResponse) {
		
		if(serverResponse.getEntity() instanceof Exception) {
			if(!(serverResponse.getEntity() instanceof ApiException)) {
				serverResponse.setEntity(new CoreException("Houve um erro interno desconhecido.", (Exception) serverResponse.getEntity()));
			}
			
			//Convertendo para ApiException
			ApiException apiException = (ApiException) serverResponse.getEntity();
			
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
			serverResponse = ServerResponseBuilder.build(serverResponse, errorResponse);
		} else {
			Response response = (Response) serverResponse.getEntity();
			serverResponse = ServerResponseBuilder.build(serverResponse, response);
		}
	}
}
