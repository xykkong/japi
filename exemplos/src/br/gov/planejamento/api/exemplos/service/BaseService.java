package br.gov.planejamento.api.exemplos.service;

import br.gov.planejamento.api.core.database.Service;
import br.gov.planejamento.api.core.database.ServiceConfiguration;

public class BaseService extends Service {
	
	 /*
     * O método getServiceConfiguration é o responsável por configurar a conexão do seu Service com o Banco de dados a ele relacionado.
     *
     * Como cada Service será relacionado a uma tabela do banco de dados, é a partir deste método que definimos qual tabela é essa, a qual schema ela pertence e quais os campo dela nosso service utilizará para montar a Response.
     *
     * As queries criadas pelo Service (classe abstrata) utilizam as informações inseridas aqui.
     */

	@Override
	public ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("nome_do_schema_do_banco");
		configs.setTable("tabela_à_ser_acessada");
		configs.setResponseFields("nome_da_coluna_1_na_tabela", "nome_da_coluna_2_na_tabela", "nome_da_coluna_x_na_tabela");
		/*Determinar quais campos aceitarão o parametro de pesquisa "order_by"*/
		configs.setValidOrderByValues("nome_da_coluna_1_na_tabela", "nome_da_coluna_x_na_tabela");
		return configs;
	}

}