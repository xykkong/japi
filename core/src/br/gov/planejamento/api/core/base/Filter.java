package br.gov.planejamento.api.core.base;

import java.util.ArrayList;


public abstract class Filter {
	private ArrayList<String> values;
	protected ArrayList<String> parameters;
	
	
	public ArrayList<String> getValues() {
		return values;
	}
	
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
	public ArrayList<String> getParameters() {
		return parameters;
	}
	
	public void setParameters(ArrayList<String> parameters) {
		this.parameters = parameters;
	}
	
	public abstract String getStatement();
}
