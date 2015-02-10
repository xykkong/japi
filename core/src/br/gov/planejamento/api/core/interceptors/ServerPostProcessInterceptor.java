package br.gov.planejamento.api.core.interceptors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
						serverResponse.setEntity(response.toHTML());
						break;
					case RequestFormats.JSON:
						String json = response.toJSON();
						serverResponse.setEntity(json);
						break;
					case RequestFormats.XML:
						serverResponse.setEntity("banana");
						break;
					case RequestFormats.CSV:
						Headers headers = new Headers();
						headers.add("Content-Type","text/csv");
						headers.add("Content-Disposition",  "attachment; filename=\"result.csv\"");
						serverResponse.setMetadata(headers);
						serverResponse.setEntity(response.toCSV());
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	

}
