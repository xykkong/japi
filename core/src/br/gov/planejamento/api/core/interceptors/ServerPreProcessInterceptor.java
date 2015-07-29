package br.gov.planejamento.api.core.interceptors;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
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

import br.gov.planejamento.api.core.annotations.ApiModule;
import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.base.JapiConfigLoader;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.database.ConnectionManager;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedRequestException;
import br.gov.planejamento.api.core.utils.StringUtils;

@Provider
@ServerInterceptor
public class ServerPreProcessInterceptor implements PreProcessInterceptor {
	
	@Override
	public ServerResponse preProcess(HttpRequest httpRequest,
			ResourceMethod method) throws Failure, WebApplicationException {	
			
		try {
			loadConfigurations(httpRequest);
		} catch (ApiException e1) {
			return new ServerResponse(e1, 500, new Headers<Object>());
		}
		
		if(method.getMethod().isAnnotationPresent(ApiRequest.class) && !method.getMethod().getDeclaringClass().isAnnotationPresent(ApiModule.class)) {
			CoreException e = new CoreException(Errors.PRE_PROCESS_REQUEST_NAO_ANOTADA_APIMODULE, "Toda classe de Request deve ser anotada por @ApiModule.", 404);
			return new ServerResponse(e, 404, new Headers<Object>());
		}
		
		try {	
			validateURIParametersUsingAnotations(httpRequest, method);
		} catch (ApiException e) {
			return new ServerResponse(e, 400, new Headers<Object>());
		}
		
		return null;
	}
	
	public static void loadConfigurations(HttpRequest httpRequest) throws ApiException {

		RequestContext.getContext().clear();
		RequestContext.getContext().putValues(httpRequest.getUri().getQueryParameters());
		RequestContext.getContext().putValues(httpRequest.getUri().getPathParameters());
		
		loadTemplates();
		
		String fullPath = loadFullPath(httpRequest);
		fullPath = FilenameUtils.removeExtension(fullPath);
		System.out.println("Full path: "+ fullPath);
		
		List<PathSegment> pathSegments = httpRequest.getUri().getPathSegments();
		String requestFormat = StringUtils.lastSplitOf(pathSegments.get(pathSegments.size()-1).getPath(), "\\.").toLowerCase();
		
		if(requestFormat == "") {
			requestFormat = Constants.RequestFormats.HTML;
		}
		
		RequestContext.getContext().setRequestFormat(requestFormat);
		String path = httpRequest.getUri().getAbsolutePath().getPath();
		System.out.println(httpRequest.getUri().getBaseUri().getPath());
		path = FilenameUtils.removeExtension(path);
		RequestContext.getContext().setPath(path);
		RequestContext.getContext().setFullPath(fullPath);
		System.out.println(RequestContext.getContext().getPath());
	
	}

	public static void loadTemplates() throws ApiException {
		/**
		 * Leitura de parâmetros da JapiConfig e inserção dos dados na RequestContext
		 */
		RequestContext.getContext().setRootURL(JapiConfigLoader.getJapiConfig().getRootUrl());
		
		if(JapiConfigLoader.getJapiConfig().getModules() != null)
			RequestContext.getContext().setModulos(JapiConfigLoader.getJapiConfig().getModules());
		else throw new CoreException(Errors.CONFIG_LOADER_MODULOS_NAO_DEFINIDOS, "Nomes dos modulos não configurados no japi_config.json (modules)");
		
		if(JapiConfigLoader.getJapiConfig().getMirrors() != null)
			RequestContext.getContext().setMirrors(JapiConfigLoader.getJapiConfig().getMirrors());
		else throw new CoreException(Errors.CONFIG_LOADER_MODULOS_NAO_DEFINIDOS, "Nomes dos modulos não configurados no japi_config.json (modules)");
		
		if(JapiConfigLoader.getJapiConfig().getDatabaseProperties() != null)
			ConnectionManager.setDbProperties(JapiConfigLoader.getJapiConfig().getDatabaseProperties());
		else throw new CoreException(Errors.CONFIG_LOADER_DATABASE_NAO_ENCONTRADO, "Propriedados de banco de dados não configuradas no japi_config.json (databaseProperties)");
		
		if(JapiConfigLoader.getJapiConfig().getResourceTemplate() != null)
			RequestContext.getContext().setResourceTemplate(JapiConfigLoader.getJapiConfig().getResourceTemplate());
		else throw new CoreException(Errors.CONFIG_LOADER_TEMPLATE_RESOURCE_NAO_ENCONTRADO, "Caminho do Template de Resource não configurado no japi_config.json (resourceTemplate)");
		
		if(JapiConfigLoader.getJapiConfig().getDocsModuloTemplate() != null)
			RequestContext.getContext().setDocsModuloTemplate(JapiConfigLoader.getJapiConfig().getDocsModuloTemplate());
		else throw new CoreException(Errors.CONFIG_LOADER_TEMPLATE_MODULO_DOCS_NAO_ENCONTRADO, "Caminho do Template de Módulo do Docs não configurado no japi_config.json (docsModuloTemplate)");
		
		if(JapiConfigLoader.getJapiConfig().getDocsMetodoTemplate() != null)
			RequestContext.getContext().setDocsMetodoTemplate(JapiConfigLoader.getJapiConfig().getDocsMetodoTemplate());
		else throw new CoreException(Errors.CONFIG_LOADER_TEMPLATE_METODO_DOCS_NAO_ENCONTRADO, "Caminho do Template de Método do Docs não configurado no japi_config.json (docsMetodoTemplate)");
		
		if(JapiConfigLoader.getJapiConfig().getErrorTemplate() != null)
			RequestContext.getContext().setErrorTemplate(JapiConfigLoader.getJapiConfig().getErrorTemplate());
		else throw new CoreException(Errors.CONFIG_LOADER_TEMPLATE_ERRO_NAO_ENCONTRADO, "Caminho do Template de erro não configurado no japi_config.json (errorTemplate)");
		
		if(JapiConfigLoader.getJapiConfig().getStaticHtmlTemplate() != null)
			RequestContext.getContext().setStaticHtmlTemplate(JapiConfigLoader.getJapiConfig().getStaticHtmlTemplate());
		else throw new CoreException(Errors.CONFIG_LOADER_TEMPLATE_PAGINA_ESTATICA_NAO_ENCONTRADO, "Caminho do Template de Página Estática não configurado no japi_config.json (staticHtmlTemplate)");
	}
	
	public static String loadFullPath(HttpRequest httpRequest){
		 String fullPath = httpRequest.getUri().getAbsolutePath().getPath();
			MultivaluedMap<String, String> parameters = httpRequest.getUri().getQueryParameters();
			boolean first = true;
			for (java.util.Map.Entry<String, List<String>> parameter : parameters.entrySet()){
				if(first) {
					fullPath = fullPath+"?"+parameter.getKey()+"="+parameter.getValue().get(0);
					first = false;
				}
				else{ 
					fullPath = fullPath+"&"+parameter.getKey()+"="+parameter.getValue().get(0);
				}
			}
			
			return fullPath;
	}
	
	private static void validateURIParametersUsingAnotations(HttpRequest httpRequest, ResourceMethod method)
			throws URIParameterNotAcceptedRequestException{
		
		for(String parameter : httpRequest.getUri().getQueryParameters().keySet()){
			boolean foundParameter = false;
			foundParameter |= Arrays.asList(Constants.FixedParameters.DEFAULT_URI_PARAMETERS).contains(parameter);
			if(!foundParameter) {
				for(Annotation[] argAnnotations : method.getMethod().getParameterAnnotations()){
					for(Annotation annotation : argAnnotations){
						if(annotation.annotationType() == Parameter.class){
							Parameter doc = (Parameter) annotation;
							foundParameter |= doc.name().toLowerCase().equals(parameter.toLowerCase());
						}
					}
				}
			}
			if (!foundParameter)
				throw new URIParameterNotAcceptedRequestException(parameter);
		}
	}

}
