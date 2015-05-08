package br.gov.planejamento.api.exemplos.service;

import br.gov.planejamento.api.core.database.Joinable;
import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class ContratoService extends Service implements Joinable {

	@Override
	public ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("teste_bd");
		configs.setTable("contratos");
		configs.setResponseFields("descricao","id_contrato","status","data_termino",
				"valor_inicial","id_contratante");
		configs.setValidOrderByValues("id_contrato","valor_inicial","data_termino");
		return configs;
	}

	@Override
	public Service joinnable() {
		return new EmpresaService();
	}

	@Override
	public String joinField() {
		return "id_contratante";
	}

	@Override
	public String joinFieldReference() {
		return "id_empresa";
	}

}