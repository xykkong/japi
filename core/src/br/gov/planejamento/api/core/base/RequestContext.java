package br.gov.planejamento.api.core.base;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueRequestException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueRequestException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderValueRequestException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class RequestContext {

	/**
	 * Singleton do RequestContext que guardará todos os dados da requisição
	 */
	private static RequestContext context = null;

	
	/**
	 * Parâmetros enviados na query string
	 */
	private Map<String, List<String>> parameters = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);

	/**
	 * Filtros inseridos pela camada de Request
	 */
	private ArrayList<Filter> filters = new ArrayList<Filter>();
	
	/**
	 * Colunas permitidas no order by do service requisitado
	 */
	private List<String> availableOrderByValues = new ArrayList<String>();
	
	/**
	 * Formato de resposta requisitado pelo cliente. Por padrão, HTML.
	 */
	private String requestFormat = Constants.RequestFormats.HTML;

	/**
	 * URL requisitada pelo cliente sem incluir a query string
	 */
	private String path;
	
	/**
	 * URL requisitada pelo cliente incluindo a query string
	 */
	private String fullPath;

	/**
	 * Contrutor privado para o singleton
	 */
	private RequestContext() {
	}

	/**
	 * Factory do singleton
	 * @return
	 */
	public static RequestContext getContext() {
		if (context == null)
			context = new RequestContext();
		return context;
	}

	/**
	 * Retorna os filtros de consulta ao banco adicionados na camada de Request
	 * @return
	 */
	public ArrayList<Filter> getFilters() {
		return filters;
	}

	/**
	 * Adiciona à filtragem da consulta os filtros que tenham seus respectivos query parameters
	 * presentes na query string da requisição 
	 * @param filters Filtros a serem inseridos
	 */
	public void addFilter(Filter...filters) {
		for(Filter filter : filters){
			Boolean addThisFilter = false;
			for(String parameter : filter.getUriParameters()){
				if (hasParameter(parameter)) {
					addThisFilter = true;
					filter.putValues(parameter, context.getValues(parameter));
					System.out.println("\n\tFilter added: "+ parameter+" with "+
							context.getValues(parameter).size()+" values.");
				}
			}
			if(addThisFilter)
				this.filters.add(filter);
		}
	}

	public void putValues(MultivaluedMap<String, String> multivaluedMap) {
		parameters.putAll(multivaluedMap);
	}

	public List<String> getValues(String key) {
		if (hasParameter(key))
			return parameters.get(key);
		return null;
	}

	/**
	 * 
	 * @param key
	 *            referente ao valor do parâmetro GET
	 * @return primeiro valor da lista de valores deste parâmetro, se existir
	 */
	public String getValue(String key) {
		if (hasParameter(key)) {
			return parameters.get(key).get(0);
		}
		return null;
	}

	/**
	 * Teste testaaaa
	 * @return valor de order_by da URI ou "1", se não existir
	 * @throws InvalidOrderByValueRequestException 
	 */
	public String getOrderByValue() throws InvalidOrderByValueRequestException {
		if (hasParameter(Constants.FixedParameters.ORDER_BY)) {
			String value = getValue(Constants.FixedParameters.ORDER_BY);
			if(!availableOrderByValues.contains(value))
				throw new InvalidOrderByValueRequestException(value, availableOrderByValues);
			return value;
		}
		return "1";
	}

	public String getOrderValue() throws InvalidOrderValueRequestException {
		if (hasParameter(Constants.FixedParameters.ORDER)) {
			String order = getValue(Constants.FixedParameters.ORDER);
			if (Arrays.asList(Constants.FixedParameters.VALID_ORDERS).contains(
					order.toUpperCase()))
				return order;
			else
				throw new InvalidOrderValueRequestException(order);
		}
		return Constants.FixedParameters.VALID_ORDERS[0];
	}
	
	public int getOffsetValue() throws InvalidOffsetValueRequestException {
		if(hasParameter(Constants.FixedParameters.OFFSET)){
			String sOffset = "";
			try{
				sOffset = getValue(Constants.FixedParameters.OFFSET); 
				int offset = Integer.parseInt(sOffset);
				return offset;
			}catch(NumberFormatException ex){
				throw new InvalidOffsetValueRequestException(sOffset);
			}
		}
		return 0;
	}

	public boolean hasParameter(String key) {
		return parameters != null && parameters.get(key) != null
				&& parameters.get(key).size() > 0;
	}

	public void clear() {
		context = null;
	}
	
	public void addAvailableOrderByValues(List<String> values) {
		availableOrderByValues.addAll(values);
	}
	
	public String getRequestFormat() {
		return requestFormat;
	}
	
	public void setRequestFormat(String requestFormat) {
		this.requestFormat = requestFormat;
	}
	
	public boolean isCurrentFormat(String format) {
		return RequestContext.getContext().getRequestFormat().equals(format);
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public Boolean isHTML() {
		return isCurrentFormat(RequestFormats.HTML);
	}

	public Boolean isJSON() {
		return isCurrentFormat(RequestFormats.JSON);
	}
	
	public Boolean isXML() {
		return isCurrentFormat(RequestFormats.XML);
	}
	
	public Boolean isCSV() {
		return isCurrentFormat(RequestFormats.CSV);
	}

	public String getHTMLTemplate() throws ApiException {
		// TODO realmente fazer este método
		if(JapiConfigLoader.getJapiConfig().getHtmlTemplate() != null)
			return (JapiConfigLoader.getJapiConfig().getHtmlTemplate());
		else throw new RequestException("Caminho do Template HTML não configurado no japi_config.json");
	}
	
	public String getDocsTemplate() throws ApiException {
		if(JapiConfigLoader.getJapiConfig().getDocsHtmlFolder() != null)
			return JapiConfigLoader.getJapiConfig().getDocsHtmlFolder();
		else return "br/gov/planejamento/api/docs/templates/docs.vm";
	}
	
	public String getRootURL(){
		//TODO pegar do japiConfig.json
		return "localhost:8080/";
	}
	public String asset(String...asset){
		//TODO pegar do japiConfig.json
		return "http://"+getRootURL()+"assets/resources/"+StringUtils.join("/", new ArrayList<String>(Arrays.asList(asset)));
	}
	
	public void putPathParameters(String name, String value){
		MultivaluedMap<String, String> pathParameters = new MultivaluedMapImpl<String, String>();
		pathParameters.add(name, value);
		RequestContext.getContext().putValues(pathParameters);
	}
	
}
