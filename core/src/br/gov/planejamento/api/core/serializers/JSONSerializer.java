package br.gov.planejamento.api.core.serializers;

import java.lang.reflect.InvocationTargetException;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.utils.SerializeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class JSONSerializer {

	public static String fromResponse(Response response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonObject json = new JsonObject();
		
		JsonArray resources = new JsonArray();		
		for(Resource resource : response) {
			if(resource == null) continue;
			JsonObject item = new JsonObject();
			item.add("_links", SerializeUtils.linksToJSON(resource.getLinks()));
			for(Property property : resource.getProperties()) {
				if(property == null) continue;
				item.addProperty(property.getId(), property.getValue());
			}
			resources.add(item);
		}		
			
		if(response.isList()) {
			json.add("_links", SerializeUtils.linksToJSON(response.getLinks()));
			JsonObject embedded = new JsonObject();
			embedded.add(response.getName(), resources); //TODO: revisar nomenclatura
			json.add("_embedded", embedded);
		} else {
			if(resources.size() == 1) {
				json = (JsonObject) resources.get(0);
			} else {
				//TODO: Lançar exception quando uma página de detalhamento é chamada havendo mais de um registro
			}
		}
		
		return json.toString();
	}
}
