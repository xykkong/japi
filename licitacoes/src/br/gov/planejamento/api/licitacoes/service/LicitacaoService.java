package br.gov.planejamento.api.licitacoes.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.database.ConnectionManager;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterJapiException;
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
		ConnectionManager.removeConfiguration();
		ConnectionManager.loadConfiguration("database-properties");
		
		DatabaseData dados = getAllFiltered();
		Response retorno = new Response(dados.getCount());
		
		for(DataRow licitacao : dados) {
			retorno.add(new LicitacaoResource(licitacao));
		}
		
		return retorno;
	}

}