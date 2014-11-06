package br.gov.planejamento.api.core.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.gov.planejamento.api.core.annotations.CSVProperties;
import br.gov.planejamento.api.core.annotations.HTMLProperties;
import br.gov.planejamento.api.core.annotations.JSONProperties;
import br.gov.planejamento.api.core.annotations.Properties;
import br.gov.planejamento.api.core.annotations.XMLProperties;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.ResourceList;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@ServerInterceptor
public class ServerProcessInterceptor implements
		PreProcessInterceptor, PostProcessInterceptor {

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
		
		return null;
	}

	@Override
	public void postProcess(ServerResponse response) {
		ResourceList resourceList = (ResourceList) response.getEntity();
		response.setGenericType(String.class);
		
		ArrayList<HashMap<String, Property>> resourceMapList = new ArrayList<HashMap<String, Property>>();
		
		for(Resource resource : resourceList) {
			
			HashMap<String, Property> resourceMap = new HashMap<String, Property>();
			Class<? extends Resource> resourceClass = resource.getClass();
			String[] propertiesNames;
			
			List<Annotation> annotations = Arrays.asList(resourceClass.getAnnotations());
			Session currentSession = Session.getCurrentSession();
			
			if(annotations.contains(HTMLProperties.class) && currentSession.isCurrentFormat(RequestFormats.HTML)) {
				propertiesNames = resourceClass.getAnnotation(HTMLProperties.class).value();
			} else if(annotations.contains(JSONProperties.class) && currentSession.isCurrentFormat(RequestFormats.JSON)) {
				propertiesNames = resourceClass.getAnnotation(JSONProperties.class).value();
			} else if(annotations.contains(XMLProperties.class) && currentSession.isCurrentFormat(RequestFormats.XML)) {
				propertiesNames = resourceClass.getAnnotation(XMLProperties.class).value();
			} else if(annotations.contains(CSVProperties.class) && currentSession.isCurrentFormat(RequestFormats.CSV)) {
				propertiesNames = resourceClass.getAnnotation(CSVProperties.class).value();
			} else {
				propertiesNames = resourceClass.getAnnotation(Properties.class).value();
			}
			
			for(String propertyName : propertiesNames) {
				try { 
					Method getter = resourceClass.getMethod("get" + StringUtils.capitalize(propertyName));
					Property property = (Property) getter.invoke(resource);
					resourceMap.put(propertyName, property);
				} catch(Exception e) {
					//TRATAR
					String a = "";
				}
			}
			
			resourceMapList.add(resourceMap);
		}
		
		switch(Session.getCurrentSession().getRequestFormat()) {
			case RequestFormats.HTML:
				response.setEntity(getHTML(resourceMapList));
				break;
			case RequestFormats.JSON:
				response.setEntity(getJSON(resourceMapList));
				break;
		}
		
	}
	
	private String getHTML(ArrayList<HashMap<String, Property>> resourceList) {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html><html><head><title>JAPIGOV</title><meta charset='utf-8'/></head><body><table border='1'><tr>");
		
		for(Entry<String, Property> entry : resourceList.get(0).entrySet()) {
			sb.append("<th>" + entry.getValue().getName() + "</th>");
		}
		sb.append("</tr>");
		
		for(HashMap<String, Property> resource : resourceList) {
			sb.append("<tr>");
			for(Entry<String, Property> entry : resource.entrySet()) {
				sb.append("<td>" + entry.getValue().getValue() + "</td>");
			}
			sb.append("</tr>");
		}
		
		sb.append("</table></body></html>");
		
		return sb.toString();
	}
	
	private String getJSON(ArrayList<HashMap<String, Property>> resourceList) {
		
		ArrayList<HashMap<String, String>> serializable = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, Property> resource : resourceList) {
			HashMap<String, String> serializableResource = new HashMap<String, String>();
			
			for(Entry<String, Property> entry : resource.entrySet()) {
				String propertyName = entry.getKey();
				Property property = entry.getValue();
				serializableResource.put(propertyName, property.getValue());
			}
			
			serializable.add(serializableResource);
		}
		
		Gson json = new GsonBuilder().create();
		String res = json.toJson(serializable);
		return res;
	}

}
