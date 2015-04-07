package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class LicitacaoService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("dados_abertos");
		configs.setTable("vw_licitacao");
		configs.setResponseFields("modalidade", "nome_modalidade", "nome_uasg",
				"numero_aviso", "uasg","data_abertura_proposta");
		configs.setValidOrderByValues("uasg","nome_modalidade","numero_aviso", "data_abertura_proposta");
		return configs;
	}

}