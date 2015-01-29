package br.gov.planejamento.api.core.annotations;


public @interface Docs {

	public String description() default "";
	public boolean required() default false;
	public String name() default "";
}
