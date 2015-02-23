package br.gov.planejamento.api.core.base;


public class Property {
	
	protected String id;
	protected String name;	
	protected String value;	
	
	public Property(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		if(value == null) return "-";
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
