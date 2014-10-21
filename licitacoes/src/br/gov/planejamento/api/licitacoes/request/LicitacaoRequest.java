package br.gov.planejamento.api.licitacoes.request;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.exceptions.ExpectedParameterNotFoundException;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
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

	private LicitacaoService service = new LicitacaoService();

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException,
			InvalidFilterValueTypeException, InvalidOrderSQLParameterException,
			ParserConfigurationException, SAXException, IOException, ExpectedParameterNotFoundException {

		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(EqualFilter.class, String.class, "uasg");
		currentSession.addFilter(ZeroFillEqualFilter.class, "modalidade",
				"numero_aviso");
		currentSession.addFilter(CaseInsensitiveLikeFilter.class, "nome_uasg");
		
		try{
			currentSession.validateURIParametersUsingFilters();
		}catch(URIParameterNotAcceptedException uex){
			return uex.getMessage();
		}

		return service.licitacoes();
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	public String teste() throws SQLException, InvalidFilterValueTypeException,
			InvalidOrderSQLParameterException, ParserConfigurationException,
			SAXException, IOException, ExpectedParameterNotFoundException {
		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(EqualFilter.class, Integer.class, "id");
		currentSession.addFilter(LikeFilter.class, "teste_string");

		return (new TesteService()).teste();
	}
}
