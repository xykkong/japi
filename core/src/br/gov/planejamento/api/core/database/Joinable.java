package br.gov.planejamento.api.core.database;

public interface Joinable extends ServiceConfigurationContainer{
	Service getService();
	String joinField();
	String joinFieldReference();
}
