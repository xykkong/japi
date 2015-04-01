package br.gov.planejamento.api.core.interceptors;

import javax.ws.rs.ext.Provider;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Returns;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.exceptions.JapiException;

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
		if (!firstPathSegment.equals("docs")) // TODO: Mudar forma de identificar docs
			try {
				if(serverResponse.getEntity() instanceof String) return;
				Response response = (Response) serverResponse.getEntity();
				serverResponse.setGenericType(String.class);
				response.isList(serverResponse.getResourceMethod().getAnnotation(Returns.class).isList());
				response.setDescription(serverResponse.getResourceMethod().getAnnotation(About.class).description());
				
				Headers<Object> headers = new Headers<Object>();
				
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
			} catch (JapiException japiException) {
				serverResponse.setEntity(japiException);
				showErrorMessage(serverResponse);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception ex) {
				// TODO tratamento de erro
				serverResponse.setEntity(ex);
				showErrorMessage(serverResponse);
			}

	}

	private static void showErrorMessage(ServerResponse serverResponse) {
		Object responseEntity = serverResponse.getEntity();
		String errorMessage = "";
		if (responseEntity instanceof JapiException) {
			JapiException japiException = (JapiException) responseEntity;
			if (japiException.isUserShowable()) {
				errorMessage = japiException.getMessage();

			} else {
				System.out.println("JapiException ocurred, stackTrace:");
				japiException.printStackTrace();
			}
		} else {
			// TODO mensagem de erro genérica
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
