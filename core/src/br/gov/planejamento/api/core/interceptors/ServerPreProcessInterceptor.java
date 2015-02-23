package br.gov.planejamento.api.core.interceptors;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.gov.planejamento.api.core.annotations.DocParameterField;
import br.gov.planejamento.api.core.base.JapiConfigLoader;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedJAPIException;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Provider
@ServerInterceptor
public class ServerPreProcessInterceptor implements PreProcessInterceptor {
	
	@Override
	public ServerResponse preProcess(HttpRequest httpRequest,
			ResourceMethod method) throws Failure, WebApplicationException {	
		
		try {
			System.out.println(JapiConfigLoader.getJapiConfig().getHtmlFolder());
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.out.println("Houve um erro ao carregar o arquivo japi_config.json");
			e.printStackTrace();
		}
		Session.getCurrentSession().clear();
		Session.getCurrentSession().putValues(httpRequest.getUri().getQueryParameters());
		
		List<PathSegment> pathSegments = httpRequest.getUri().getPathSegments();
		String requestFormat = StringUtils.lastSplitOf(pathSegments.get(pathSegments.size()-1).getPath(), "\\.").toLowerCase();
		
		if(requestFormat == "") {
			requestFormat = Constants.RequestFormats.HTML;
		}
		
		Session.getCurrentSession().setRequestFormat(requestFormat);
		String path = httpRequest.getUri().getAbsolutePath().getPath();
		System.out.println(httpRequest.getUri().getBaseUri().getPath());
		path = FilenameUtils.removeExtension(path);
		Session.getCurrentSession().setPath(path);
		System.out.println(Session.getCurrentSession().getPath());
		
		try {	
			validateURIParametersUsingAnotations(httpRequest, method);
		} catch (URIParameterNotAcceptedJAPIException e) {
			return new ServerResponse(e, 400, new Headers<Object>());
		}
		
		return null;
	}
	
	private static void validateURIParametersUsingAnotations(HttpRequest httpRequest, ResourceMethod method)
			throws URIParameterNotAcceptedJAPIException{
		
		for(String parameter : httpRequest.getUri().getQueryParameters().keySet()){
			boolean foundParameter = false;
			foundParameter |= Arrays.asList(Constants.FixedParameters.DEFAULT_URI_PARAMETERS).contains(parameter);
			if(!foundParameter) {
				for(Annotation[] argAnnotations : method.getMethod().getParameterAnnotations()){
					for(Annotation annotation : argAnnotations){
						if(annotation.annotationType() == DocParameterField.class){
							DocParameterField doc = (DocParameterField) annotation;
							foundParameter |= doc.name().toLowerCase().equals(parameter.toLowerCase());
						}
					}
				}
			}
			if (!foundParameter)
				throw new URIParameterNotAcceptedJAPIException(parameter);
		}
	}

}
