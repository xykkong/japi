package br.gov.planejamento.api.licitacoes.service;

import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;

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

	public Response licitacoes() throws JapiException {
		
		DatabaseData dados = getAllFiltered();
		Response retorno = new Response(dados.getCount());
		
		for(DataRow licitacao : dados) {
			retorno.add(new LicitacaoResource(licitacao));
		}
		
		return retorno;
	}

}