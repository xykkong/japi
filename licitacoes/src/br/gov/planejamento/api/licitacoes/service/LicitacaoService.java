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
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;

public class LicitacaoService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("dados_abertos");
		configs.setTable("vw_licitacao");
		configs.setResponseFields("modalidade", "nome_modalidade", "nome_uasg",
				"numero_aviso", "uasg");

		return configs;
	}

	private Response getResourceList(DatabaseData data) {
		Response resources = new Response(data.getCount());
		for (DataRow licitacao : data) {
			resources.add(new LicitacaoResource(licitacao));
		}
		return resources;
	}

	public Response licitacoes() throws SQLException,
			InvalidFilterValueTypeJapiException, InvalidOrderSQLParameterJapiException,
			ParserConfigurationException, SAXException, IOException,
			InvalidOrderByValueJapiException, InvalidOffsetValueJapiException {
		ConnectionManager.removeConfiguration();
		ConnectionManager.loadConfiguration("database-properties");
		return getResourceList(getData());
	}

	@Override
	public List<String> availableOrderByValues() {
		List<String> values = new ArrayList<String>();
		values.add("uasg");
		values.add("nome_modalidade");
		values.add("numero_aviso");
		return values;
	}

}