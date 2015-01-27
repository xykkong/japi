package br.gov.planejamento.api.licitacoes.request;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.common.constants.LicitacaoConstants;
import br.gov.planejamento.api.common.filters.ZeroFillEqualFilter;
import br.gov.planejamento.api.core.annotations.DocDescription;
import br.gov.planejamento.api.core.annotations.DocParameterField;
import br.gov.planejamento.api.core.base.ResourceList;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.ExpectedParameterNotFoundException;
import br.gov.planejamento.api.core.exceptions.InvalidArgToAddFilter;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.filters.LikeFilter;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;
import br.gov.planejamento.api.licitacoes.service.TesteService;

@Path("/")
public class LicitacaoRequest {

	private LicitacaoService lService = new LicitacaoService();
	private TesteService tService = new TesteService();

	@GET	
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public ResourceList licitacoes() throws SQLException,
			InvalidFilterValueTypeException, InvalidOrderSQLParameterException,
			ParserConfigurationException, SAXException, IOException,
			ExpectedParameterNotFoundException, InvalidOffsetValueException, InvalidArgToAddFilter {
		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(EqualFilter.class, String.class, "uasg");
		currentSession.addFilter(ZeroFillEqualFilter.class, "modalidade",
				"numero_aviso");
		currentSession.addFilter(CaseInsensitiveLikeFilter.class, "nome_uasg");
		ResourceList response = null;

		try {
			currentSession.validateURIParametersUsingFilters();
			response = lService.licitacoes();
		} catch (URIParameterNotAcceptedException
				| InvalidOrderByValueException ex) {
			ex.printStackTrace();
		}

		return response;
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@DocDescription("Lista de pessoas da tabela de testes")
	public ResourceList teste(
			@DocParameterField(name = "idade", required = false, description = "Idade da pessoa") String testeInt,
			@DocParameterField(name = "nome", required = false, description = "Nome da pessoa") String testeString
			)
			throws SQLException, InvalidFilterValueTypeException,
			InvalidOrderSQLParameterException, ParserConfigurationException,
			SAXException, IOException, ExpectedParameterNotFoundException,
			InvalidOffsetValueException, InvalidArgToAddFilter {
		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(EqualFilter.class, Integer.class, "teste_int");
		currentSession.addFilter(CaseInsensitiveLikeFilter.class, "teste_string");

		try {
			currentSession.validateURIParametersUsingFilters();
			return tService.teste();
		} catch (URIParameterNotAcceptedException
				| InvalidOrderByValueException ex) {
			// return ex.getMessage();
		}

		return null;
	}
}