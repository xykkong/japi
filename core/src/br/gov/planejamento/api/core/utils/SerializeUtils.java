package br.gov.planejamento.api.core.utils;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	public static ArrayList<Element> linksToXML(Document document, ArrayList<Link> links) {
		ArrayList<Element> elements = new ArrayList<Element>();
		for(Link link : links) {
			if(link == null) continue;
			Element linkObject = document.createElement("link");
			linkObject.setAttribute("href", link.getHref());
			linkObject.setAttribute("rel", link.getRel());
			linkObject.setAttribute("title", link.getTitle());
			elements.add(linkObject);
		}
		return elements;
	}
}
