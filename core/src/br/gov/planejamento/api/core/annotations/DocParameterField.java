package br.gov.planejamento.api.core.annotations;

public @interface DocParameterField {
	
	public String name();
	public boolean required();
	public String description();

}
