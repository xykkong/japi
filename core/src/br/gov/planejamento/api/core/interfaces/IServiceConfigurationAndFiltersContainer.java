package br.gov.planejamento.api.core.interfaces;

import java.util.List;

import br.gov.planejamento.api.core.database.Filter;

public interface IServiceConfigurationAndFiltersContainer extends IServiceConfigurationContainer{
	List<Filter> getFilters();
}
