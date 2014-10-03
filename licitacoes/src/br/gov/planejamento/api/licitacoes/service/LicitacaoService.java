package br.gov.planejamento.api.licitacoes.service;

import java.sql.SQLException;
import java.util.HashMap;

import br.gov.planejamento.api.core.base.DatabaseData;
import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;

public class LicitacaoService extends Service {
	
	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("dados_abertos");
		configs.setTable("licitacao_view");
		//configs.setResponseFields("teste_string", "teste_int", "teste_numeric", "teste_date", "teste_time");		
		configs.setResponseFields("uasg", "nome_uasg", "modalidade", "nome_modalidade", "numero_aviso");
		
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