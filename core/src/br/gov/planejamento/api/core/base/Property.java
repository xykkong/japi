package br.gov.planejamento.api.core.base;

import br.gov.planejamento.api.core.interfaces.IMask;


public class Property<T extends Object> {
	
	protected String id;
	protected String name;	
	protected String value;	
	protected String unmaskedValue;
	
	public Property(String name) {
		this.name = name;
	}
	
	public Property(String name, String value) {
		this(name);
		this.value = value;
	}
	
	public Property(String name, String value, IMask mask) {
		this(name);
		this.unmaskedValue = value;
		this.value = mask.apply(value);
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
	
	@Override
	public String toString(){
		return this.value;
		
	}
}
