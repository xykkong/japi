package br.gov.planejamento.api.core.responses;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class ErrorResponse extends Response {

	private String description = "Página de erro";
	private ApiException exception = null;

	/**
	 * Construtor da ErrorResponse
	 */
	public ErrorResponse (ApiException exception){
		this.exception = exception;
	}
	
	/**
	 * Obtém a ApiException encapsulada pela ErrorResponse
	 * @return ApiException
	 */
	public ApiException getApiException() {
		return exception;
	}
	
	/**
	 * Define a ApiException que motivou a ErrorResponse
	 * @param exception ApiException
	 * @throws ApiException
	 */
	public void setResource(ApiException exception) {
		this.exception = exception;
	}
		
	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
	
	@Override
	public SelfLink getSelfLink() {
		return new SelfLink(RequestContext.getContext().getFullPath(), this.description);
	}
	
	@Override
	public String toJSON() throws ApiException {
		return JSONSerializer.fromErrorResponse(this);		
	}

	@Override
	public String toCSV() throws ApiException {
		return CSVSerializer.fromErrorResponse(this);
	}

	@Override
	public Object toHTML() throws ApiException {
		return HTMLSerializer.fromErrorResponse(this);
	}
	
	@Override
	public String toXML() throws ApiException {
		return XMLSerializer.fromErrorResponse(this);
	}
	
	@Override
	public int getHttpStatusCode() {
		return getApiException().getHttpStatusCode();
	}
}