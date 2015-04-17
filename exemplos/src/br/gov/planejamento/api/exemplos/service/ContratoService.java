package br.gov.planejamento.api.exemplos.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class ContratoService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("teste_bd");
		configs.setTable("contratos");
		configs.setResponseFields("descricao","id_contrato","status","data_termino",
				"valor_inicial","cnpj_contratante","cnpj_contratada");
		configs.setValidOrderByValues("id_contrato","valor_inicial","data_termino");
		return configs;
	}

}