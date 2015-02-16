package br.gov.planejamento.api.core.interceptors;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.ext.Provider;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
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

		String firstPathSegment = Session.getCurrentSession().getPath()
				.split("/")[1];
		if (!firstPathSegment.equals("docs")) // TODO: Mudar forma de identificar docs
			try {
				Response response = (Response) serverResponse.getEntity();
				serverResponse.setGenericType(String.class);
				ArrayList<HashMap<String, Property>> resourceMapList = new ArrayList<HashMap<String, Property>>();

				switch (Session.getCurrentSession().getRequestFormat()) {
				case RequestFormats.HTML:
					serverResponse.setEntity(response.toHTML());
					break;
				case RequestFormats.JSON:
					String json = response.toJSON();
					serverResponse.setEntity(json);
					break;
				case RequestFormats.XML:
					serverResponse.setEntity("banana");
					break;
				case RequestFormats.CSV:
					Headers headers = new Headers();
					headers.add("Content-Type", "text/csv");
					headers.add("Content-Disposition",
							"attachment; filename=\"result.csv\"");
					serverResponse.setMetadata(headers);
					serverResponse.setEntity(response.toCSV());
					break;
				}
			} catch (JapiException japiException) {
				serverResponse.setEntity(japiException);
				showErrorMessage(serverResponse);
			} catch (Exception ex) {
				// TODO tratamento de erro
				serverResponse.setEntity(ex);
				showErrorMessage(serverResponse);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		switch (Session.getCurrentSession().getRequestFormat()) {
		// TODO tratamento de erro pegando template html, formatando json etc
		}
		serverResponse.setEntity(errorMessage);
	}
}
