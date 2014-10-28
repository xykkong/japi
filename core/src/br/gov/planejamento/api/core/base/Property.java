package br.gov.planejamento.api.core.base;

public class Property {
	protected String name;	
	protected String value;	
	
	public Property(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString(){
		return value;
	}
}
