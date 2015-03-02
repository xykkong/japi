package br.gov.planejamento.api.core.annotations;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ResourceType {

	public Class value();
}
