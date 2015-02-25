package br.gov.planejamento.api.core.utils;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;

import com.google.gson.JsonObject;

public abstract class SerializeUtils {
	
	public static JsonObject linksToJSON(ArrayList<Link> links) {
		JsonObject json = new JsonObject();
		for(Link link : links) {
			if(link == null) continue;
			JsonObject linkObject = new JsonObject();
			linkObject.addProperty("href", link.getHref());
			linkObject.addProperty("title", link.getTitle());
			json.add(link.getRel(), linkObject); //TODO: Lançar exception caso getRel retorne vazio/null
		}
		return json;		
	}
}
