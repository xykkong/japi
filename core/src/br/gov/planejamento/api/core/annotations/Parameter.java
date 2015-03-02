package br.gov.planejamento.api.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
	
	public String name() default "";
	public boolean required() default false;
	public String description() default "";

}
