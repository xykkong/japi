package br.gov.planejamento.api.core.base;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MultivaluedMap;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterJapiException;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.core.utils.StringUtils;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class RequestContext {

	/**
	 * Current request object created by Resteasy
	 */
	private static RequestContext context = null;

	/**
	 * All parameters sent in querystring
	 */
	private Map<String, List<String>> parameters = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);

	private ArrayList<Filter> filters = new ArrayList<Filter>();
	
	private List<String> availableOrderByValues = new ArrayList<String>();
	
	private String requestFormat = Constants.RequestFormats.HTML;

	private String path;
	
	private String fullPath;

	/**
	 * Constructor that populates the static current variable with the created
	 * Request
	 */
	private RequestContext() {
		context = this;
	}

	public static RequestContext getContext() {
		if (context == null)
			context = new RequestContext();
		return context;
	}

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void addFilter(Filter...filters) {
		for(Filter filter : filters){
			Boolean addThisFilter = false;
			for(String parameter : filter.getUriParameters()){
				if (hasParameter(parameter)) {
					addThisFilter = true;
					filter.putValues(parameter, context.getValues(parameter));
					System.out.println("\n\tfilter added: "+ parameter+" with "+
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
	 * 
	 * @return valor de order_by da URI ou "1", se não existir
	 * @throws InvalidOrderByValueJapiException 
	 */
	public String getOrderByValue() throws InvalidOrderByValueJapiException {
		if (hasParameter(Constants.FixedParameters.ORDER_BY)) {
			String value = getValue(Constants.FixedParameters.ORDER_BY);
			if(!availableOrderByValues.contains(value))
				throw new InvalidOrderByValueJapiException(value, availableOrderByValues);
			return value;
		}
		return "1";
	}

	public String getOrderValue() throws InvalidOrderSQLParameterJapiException {
		if (hasParameter(Constants.FixedParameters.ORDER)) {
			String order = getValue(Constants.FixedParameters.ORDER);
			if (Arrays.asList(Constants.FixedParameters.VALID_ORDERS).contains(
					order.toUpperCase()))
				return order;
			else
				throw new InvalidOrderSQLParameterJapiException(order);
		}
		return "ASC";
	}
	
	public int getOffsetValue() throws InvalidOffsetValueJapiException {
		if(hasParameter(Constants.FixedParameters.OFFSET)){
			String sOffset = "";
			try{
				sOffset = getValue(Constants.FixedParameters.OFFSET); 
				int offset = Integer.parseInt(sOffset);
				return offset;
			}catch(NumberFormatException ex){
				throw new InvalidOffsetValueJapiException(sOffset);
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

	public String getHTMLTemplate() throws JsonSyntaxException, JsonIOException, FileNotFoundException, JapiException {
		if(JapiConfigLoader.getJapiConfig().getHtmlTemplate() != null)
			return (JapiConfigLoader.getJapiConfig().getHtmlTemplate());
		else throw new JapiException("Caminho do Template HTML não configurado no japi_config.json");
	}
	
	public String getDocsTemplate() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		if(JapiConfigLoader.getJapiConfig().getDocsHtmlFolder() != null)
			return JapiConfigLoader.getJapiConfig().getDocsHtmlFolder();
		else return "br/gov/planejamento/api/docs/templates/docs.vm";
	}
	
	public String getRootURL(){		
		try {
			return JapiConfigLoader.getJapiConfig().getRootUrl();
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public String asset(String...asset){

		return getRootURL()+"assets/resources/"+StringUtils.join("/", new ArrayList<String>(Arrays.asList(asset)));
	}
	
}
