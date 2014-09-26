package br.gov.planejamento.api.licitacoes.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import br.gov.planejamento.api.core.base.DatabaseData;
import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;

public class LicitacaoService extends Service {
	
	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setTable("test_table");
		configs.setResponseFields("teste_string", "teste_int", "teste_numeric", "teste_date", "teste_time");		
		return configs;
	}
	
	public String licitacoes() throws SQLException {
		DatabaseData data = getData();
		StringBuilder sb = new StringBuilder();
		for(HashMap<String, String> map : data) {
			for(String value : map.values()) {
				sb.append(value);
				sb.append("<br>");
			}
		}
		return sb.toString();
	}
	
}