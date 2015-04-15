package br.gov.planejamento.api.core.responses;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.exceptions.ApiException;

import com.google.gson.JsonObject;

public class JsonObjectSwaggerResponse extends Response {
	
	private JsonObject jsonObject;
	
	public JsonObjectSwaggerResponse(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	@Override
	public SelfLink getSelfLink() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXML() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toCSV() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJSON() throws ApiException {
		return jsonObject.toString();
	}

	@Override
	public Object toHTML() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHttpStatusCode() {
		return 200;
	}

}
