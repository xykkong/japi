package br.gov.planejamento.api.licitacoes.service;

import java.io.IOException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.DataRow;
import br.gov.planejamento.api.core.base.DatabaseData;
import br.gov.planejamento.api.core.base.ResourceList;
import br.gov.planejamento.api.core.base.Service;
import br.gov.planejamento.api.core.base.ServiceConfiguration;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;

public class LicitacaoService extends Service {

	@Override
	protected ServiceConfiguration getServiceConfiguration() {
		ServiceConfiguration configs = new ServiceConfiguration();
		configs.setSchema("dados_abertos");
		configs.setTable("vw_licitacao");
		configs.setResponseFields("modalidade", "nome_modalidade", "nome_uasg",
				"numero_aviso", "uasg");

		return configs;
	}

	private ResourceList getResourceList(DatabaseData data) {
		ResourceList resources = new ResourceList();
		for (DataRow licitacao : data) {
			LicitacaoResource resource = new LicitacaoResource();
			resource.setModalidade(licitacao.get("modalidade"));
			resource.setNomeModalidade(licitacao.get("nome_modalildade"));
			resource.setNomeUasg(licitacao.get("nome_uasg"));
			resource.setNumeroAviso(licitacao.get("numero_aviso"));
			resource.setUasg(licitacao.get("uasg"));
			resources.add(resource);
		}
		return resources;
	}

	public String licitacoes() throws SQLException,
			InvalidFilterValueTypeException, InvalidOrderSQLParameterException,
			ParserConfigurationException, SAXException, IOException {
		return getResourceList(getData()).build();
	}

}