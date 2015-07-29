package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.responses.ErrorResponse;

@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor {

	@Override
	public void postProcess(ServerResponse serverResponse) {
		
		if(serverResponse.getEntity() == null) {
			serverResponse.setEntity(new CoreException(Errors.POST_PROCESS_NULL_RESPONSE, "A Response retornada pela camada de Request é nula. Verifique o factory da sua Response."));
		}
		
		if(serverResponse.getEntity() instanceof ApiException) {
			if(((ApiException)serverResponse.getEntity()).getOriginalException() instanceof ApiException) {
				serverResponse.setEntity(new CoreException(Errors.API_EXCEPTION_COMO_ORIGINAL_EXCEPTION, "Foi lançada uma ApiException cuja causa de origem era uma outra ApiException. Isso não é permitido, uma ApiException não deve ser encapsulada por outra ApiException.", (ApiException)serverResponse.getEntity()));
			}
		}
		
		if(serverResponse.getEntity() instanceof Exception) {
			if(!(serverResponse.getEntity() instanceof ApiException)) {
				serverResponse.setEntity(new CoreException(Errors.POST_PROCESS_ERRO_DESCONHECIDO, "Houve um erro interno desconhecido.", (Exception) serverResponse.getEntity()));
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
