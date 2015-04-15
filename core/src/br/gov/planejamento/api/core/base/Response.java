package br.gov.planejamento.api.core.base;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.interfaces.ILinks;
import br.gov.planejamento.api.core.interfaces.ISelfLink;
import br.gov.planejamento.api.core.interfaces.ISerializable;
import br.gov.planejamento.api.core.responses.ResourceListResponse;

public abstract class Response implements ISelfLink, ILinks, ISerializable {

	/**
	 * Obtém o código do Status HTTP da Response
	 * @return
	 */
	public abstract int getHttpStatusCode();
	
	private String name = "";
	private String description = "";
	
	public boolean isList(){
		if(this instanceof ResourceListResponse<?>) return true;
		return false;
	}
	
	/**
	 * Obtém o nome da Response	
	 * @return Nome da Response
	 */
	public String getName() throws ApiException {
		return name;
	}
	
	/**
	 * Obtém a descrição da Response
	 * @return Descrição da Response
	 */
	public String getDescription() throws ApiException {
		return description;
	}
	
	/** 
	 * Define o nome da Response
	 * @param name Nome
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Define a descrição da Response
	 * @param description Descrição
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getNextPage() throws ApiException{
		SelfLink self = getSelfLink();
		String[] arraySelf = self.getHref().split("offset=");
		int nextOffset = RequestContext.getContext().getOffsetValue()+Constants.FixedParameters.VALUES_PER_PAGE;
		if(arraySelf[1].matches("(\\d+)(.*)")){
			arraySelf[1] = arraySelf[1].replaceAll("(\\d+)(.*)", nextOffset+"$2");
		}
		return arraySelf[0]+"offset="+arraySelf[1];
	}
	
	public String getPreviousPage() throws ApiException{
		SelfLink self = getSelfLink();
		String[] arraySelf = self.getHref().split("offset=");
		int prevOffset = RequestContext.getContext().getOffsetValue()-Constants.FixedParameters.VALUES_PER_PAGE;
		if(arraySelf[1].matches("(\\d+)(.*)")){
			arraySelf[1] = arraySelf[1].replaceAll("(\\d+)(.*)", prevOffset+"$2");
		}
		return arraySelf[0]+"offset="+arraySelf[1];
	}
}
