package br.gov.planejamento.api.core.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class Response extends ArrayList<Resource> {

	private Boolean isList = true;
	private String name = "resources";
	private String description = "";
	
	/**
	 * Retorna true se a Response em quest�o � uma listagem de Resources.
	 * Caso a Response seja de um item �nico (p�gina de detalhamento), retorna false.
	 * 
	 * Observe que pode haver uma listagem que cont�m somente um elemento, onde a Response 
	 * n�o � uma p�gina de detalhamento e portanto isList() retorna true.
	 * @return
	 */
	public Boolean isList() {
		return isList;
	}
	
	/**
	 * Define se a Response em quest�o � uma listagem de Resources (true) ou uma p�gina de detalhamento
	 * de um �nico Resource (false).
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
	 * Getter da descri��o da Response
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter da descri��o da Response
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
	 * Serializa a Response no formato JSON, seguindo o padr�o HAL.
	 * @return
	 */
	public String toJSON() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return JSONSerializer.fromResponse(this);		
	}

	public String toCSV() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		return CSVSerializer.fromResponse(this);
	}

	public Object toHTML() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, Exception {
		return HTMLSerializer.fromResponse(this);
	}
	
}
