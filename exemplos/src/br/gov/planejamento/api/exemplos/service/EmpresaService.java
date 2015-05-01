package br.gov.planejamento.api.exemplos.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class EmpresaService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("teste_bd");
		configs.setTable("empresas");
		configs.setResponseFields("nome","id_empresa","descricao","representante_legal","cnpj");
		configs.setValidOrderByValues("nome","cnpj");
		return configs;
	}

}