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

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public void putValues(MultivaluedMap<String, String> multivaluedMap) {
		parameters = multivaluedMap;
	}

	public String getParameter(String key) {
		if (hasParameter(key))
			return parameters.get(key).get(0);
		// TODO descobrir porque isso funciona, como é o esquema da List de
		// String
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
