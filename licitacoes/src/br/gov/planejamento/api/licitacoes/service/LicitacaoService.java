package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;

public class LicitacaoService extends Service {
	
	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setTable("test_table");
		configs.setResponseParameters(new String[] {"teste_string", "teste_int", "teste_numeric", "teste_date", "teste_time" });
		return configs;
	}
	
	
}