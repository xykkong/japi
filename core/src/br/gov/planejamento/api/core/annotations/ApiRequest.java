package br.gov.planejamento.api.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRequest {
    public String name() default "";
    public String description() default "";
    public String exampleQuery() default "";
	public String exampleId() default "";
}

