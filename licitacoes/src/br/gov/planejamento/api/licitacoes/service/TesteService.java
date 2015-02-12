package br.gov.planejamento.api.licitacoes.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.Resource;
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
import br.gov.planejamento.api.licitacoes.resource.TesteResource;

public class TesteService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("public");
		configs.setTable("test_table");
		configs.setResponseFields("id", "teste_string", "teste_int",
				"teste_numeric", "teste_date", "teste_time");

		return configs;
	}

	private Resource getResource(DataRow dataRow) {
		TesteResource resource = new TesteResource();
		resource.setTesteDate(dataRow.get("teste_date"));
		resource.setTesteInt(dataRow.get("teste_int"));
		resource.setTesteNumeric(dataRow.get("teste_numeric"));
		resource.setTesteString(dataRow.get("teste_string"));
		resource.setTesteTime(dataRow.get("teste_time"));
		return resource;
	}

	private Response getResourceList(DatabaseData data) {
		Response resources = new Response(data.getCount());
		for (DataRow teste : data) {
			resources.add(getResource(teste));
		}
		return resources;
	}

	public Response teste() throws SQLException,
			InvalidFilterValueTypeJapiException, InvalidOrderSQLParameterJapiException,
			ParserConfigurationException, SAXException, IOException,
			InvalidOrderByValueJapiException, InvalidOffsetValueJapiException {
		ConnectionManager.removeConfiguration();
		ConnectionManager
				.loadConfiguration("database-local-graciano-properties");
		return getResourceList(getData());
	}

	@Override
	public List<String> availableOrderByValues() {
		List<String> values = new ArrayList<String>();
		values.add("id");
		values.add("teste_string");
		values.add("teste_date");
		return values;
	}

}
