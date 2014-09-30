package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.HashMap;

public class Request {

	/**
	 * Current request object created by Resteasy
	 */
	private static Request currentRequest = null;

	/**
	 * All parameters sent in querystring
	 */
	protected HashMap<String, String> parameters = new HashMap<String, String>();

	private ArrayList<Filter> filters = new ArrayList<Filter>();

	/**
	 * Constructor that populates the static current variable with the created
	 * Request
	 */
	private Request() {
		currentRequest = this;
	}

	public static Request getCurrentRequest() {
		if(currentRequest == null)
			currentRequest = new Request();
		return currentRequest;
	}

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void addFilter(Filter filter){
		filters.add(filter);
	}
}
