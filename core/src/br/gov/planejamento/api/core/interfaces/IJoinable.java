package br.gov.planejamento.api.core.interfaces;

import br.gov.planejamento.api.core.database.Service;


public interface IJoinable extends IServiceConfigurationAndFiltersContainer{
	Service getService();
	String joinField();
	String joinFieldReference();
}
