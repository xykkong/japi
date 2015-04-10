package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.licitacoes.resource.TesteResource;

public class TesteService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("public");
		configs.setTable("test_table");
		configs.setResponseFields("id", "teste_string", "teste_int",
				"teste_numeric", "teste_date", "teste_time", "teste_boolean");
		configs.setValidOrderByValues("id","teste_string","teste_date");

		return configs;
	}

}
