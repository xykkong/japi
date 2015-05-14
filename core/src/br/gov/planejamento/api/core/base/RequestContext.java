package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MultivaluedMap;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueRequestException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueRequestException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderValueRequestException;
import br.gov.planejamento.api.core.utils.StringUtils;

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
	 * URL raíz da aplicação
	 */
	private String rootURL;
	
	/**
	 * Templates a serem carregados no preprocess
	 */
	private String resourceTemplate;	
	private String docsModuloTemplate;
	private String docsMetodoTemplate;
	private String errorTemplate;
	private String staticHtmlTemplate;


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
	public String getOrderByValue() throws ApiException {
		if (hasParameter(Constants.FixedParameters.ORDER_BY)) {
			String value = getValue(Constants.FixedParameters.ORDER_BY);
			if(!availableOrderByValues.contains(value))
				throw new InvalidOrderByValueRequestException(value, availableOrderByValues);
			return value;
		}
		return "1";
	}

	public String getOrderValue() throws ApiException {
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
	
	public int getOffsetValue() throws ApiException {
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
	

	public void setResourceTemplate(String resourceTemplate) {
		this.resourceTemplate = resourceTemplate;
	}

	public void setDocsModuloTemplate(String docsModuloTemplate) {
		this.docsModuloTemplate = docsModuloTemplate;
	}

	public void setDocsMetodoTemplate(String docsMetodoTemplate) {
		this.docsMetodoTemplate = docsMetodoTemplate;
	}

	public void setErrorTemplate(String errorTemplate) {
		this.errorTemplate = errorTemplate;
	}

	public void setStaticHtmlTemplate(String staticHtmlTemplate) {
		this.staticHtmlTemplate = staticHtmlTemplate;
	}
	
	public String getResourceTemplate() {
		return resourceTemplate;
	}

	public String getDocsModuloTemplate() {
		return docsModuloTemplate;
	}

	public String getDocsMetodoTemplate() {
		return docsMetodoTemplate;
	}

	public String getErrorTemplate() {
		return errorTemplate;
	}

	public String getStaticHtmlTemplate() {
		return staticHtmlTemplate;
	}

	public String getRootURL() {		
		return rootURL;
	}
	
	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}
	
	public String asset(String...asset) throws ApiException{
		return getRootURL()+"assets/resources/"+StringUtils.join("/", new ArrayList<String>(Arrays.asList(asset)));
	}
	
}
