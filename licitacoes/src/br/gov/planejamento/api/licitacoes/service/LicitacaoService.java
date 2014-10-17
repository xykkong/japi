package br.gov.planejamento.api.licitacoes.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

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
		configs.setSchema("public");
		configs.setTable("licitacoes");
		configs.setResponseFields("nome");

		return configs;
	}
	
	private ResourceList getResourceList (DatabaseData data) {
		ResourceList resources = new ResourceList();
		for(DataRow licitacao : data) {
			LicitacaoResource resource = new LicitacaoResource();
			resource.setModalidade(licitacao.get("modalidade"));
			resource.setNomeModalidade(licitacao.get("nome"));
			resource.setNomeUasg(licitacao.get("nomeUasg"));
			resource.setNumeroAviso(licitacao.get("numeroAviso"));
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