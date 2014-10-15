package br.gov.planejamento.api.licitacoes.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.DatabaseData;
import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;

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

	public String teste() throws SQLException, InvalidFilterValueTypeException,
			InvalidOrderSQLParameterException, ParserConfigurationException,
			SAXException, IOException {
		DatabaseData data = getData();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (HashMap<String, String> map : data) {
			sb.append("{");
			for (Entry<String, String> entry : map.entrySet()) {
				sb.append("\"");
				sb.append(entry.getKey());
				sb.append("\": \"");
				sb.append(entry.getValue());
				sb.append("\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},<br>");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("}");
		return sb.toString();
	}

}