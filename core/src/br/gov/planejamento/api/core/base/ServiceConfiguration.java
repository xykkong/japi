package br.gov.planejamento.api.core.base;

import java.util.ArrayList;

public class ServiceConfiguration {
	
	private String table = "";
	private ArrayList<String> responseParameters = new ArrayList<String>();
	private ArrayList<String> requiredParameters = new ArrayList<String>();
	private ArrayList<String> optionalParameters = new ArrayList<String>();
	private ArrayList<String> availableFilters = new ArrayList<String>();
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public ArrayList<String> getResponseParameters() {
		return responseParameters;
	}
	public void setResponseParameters(ArrayList<String> responseParameters) {
		this.responseParameters = responseParameters;
	}
	public ArrayList<String> getRequiredParameters() {
		return requiredParameters;
	}
	public void setRequiredParameters(ArrayList<String> requiredParameters) {
		this.requiredParameters = requiredParameters;
	}
	public ArrayList<String> getOptionalParameters() {
		return optionalParameters;
	}
	public void setOptionalParameters(ArrayList<String> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}
	public ArrayList<String> getAvailableFilters() {
		return availableFilters;
	}
	public void setAvailableFilters(ArrayList<String> availableFilters) {
		this.availableFilters = availableFilters;
	}

	
}
