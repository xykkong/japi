package br.gov.planejamento.api.core.responses;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

import com.google.gson.JsonObject;

public class SwaggerResponse extends Response {
	
	private JsonObject jsonObject;
	
	public SwaggerResponse(JsonObject jsonObject) {
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
		throw new CoreException(Errors.SWAGGER_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para XML. Uma response do tipo SwaggerResponse só pode ser serializada no formato JSON.");
	}

	@Override
	public String toCSV() throws ApiException {
		throw new CoreException(Errors.SWAGGER_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para CSV. Uma response do tipo SwaggerResponse só pode ser serializada no formato JSON.");
	}

	@Override
	public String toJSON() throws ApiException {
		return jsonObject.toString();
	}

	@Override
	public Object toHTML() throws ApiException {
		throw new CoreException(Errors.SWAGGER_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para HTML. Uma response do tipo SwaggerResponse só pode ser serializada no formato JSON.");
	}

	@Override
	public int getHttpStatusCode() {
		return 200;
	}

}
