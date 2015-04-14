package br.gov.planejamento.api.core.responses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class ResourceResponse<T extends Resource> extends Response {

	private String name = "resource";
	private String description = "";
	private Resource resource = null;

	/**
	 * Construtor privado da ResourceResponse
	 */
	private ResourceResponse (){
		setName(name);
		setDescription(description);
	}
	
	/**
	 * Constrói uma ResourceResponse a partir de um DatabaseData e de um tipo de Resource especificado
	 * @param data Dados recebidos do Banco de Dados
	 * @param resourceType Tipo de Resource a ser criado
	 * @return Uma instância de ResourceResponse contendo um Resource do tipo especificado
	 * @throws ApiException 
	 */
	public static <T extends Resource> ResourceResponse<T> factory(DatabaseData data, Class<? extends Resource> resourceType) throws ApiException {
		
		ResourceResponse<T> response = new ResourceResponse<T>();
		
		try {
			if(data.iterator().hasNext()) {
				Resource object = (resourceType.getConstructor(DataRow.class).newInstance(data.iterator().next()));
				response.setResource(object);
			} else {
				return response;
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new CoreException("Houve um erro ao criar os Resources a partir da DatabaseData", e);
		}
		
		return response;
	}
	
	/**
	 * Obtém o Resource solicitado na Request
	 * @return Resource caso seja encontrado, null caso contrário
	 * @throws ApiException
	 */
	public Resource getResource() throws ApiException {
		return resource;
	}
	
	/**
	 * Define o Resource que será retornado pela Response
	 * @param resource Resource a ser retornado
	 * @throws ApiException
	 */
	public void setResource(Resource resource) throws ApiException {
		this.resource = resource;
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
		return JSONSerializer.fromResourceResponse(this);		
	}

	@Override
	public String toCSV() throws ApiException {
		return CSVSerializer.fromResourceResponse(this);
	}

	@Override
	public Object toHTML() throws ApiException {
		return HTMLSerializer.fromResourceResponse(this);
	}
	
	@Override
	public String toXML() throws ApiException {
		return XMLSerializer.fromResourceResponse(this);
	}

	@Override
	public int getHttpStatusCode() {
		return Constants.HttpStatusCodes.OK;
	}
}