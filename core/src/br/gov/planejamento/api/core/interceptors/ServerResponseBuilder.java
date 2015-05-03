package br.gov.planejamento.api.core.interceptors;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.DocumentationResponse;
import br.gov.planejamento.api.core.responses.SwaggerResponse;

public class ServerResponseBuilder {
	public static ServerResponse build(ServerResponse serverResponse, Response response) {
		serverResponse.setGenericType(String.class);
		serverResponse.setStatus(response.getHttpStatusCode());
		
		try {
			response.setName(serverResponse.getResourceMethod().getAnnotation(About.class).name());
			response.setDescription(serverResponse.getResourceMethod().getAnnotation(About.class).description());
		} catch(Exception e) {
			//O serverResponse.getResourceMethod() pode retornar nulo, implicando em uma NullPointerException
			//TODO: Implementar verificação para evitar esse try/catch
		}
		
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
			
			serverResponse.setMetadata(headers);
		} catch (ApiException e) {
			serverResponse.setEntity("Houve um erro no processo de serialização dos dados.");
			serverResponse.setStatus(Constants.HttpStatusCodes.INTERNAL_ERROR);
			e.printStackTrace();
		}
		
		return serverResponse;
	}
}
