package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

public class Request {

	/**
	 * Current request object created by Resteasy
	 */
	private static Request currentRequest = null;

	/**
	 * All parameters sent in querystring
	 */
	protected Map<String, List<String>> parameters;

	private ArrayList<Filter> filters = new ArrayList<Filter>();

	/**
	 * Constructor that populates the static current variable with the created
	 * Request
	 */
	private Request() {
		currentRequest = this;
	}

	public static Request getCurrentRequest() {
		if (currentRequest == null)
			currentRequest = new Request();
		return currentRequest;
	}

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void addFilter(Class<? extends Filter> type, String parameter) {
		if(this.hasParameter(parameter)){
			try {
				Filter filter = type.newInstance(); //getConstructors()[0].newInstance(null);
				filter.setParameter(parameter);
				filter.setValues(this.getValues(parameter).toArray(new String[0]));
				filters.add(filter);
			} catch (InstantiationException | IllegalAccessException e) {
				//never happens
				e.printStackTrace();
				
			}
		}

	}
	public void addFilter(Class<? extends Filter> type, String...parameters){
		for(String parameter : parameters){
			addFilter(type, parameter);
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

	public boolean hasParameter(String key) {
		return parameters != null && parameters.get(key) != null
				&& parameters.get(key).size() > 0;
	}

	public void clear() {
		parameters = null;
		filters = new ArrayList<Filter>();
	}
}
