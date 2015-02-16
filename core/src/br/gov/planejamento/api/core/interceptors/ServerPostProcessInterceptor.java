package br.gov.planejamento.api.core.interceptors;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor {

	@Override
	public void postProcess(ServerResponse serverResponse) {
		
		String firstPathSegment = Session.getCurrentSession().getPath().split("/")[1];		
		if(!firstPathSegment.equals("docs")) //TODO: Mudar forma de identificar docs
			try {
				Response response = (Response) serverResponse.getEntity();
				serverResponse.setGenericType(String.class);
				ArrayList<HashMap<String, Property>> resourceMapList = new ArrayList<HashMap<String, Property>>();
				
				switch(Session.getCurrentSession().getRequestFormat()) {
					case RequestFormats.HTML:
						serverResponse.setEntity(getHTML(resourceMapList));
						break;
					case RequestFormats.JSON:
						serverResponse.setEntity(response.toJSON());
						break;
					case RequestFormats.XML:
						serverResponse.setEntity(response.toXML());
						break;
					case RequestFormats.CSV:
						serverResponse.setEntity(getCSV(resourceMapList));
						break;
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	private String getXML(ArrayList<HashMap<String, Property>> resourceList) {	        
		XStream magicApi = new XStream();
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias("resourceList", List.class);
        magicApi.alias("resource", Map.class);

        String xml = magicApi.toXML(resourceList);
	
		return xml;
	}
	
	private String getCSV(ArrayList<HashMap<String, Property>> resourceList) {
		StringBuilder sb = new StringBuilder();
		
		for(HashMap<String, Property> resource : resourceList) {
			for(Entry<String, Property> entry : resource.entrySet()) {
				sb.append(entry.getValue().getValue());
				sb.append(",");
			}
			sb.append("\r\n");
		}
		
		return sb.toString();
		
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
