package br.gov.planejamento.api.core.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;
import br.gov.planejamento.api.core.utils.SerializeUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Response extends ArrayList<Resource> {

	private Boolean isList = true;
	private String name = "resources";
	private String description = "";
	
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
	public ArrayList<Link> getLinks() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return ReflectionUtils.getLinks(this);
	}
	
	/**
	 * Retorna um SelfLink da Response
	 * @return
	 */
	public Link getSelfLink() {
		return new SelfLink();
	}
	
	/**
	 * Serializa a Response no formato JSON, seguindo o padrão HAL.
	 * @return
	 */
	public String toJSON() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return JSONSerializer.fromResponse(this);		
	}
	
	/**
	 * Serializa a Response no formato XML, seguindo o padrão HAL.
	 * @return
	 * @throws TransformerException 
	 */
	public String toXML() throws ParserConfigurationException, TransformerException {
		return XMLSerializer.fromResponse(this);
	}
}
