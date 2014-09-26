package br.gov.planejamento.api.core.base;

import java.util.ArrayList;
import java.util.HashMap;


public class Request {
	
	/**
	 * Current request object created by Resteasy
	 */
	private static Request currentRequest;
	
	/**
	 * All parameters sent in querystring
	 */
	public HashMap<String, String> parameters = new HashMap<String, String>();
	
	/**
	 * Constructor that populates the static current variable with the created Request
	 */
	protected Request() {
		currentRequest = this;
	}
	
	public static Request getCurrentRequest() {
		return currentRequest;
	}
	
	public ArrayList<Filter> getFilters(){
		ArrayList<Filter> filters = new ArrayList<Filter>();
		return filters;
	}
}
