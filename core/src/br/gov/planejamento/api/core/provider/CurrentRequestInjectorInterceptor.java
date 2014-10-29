package br.gov.planejamento.api.core.provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.gov.planejamento.api.core.base.Session;

@Provider
@ServerInterceptor
public class CurrentRequestInjectorInterceptor implements
		PreProcessInterceptor, PostProcessInterceptor {

	@Override
	public ServerResponse preProcess(HttpRequest httpRequest,
			ResourceMethod method) throws Failure, WebApplicationException {
		Session.getCurrentSession().clear();
		Session.getCurrentSession().putValues(
				httpRequest.getUri().getQueryParameters());
		return null;
	}

	@Override
	public void postProcess(ServerResponse response) {
		String res;
		/*
		 * response.setGenericType(String.class); response.setEntity("banana");
		 */
		res = (String) response.getEntity();
		response.setEntity("<!DOCTYPE html>"
				+ "<html>"
					+ "<head>"
						+ "<meta charset='utf-8'/>"
					+ "</head>"
					+ "<body>"
						+res
					+ "</body>"
				+ "</html>");
	}

}
