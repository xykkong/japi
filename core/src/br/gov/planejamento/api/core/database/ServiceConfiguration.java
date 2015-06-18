package br.gov.planejamento.api.core.database;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.filters.PrimaryKeyEqualFilter;

public class ServiceConfiguration {
	
	private String schema = "public";
	private String table = "";
	private BasicEqualFilter[] primaryKeyEqualFilters = null;
	private List<String> responseFields = new ArrayList<>();
	private List<DatabaseAlias> validOrderByValues = new ArrayList<>();
		
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
	public List<String> getResponseFields() {
		return responseFields;
	}
	public void setResponseFields(String... responseFields) {
		this.responseFields = new ArrayList<>();
		for(String elem : responseFields){
			this.responseFields.add(elem.toLowerCase().trim());
		}
	}
	public List<String> getValidOrderByStringValues() {
		List<String> list = new ArrayList<>();
		for(DatabaseAlias alias : this.validOrderByValues){
			list.add(alias.getUriName());
		}
		return list;
	}
	public List<DatabaseAlias> getValidOrderByValues() {
		return validOrderByValues;
	}
	public void setValidOrderByValues(String... values) {
		for(String value : values) {
			validOrderByValues.add(DatabaseAlias.fromSpecialString(value));
		}
	}
	public BasicEqualFilter[] getPrimaryKeyEqualFilters() {
		return primaryKeyEqualFilters;
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
