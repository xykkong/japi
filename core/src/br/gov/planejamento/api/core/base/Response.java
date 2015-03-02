package br.gov.planejamento.api.core.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.dom.DOMException;

import br.gov.planejamento.api.core.serializers.CSVSerializer;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;
import br.gov.planejamento.api.core.serializers.JSONSerializer;
import br.gov.planejamento.api.core.serializers.XMLSerializer;
import br.gov.planejamento.api.core.utils.ReflectionUtils;

public class Response extends ArrayList<Resource> {

	private Boolean isList = true;
	private String name = "resources";
	private String description = "";
	private int count = 0;

	public int getCount() {
		return count;
	}
	
	public Response (int count){
		this.count = count;
	}
	
	/**
	 * Caso a Response seja de um item único (página de detalhamento), retorna false.
	 * 
	 * não é uma página de detalhamento e portanto isList() retorna true.
	 * @return
	 */
	public Boolean isList() {
		return isList;
	}
	
	/**
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

	public String toCSV() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		return CSVSerializer.fromResponse(this);
	}

	public Object toHTML() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, Exception {
		return HTMLSerializer.fromResponse(this);
	}
	
	/**
	 * Serializa a Response no formato XML, seguindo o padrão HAL.
	 * @return
	 * @throws TransformerException 
	 * @throws DOMException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public String toXML() throws ParserConfigurationException, TransformerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DOMException {
		return XMLSerializer.fromResponse(this);
	}
}
