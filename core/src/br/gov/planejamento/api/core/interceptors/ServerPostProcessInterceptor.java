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

@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor {

	@Override
	public void postProcess(ServerResponse serverResponse) {
		Response response = (Response) serverResponse.getEntity();
		serverResponse = ServerResponseBuilder.build(serverResponse, response);
	}
}
