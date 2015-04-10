package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.licitacoes.resource.OrgaoResource;

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
