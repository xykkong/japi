package br.gov.planejamento.api.core.serializers;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.ErrorResponse;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.core.utils.SerializeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class JSONSerializer {

	private static JsonObject resourceToJsonObject(Resource resource) throws ApiException {
		if(resource == null) return null;
		JsonObject item = new JsonObject();
		item.add("_links", SerializeUtils.linksToJSON(resource.getLinks()));
		for(Property property : resource.getProperties()) {
			if(property == null) continue;
			item.addProperty(property.getId(), property.getValue());
		}
		return item;
	}
	
	public static <T extends Resource> String fromResourceListResponse(ResourceListResponse<T> response) throws ApiException {
		JsonObject json = new JsonObject();
		
		JsonArray resources = new JsonArray();		
		for(Resource resource : response) {
			resources.add(resourceToJsonObject(resource));
		}		
		
		json.add("_links", SerializeUtils.linksToJSON(response.getLinks()));
		JsonObject embedded = new JsonObject();
		embedded.add(response.getName(), resources); //TODO: revisar nomenclatura
		json.add("_embedded", embedded);
		
		return json.toString();
	}
	
	public static String fromResourceResponse(ResourceResponse response) throws ApiException {
		JsonObject json = resourceToJsonObject(response.getResource());
		return json.toString();
	}
	
	public static String fromErrorResponse(ErrorResponse response) {
		JsonObject json = new JsonObject();
		
		JsonObject error = new JsonObject();
		error.addProperty("status", response.getApiException().getHttpStatusCode());
		error.addProperty("mensagem", response.getApiException().getMessage());
		
		json.add("error", error);
		
		return json.toString();
	}
}
