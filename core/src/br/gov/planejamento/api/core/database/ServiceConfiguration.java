package br.gov.planejamento.api.core.database;

import java.util.ArrayList;
import java.util.Arrays;

import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.filters.PrimaryKeyEqualFilter;

public class ServiceConfiguration {
	
	private String schema = "public";
	private String table = "";
	private BasicEqualFilter[] primaryKeyEqualFilters = null;
	private ArrayList<String> responseFields = new ArrayList<String>();
	private ArrayList<String> requiredParameters = new ArrayList<String>();
	private ArrayList<String> optionalParameters = new ArrayList<String>();
	private ArrayList<String> availableFilters = new ArrayList<String>();
	private ArrayList<String> validOrderByValues = new ArrayList<String>();
		
	public String getSchema(){
		return schema;
	}
	public void setSchema(String schema){
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public ArrayList<String> getResponseFields() {
		return responseFields;
	}
	public void setResponseFields(String... responseFields) {
		this.responseFields = new ArrayList<String>(Arrays.asList(responseFields));
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
	public ArrayList<String> getValidOrderByValues() {
		return this.validOrderByValues;
	}
	public void setValidOrderByValues(String... values) {
		for(String value : values) {
			validOrderByValues.add(value);
		}
	}
	public BasicEqualFilter[] getPrimaryKeyEqualFilters() {
		return primaryKeyEqualFilters;
	}
	public void setPrimaryKeyEqualFilters(PrimaryKeyEqualFilter primaryKeyEqualFilter) {
		this.primaryKeyEqualFilters[0] = primaryKeyEqualFilter;
	}
	public void setPrimaryKeyEqualFilters(PrimaryKeyEqualFilter...primaryKeyEqualFilters) {
		this.primaryKeyEqualFilters = primaryKeyEqualFilters;
	}
	public void appendSchemaDotTable(StringBuilder query) {
		query.append(getSchema());
		query.append(".");
		query.append(getTable());
	}
	
	
}
