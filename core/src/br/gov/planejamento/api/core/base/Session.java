package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.ExpectedParameterNotFoundException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedException;

public class Session {

	/**
	 * Current request object created by Resteasy
	 */
	private static Session currentSession = null;

	/**
	 * All parameters sent in querystring
	 */
	private Map<String, List<String>> parameters;

	private ArrayList<Filter> filters = new ArrayList<Filter>();
	
	private List<String> availableOrderByValues = new ArrayList<String>();
	
	private String requestFormat = Constants.RequestFormats.HTML;

	private String relativePath;

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
			Class<? extends Object> valueType, List<String>... parameters)
			throws ExpectedParameterNotFoundException {
		Session currentSession = Session.getCurrentSession();
		for (List<String> parameterList : parameters) {
			try {
				Filter filter = filterType.newInstance();
				List<String> availableParameters = new ArrayList<String>();
				List<String> missingParameters = new ArrayList<String>();
				for (String parameter : parameterList) {
					if (hasParameter(parameter)) {
						availableParameters.add(parameter);
						filter.addParameter(parameter);
						filter.addValues(currentSession.getValues(parameter));
						filter.setValueType(valueType);
					} else {
						missingParameters.add(parameter);
					}
				}
				if (!availableParameters.isEmpty()) {
					if (missingParameters.isEmpty()) {
						filters.add(filter);
					} else {
						throw new ExpectedParameterNotFoundException(
								parameterList, availableParameters,
								missingParameters);
					}
				}

			} catch (InstantiationException | IllegalAccessException e) {
				// never happens
				e.printStackTrace();
			}
		}
	}

	public void addFilter(Class<? extends Filter> filterType,
			List<String>... parameters)
			throws ExpectedParameterNotFoundException {
		addFilter(filterType, String.class, parameters);
	}

	public void addFilter(Class<? extends Filter> filterType,
			Class<? extends Object> valueType, String... parameters)
			throws ExpectedParameterNotFoundException {
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
			String... parameters) throws ExpectedParameterNotFoundException {
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
	 * @throws InvalidOrderByValueException 
	 */
	public String getOrderByValue() throws InvalidOrderByValueException {
		if (hasParameter(Constants.FixedParameters.ORDER_BY)) {
			String value = getValue(Constants.FixedParameters.ORDER_BY);
			if(!availableOrderByValues.contains(value))
				throw new InvalidOrderByValueException(value, availableOrderByValues);
			return value;
		}
		return "1";
	}

	public String getOrderValue() throws InvalidOrderSQLParameterException {
		if (hasParameter(Constants.FixedParameters.ORDER)) {
			String order = getValue(Constants.FixedParameters.ORDER);
			if (Arrays.asList(Constants.FixedParameters.VALID_ORDERS).contains(
					order.toUpperCase()))
				return order;
			else
				throw new InvalidOrderSQLParameterException(order);
		}
		return "ASC";
	}
	
	public int getOffsetValue() throws InvalidOffsetValueException {
		if(hasParameter(Constants.FixedParameters.OFFSET)){
			String sOffset = "";
			try{
				sOffset = getValue(Constants.FixedParameters.OFFSET); 
				int offset = Integer.parseInt(sOffset);
				return offset;
			}catch(NumberFormatException ex){
				throw new InvalidOffsetValueException(sOffset);
			}
		}
		return 0;
	}

	public boolean hasParameter(String key) {
		return parameters != null && parameters.get(key) != null
				&& parameters.get(key).size() > 0;
	}

	public void clear() {
		currentSession = null;
	}

	public void validateURIParametersUsingFilters()
			throws URIParameterNotAcceptedException {
		Iterator<String> iterator = parameters.keySet().iterator();
		while (iterator.hasNext()) {
			String parameter = iterator.next();
			boolean foundParameter = false;
			for (Filter filter : filters) {
				foundParameter |= filter.getParameters().contains(parameter);
			}
			foundParameter |= Arrays.asList(Constants.FixedParameters.DEFAULT_URI_PARAMETERS).contains(
						parameter);
			if (!foundParameter)
				throw new URIParameterNotAcceptedException(parameter);
		}
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
		return Session.getCurrentSession().getRequestFormat().equals(format);
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	public String getRelativePath() {
		return relativePath;
	}
}
