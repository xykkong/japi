package br.gov.planejamento.api.licitacoes.request;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.ConnectionManager;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.ResourceList;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.exceptions.ExpectedParameterNotFoundException;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;
import br.gov.planejamento.api.core.exceptions.URIParameterNotAcceptedException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.filters.LikeFilter;
import br.gov.planejamento.api.core.filters.ZeroFillEqualFilter;
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
			ExpectedParameterNotFoundException, InvalidOffsetValueException {
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
	public ResourceList teste() throws SQLException,
			InvalidFilterValueTypeException, InvalidOrderSQLParameterException,
			ParserConfigurationException, SAXException, IOException,
			ExpectedParameterNotFoundException, InvalidOffsetValueException {
		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(EqualFilter.class, Integer.class, "id");
		currentSession.addFilter(LikeFilter.class, "teste_string");

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