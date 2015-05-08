package br.gov.planejamento.api.core.database;

public interface Joinable extends ServiceConfigurationContainer{
	Service joinnable();
	String joinField();
	String joinFieldReference();
}
