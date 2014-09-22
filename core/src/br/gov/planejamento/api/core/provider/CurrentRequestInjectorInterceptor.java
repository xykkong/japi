package br.gov.planejamento.api.core.provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
public class CurrentRequestInjectorInterceptor implements PreProcessInterceptor {

	@Override
	public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod method) throws Failure, WebApplicationException {
		return null;
	}
	
}
