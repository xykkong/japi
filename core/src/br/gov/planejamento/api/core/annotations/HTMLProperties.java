package br.gov.planejamento.api.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.gov.planejamento.api.core.constants.Constants;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HTMLProperties {
	
	public String[] value() default { };
	
	public String extension() default Constants.RequestFormats.HTML;
}
