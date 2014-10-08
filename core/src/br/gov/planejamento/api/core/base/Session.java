package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

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

	public void addFilter(Class<? extends Filter> filterType, Class<? extends Object> valueType, List<String>... parameters) {
		String p[] = new String[10];
		for(List<String> parameterList : parameters) {
			
			Boolean hasAllParameters = true;
			List<List<String>> valueList = new ArrayList<List<String>>();
			for(String parameter : parameterList) {
				if(hasParameter(parameter)) {
					valueList.add(getValues(parameter));
				} else {
					hasAllParameters = false;
				}
			}			
			
			//todo: Tratar caso em que nem todos os parâmetros necessários foram passados
			if(hasAllParameters){
				try {
					Filter filter = filterType.newInstance();
					filter.setParameters(parameterList);
					filter.setValues(valueList);
					filters.add(filter);
				} catch (InstantiationException | IllegalAccessException e) {
					//never happens
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addFilter(Class<? extends Filter> filterType, List<String>... parameters){
		addFilter(filterType, String.class, parameters);
	}
	
	public void addFilter(Class<? extends Filter> filterType, Class<? extends Object> valueType, String... parameters) {
		ArrayList<List<String>> parametersList = new ArrayList<List<String>>();
		for(String parameter : parameters) {
			List<String> parameterList = new ArrayList<String>();
			parameterList.add(parameter);
			parametersList.add(parameterList);
		}
		addFilter(filterType, valueType, parametersList.toArray((ArrayList<String>[]) new ArrayList[parameters.length]));
	}
	
	public void addFilter(Class<? extends Filter> filterType, String... parameters) {
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

	public boolean hasParameter(String key) {
		return parameters != null && parameters.get(key) != null
				&& parameters.get(key).size() > 0;
	}

	public void clear() {
		parameters = null;
		filters = new ArrayList<Filter>();
	}
}
