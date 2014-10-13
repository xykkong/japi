package br.gov.planejamento.api.licitacoes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.base.DatabaseData;
import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;

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
	
	public String licitacoes() throws SQLException, InvalidFilterValueTypeException {
		DatabaseData data = getData();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(HashMap<String, String> map : data) {
			sb.append("{");
			for(Entry<String, String> entry : map.entrySet()) {
				sb.append("\"");
				sb.append(entry.getKey());
				sb.append("\": \"");
				sb.append(entry.getValue());
				sb.append("\",");
			}
			//sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append("},<br>");
		}
		//sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("}");
		return sb.toString();
	}
	
}