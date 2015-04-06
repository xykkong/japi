package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Returns;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.RequestException;

@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor {

	@Override
	public void postProcess(ServerResponse serverResponse) {
		if (serverResponse.getStatus() != 200) {
			showErrorMessage(serverResponse);
			return;
		}

		String firstPathSegment = RequestContext.getContext().getPath()
				.split("/")[1];
		if ( RequestContext.getContext().getPath().contains("docs")){ // TODO: Mudar forma de identificar docs
			if(serverResponse.getEntity() instanceof String){
				System.out.println("oi");
				return;
			}
			else System.out.println("oi2");
		}else System.out.println("oi3 "+firstPathSegment);
		
		System.out.println("wut wut "+serverResponse.getEntity());
		Response response = (Response) serverResponse.getEntity();
		serverResponse.setGenericType(String.class);
		
		response.isList(serverResponse.getResourceMethod().getAnnotation(Returns.class).isList());
		response.setDescription(serverResponse.getResourceMethod().getAnnotation(About.class).description());
		
		Headers<Object> headers = new Headers<Object>();
		
		try {
			switch (RequestContext.getContext().getRequestFormat()) {
			case RequestFormats.HTML:
				serverResponse.setEntity(response.toHTML());
				break;
			case RequestFormats.JSON:
				headers.add("Content-Type", "text/json");
				String json = response.toJSON();
				serverResponse.setEntity(json);
				break;
			case RequestFormats.XML:
				headers.add("Content-Type", "text/xml");
				String xml = response.toXML();
				serverResponse.setEntity(xml);
				break;
			case RequestFormats.CSV:
				headers.add("Content-Type", "text/csv");
				headers.add("Content-Disposition",
						"attachment; filename=\"result.csv\"");
				serverResponse.setEntity(response.toCSV());
				break;
			}
		} catch (ApiException e) {
			showErrorMessage(serverResponse);
		}
		
		serverResponse.setMetadata(headers);
	}

	private static void showErrorMessage(ServerResponse serverResponse) {
		Object responseEntity = serverResponse.getEntity();
		String errorMessage = "";
		if (responseEntity instanceof RequestException) {
			RequestException japiException = (RequestException) responseEntity;
			errorMessage = japiException.getMessage();
		} else if(responseEntity instanceof CoreException) {
			errorMessage = "Erro interno desconhecido";
			serverResponse.setStatus(500);
			if(responseEntity instanceof Exception){
				((Exception) responseEntity).printStackTrace();
			}
		} else {
			errorMessage = "Erro interno desconhecido";
			serverResponse.setStatus(500);
			if(responseEntity instanceof Exception){
				((Exception) responseEntity).printStackTrace();
			}
		}
		showErrorMessageByRequestFormat(serverResponse, errorMessage);
	}

	private static void showErrorMessageByRequestFormat(ServerResponse serverResponse,
			String errorMessage) {
		switch (RequestContext.getContext().getRequestFormat()) {
		// TODO tratamento de erro pegando template html, formatando json etc
		}
		serverResponse.setEntity(errorMessage);
	}
	
}
