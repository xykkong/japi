package br.gov.planejamento.api.core.interceptors;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.utils.StringUtils;

@Provider
@ServerInterceptor
public class ServerPreProcessInterceptor implements PreProcessInterceptor {
	
	@Override
	public ServerResponse preProcess(HttpRequest httpRequest,
			ResourceMethod method) throws Failure, WebApplicationException {	
		
		Session.getCurrentSession().clear();
		Session.getCurrentSession().putValues(httpRequest.getUri().getQueryParameters());
		
		List<PathSegment> pathSegments = httpRequest.getUri().getPathSegments();
		String requestFormat = StringUtils.lastSplitOf(pathSegments.get(pathSegments.size()-1).getPath(), "\\.").toLowerCase();
		
		if(requestFormat == "") {
			requestFormat = Constants.RequestFormats.HTML;
		}
		
		Session.getCurrentSession().setRequestFormat(requestFormat);
		String path = httpRequest.getUri().getAbsolutePath().getPath();
		path = FilenameUtils.removeExtension(path);
		Session.getCurrentSession().setPath(path);
		System.out.println(Session.getCurrentSession().getPath());
		return null;
	}

}