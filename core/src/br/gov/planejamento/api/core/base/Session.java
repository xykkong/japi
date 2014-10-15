package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;

public class Session {

	/**
	 * Current request object created by Resteasy
	 */
	private static Session currentSession = null;

	/**
	 * All parameters sent in querystring
	 */
	protected Map<String, List<String>> parameters;

	private ArrayList<Filter> filters = new ArrayList<Filter>();

	/**
	 * Constructor that populates the static current variable with the created
	 * Request
	 */
	private Session() {
		currentSession = this;
	}

	public static Session getCurrentSession() {
		if (currentSession == null)
			currentSession = new Session();
		return currentSession;
	}

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void addFilter(Class<? extends Filter> filterType,
			Class<? extends Object> valueType, List<String>... parameters) {
		for (List<String> parameterList : parameters) {
			for (String parameter : parameterList) {
				// TODO tratar falta de parâmetros
				if (hasParameter(parameter)) {
					try {
						Filter filter = filterType.newInstance();
						filter.addParameter(parameter);
						filters.add(filter);
					} catch (InstantiationException | IllegalAccessException e) {
						// never happens
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void addFilter(Class<? extends Filter> filterType,
			List<String>... parameters) {
		addFilter(filterType, String.class, parameters);
	}

	public void addFilter(Class<? extends Filter> filterType,
			Class<? extends Object> valueType, String... parameters) {
		ArrayList<List<String>> parametersList = new ArrayList<List<String>>();
		for (String parameter : parameters) {
			List<String> parameterList = new ArrayList<String>();
			parameterList.add(parameter);
			parametersList.add(parameterList);
		}
		addFilter(
				filterType,
				valueType,
				parametersList
						.toArray((ArrayList<String>[]) new ArrayList[parameters.length]));
	}

	public void addFilter(Class<? extends Filter> filterType,
			String... parameters) {
		addFilter(filterType, String.class, parameters);
	}

	public void putValues(MultivaluedMap<String, String> multivaluedMap) {
		parameters = multivaluedMap;
	}

	public List<String> getValues(String key) {
		if (hasParameter(key))
			return parameters.get(key);
		return null;
	}
	
	/**
	 * 
	 * @param key referente ao valor do parâmetro GET
	 * @return primeiro valor da lista de valores deste parâmetro, se existir
	 */
	public String getValue(String key){
		if(hasParameter(key)){
			return parameters.get(key).get(0); 
		}
		return null;
	}

	public int getPage() {
		List<String> pageValues = getValues(Constants.FixedParameters.PAGINATION);
		return hasParameter(Constants.FixedParameters.PAGINATION) ? 
				Integer.parseInt(pageValues.get(0)) : 1;
	}
	
	/**
	 * 
	 * @return valor de order_by da URI ou "1", se não existir
	 */
	public String getOrderByValue(){
		if(hasParameter(Constants.FixedParameters.ORDER_BY)){
			return getValue(Constants.FixedParameters.ORDER_BY);
		}
		return "1";
	}
	
	public String getOrderValue() throws InvalidOrderSQLParameterException{
		if(hasParameter(Constants.FixedParameters.ORDER)){
			String order = getValue(Constants.FixedParameters.ORDER);
			if(Arrays.asList(Constants.FixedParameters.VALID_ORDERS).contains(order.toUpperCase()))
				return order;
			else
				throw new InvalidOrderSQLParameterException(order);
		}
		return "ASC";
	}

	public boolean hasParameter(String key) {
		return parameters != null && parameters.get(key) != null
				&& parameters.get(key).size() > 0;
	}

	public void clear() {
		parameters = null;
		filters = new ArrayList<Filter>();
	}
}
