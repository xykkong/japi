package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class OrgaoService extends Service{

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("dados_abertos");
		configs.setTable("vw_orgao");
		configs.setResponseFields("codigo_tipo_adm","ativo");
		configs.setValidOrderByValues("codigo_tipo_adm","ativo");
		return configs;		
	}

}
