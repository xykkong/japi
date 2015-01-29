package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Constants.RequestFormats;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
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

	private String path;

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
			List<DatabaseAlias>... parameters)
			throws ExpectedParameterNotFoundException {
		addFilter(filterType, String.class, parameters);
	}

	public void addFilter(Class<? extends Filter> filterType,
			Class<? extends Object> valueType, DatabaseAlias... parameters)
			throws ExpectedParameterNotFoundException {
		ArrayList<List<DatabaseAlias>> parametersList = new ArrayList<List<DatabaseAlias>>();
		for (DatabaseAlias parameter : parameters) {
			List<DatabaseAlias> parameterList = new ArrayList<DatabaseAlias>();
			parameterList.add(parameter);
			parametersList.add(parameterList);
		}
		addFilter(
				filterType,
				valueType,
				parametersList
						.toArray((ArrayList<DatabaseAlias>[]) new ArrayList[parameters.length]));
	}

	public void addFilter(Class<? extends Filter> filterType,
			DatabaseAlias... parameters) throws ExpectedParameterNotFoundException {
		addFilter(filterType, String.class, parameters);
	}
	
	public void addFilter(Class<? extends Filter> filterType,
			Class<? extends Object> valueType,
			List<DatabaseAlias>... databaseAliasesParameters)
			throws ExpectedParameterNotFoundException {
		Session currentSession = Session.getCurrentSession();
		for (List<DatabaseAlias> parameterList : databaseAliasesParameters) {
			try {
				Filter filter = filterType.newInstance();
				List<String> availableParameters = new ArrayList<String>();
				List<String> missingParameters = new ArrayList<String>();
				for (DatabaseAlias parameter : parameterList) {
					if (hasParameter(parameter.getUriName())) {
						availableParameters.add(parameter.getUriName());
						filter.addParameterAlias(parameter);
						filter.addValues(currentSession.getValues(parameter.getUriName()));
						filter.setValueType(valueType);
					} else {
						missingParameters.add(parameter.getUriName());
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
				foundParameter |= filter.getUriParameters().contains(parameter);
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

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
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
}
