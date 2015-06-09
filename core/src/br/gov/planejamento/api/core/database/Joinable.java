package br.gov.planejamento.api.core.database;

import java.util.List;

public interface Joinable extends ServiceConfigurationContainer{
	Service getService();
	List<Filter> getFilters();
	String joinField();
	String joinFieldReference();
}
