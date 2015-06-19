package br.gov.planejamento.api.exemplos.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;
import br.gov.planejamento.api.core.filters.PrimaryKeyEqualFilter;
import br.gov.planejamento.api.core.interfaces.IJoinable;

public class ContratoService extends Service implements IJoinable {
	
	private Service serviceJoin;

	@Override
	public ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("teste_bd");
		configs.setTable("contratos");
		configs.setResponseFields("descricao","id_contrato","status","data_termino",
				"valor_inicial","id_contratante");
		configs.setValidOrderByValues("id_contrato as id","valor_inicial","data_termino");
		configs.setPrimaryKeyEqualFilters(PrimaryKeyEqualFilter.factory(Integer.class, "id_contrato as id_contrato"));
		return configs;
	}
	
	public void setServiceJoin(Service serviceJoin) {
		this.serviceJoin = serviceJoin;
	}

	@Override
	public Service getService() {
		return serviceJoin;
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