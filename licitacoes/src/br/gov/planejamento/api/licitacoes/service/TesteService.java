package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.filters.PrimaryKeyEqualFilter;

public class TesteService extends Service {

	@Override
	public ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("public");
		configs.setTable("test_table");
		configs.setResponseFields("id", "teste_string", "teste_int",
				"teste_numeric", "teste_date", "teste_time", "teste_boolean");
		configs.setValidOrderByValues("id","teste_string","teste_date");
		configs.setPrimaryKeyEqualFilters(PrimaryKeyEqualFilter.factory(Integer.class, "teste_int as idade"));

		return configs;
	}

}
