package br.gov.planejamento.api.core.provider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import au.com.bytecode.opencsv.CSVWriter;
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
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedException;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

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
		Session.getCurrentSession().setPath(httpRequest.getUri().getAbsolutePath().getPath());
		System.out.println(Session.getCurrentSession().getPath());
		return null;
	}

	@Override
	public void postProcess(ServerResponse response) {
		Session currentSession = Session.getCurrentSession();
		String firstPathSegment = currentSession.getPath().split("/")[1];
		try {
			currentSession.validateURIParametersUsingFilters();
		} catch (URIParameterNotAcceptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO usar constantes
		if(!firstPathSegment.equals("docs"))
			try {
				interceptJapiResource(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private void interceptJapiResource(ServerResponse response) throws IOException{
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
			case RequestFormats.XML:
				response.setEntity(getXML(resourceMapList));
				break;
			case RequestFormats.CSV:
				Headers headers = new Headers();
				headers.add("Content-Type","text/csv");
				headers.add("Content-Disposition",  "attachment; filename=\"result.csv\"");
				response.setMetadata(headers);
				response.setEntity(getCSV(resourceMapList));
				break;
		}
	}
	
	private String getHTML(ArrayList<HashMap<String, Property>> resourceList) {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html><html><head><title>JAPIGOV</title><meta charset='utf-8'/></head><body><table border='1'><tr>");
	
		if(resourceList.size() > 0) {
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
	
	private String getXML(ArrayList<HashMap<String, Property>> resourceList) {	        
		XStream magicApi = new XStream();
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("resourceList", List.class);
        magicApi.alias("resource", Map.class);

        String xml = magicApi.toXML(resourceList);
		
		return xml;
	}
	
	private String getCSV(ArrayList<HashMap<String, Property>> resourceList) throws IOException {
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		int numeroColunas = resourceList.get(0).size();
		String[] linha = new String[numeroColunas];
		
		
		for(HashMap<String, Property> resource : resourceList) {
			int counter = 0;
			
			for(Entry<String, Property> entry : resource.entrySet()) {
				
				linha[counter] = entry.getValue().getValue();
				counter++;
			}
			writer.writeNext(linha);
			
		}
		writer.close();
		return sw.toString();
	}
	
	
	public static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                writer.setValue(entry.getValue().toString());
                writer.endNode();
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>();

            while(reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }

}
