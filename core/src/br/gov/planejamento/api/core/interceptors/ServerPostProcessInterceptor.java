package br.gov.planejamento.api.core.interceptors;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

import au.com.bytecode.opencsv.CSVWriter;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;

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
						String json = response.toJSON();
						serverResponse.setEntity(json);
						break;
					case RequestFormats.XML:
						serverResponse.setEntity(getXML(resourceMapList));
						break;
					case RequestFormats.CSV:
						Headers headers = new Headers();
						headers.add("Content-Type","text/csv");
						headers.add("Content-Disposition",  "attachment; filename=\"result.csv\"");
						serverResponse.setMetadata(headers);
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
			} catch (IOException e) {
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
