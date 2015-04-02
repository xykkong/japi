package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class Response extends ArrayList<Resource> {

	private static final long serialVersionUID = 8239651864123040735L;
	private Boolean isList;
	private String name;
	private String description;
	private int count = 0;

	public int getCount() {
		return count;
	}
	
	public Response (int count){
		this.count = count;
	}
	
	/**
	 * Retorna true se a Response em questão é uma listagem de Resources.
	 * Caso a Response seja de um item único (página de detalhamento), retorna false.
	 * 
	 * Observe que pode haver uma listagem que contém somente um elemento, onde a Response 
	 * não é uma página de detalhamento e portanto isList() retorna true.
	 * @return
	 */
	public Boolean isList() {
		return isList;
	}
	
	/**
	 * Define se a Response em questão é uma listagem de Resources (true) ou uma página de detalhamento
	 * de um único Resource (false).
	 * @param isList
	 */
	public void isList(Boolean isList) {
		this.isList = isList;
	}
	
	/**
	 * Getter do nome da Response	
	 * @return
	 */
	public String getName() {
		return name;
	}	
	
	/** 
	 * Setter do nome da Response
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter da descrição da Response
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter da descrição da Response
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Retorna todos os Links do Response
	 * @return
	 */
	public ArrayList<Link> getLinks() throws ApiException {
		return ReflectionUtils.getLinks(this);
	}
	
	/**
	 * Retorna um SelfLink da Response
	 * @return
	 */
	public Link getSelfLink() {
		return new SelfLink(RequestContext.getContext().getFullPath());
	}
	
	/**
	 * Serializa a Response no formato JSON, seguindo o padrão HAL.
	 * @return
	 */
	public String toJSON() throws ApiException {
		return JSONSerializer.fromResponse(this);		
	}

	/**
	 * Serializa a Response no formato CSV.
	 * @return
	 */
	public String toCSV() throws ApiException {
		return CSVSerializer.fromResponse(this);
	}

	/**
	 * Serializa a Response no formato HTML, utilizando o Velocity.
	 * @return
	 */
	public Object toHTML() throws ApiException {
		return HTMLSerializer.fromResponse(this);
	}
	
	/**
	 * Serializa a Response no formato XML, seguindo o padrão HAL.
	 * @return
	 */
	public String toXML() throws ApiException {
		return XMLSerializer.fromResponse(this);
	}
}
