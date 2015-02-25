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
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.InvalidArgToAddFilterJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterJapiException;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;
import br.gov.planejamento.api.licitacoes.service.TesteService;

@Path("/")
public class LicitacaoRequest {

	private TesteService tService = new TesteService();
	private LicitacaoService lService = new LicitacaoService();

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public Response licitacoes(
				@DocParameterField(name = "uasg", required = false, description = "número UASG da Licitação") String uasg,
				@DocParameterField(name = "modalidade", required = false, description = "Modalidade") String modalidade,
				@DocParameterField(name = "numero_aviso", required = false, description = "Número aviso") String numeroAviso,
				@DocParameterField(name = "nome_uasg", required = false, description = "nome da Uasg") String nomeUasg				
			) throws JapiException,
			ParserConfigurationException, SAXException, IOException,
			SQLException {
		Session currentSession = Session.getCurrentSession();

		currentSession.addFilter(
				new EqualFilter(Integer.class, new DatabaseAlias("uasg")),
				new ZeroFillEqualFilter(new DatabaseAlias("modalidade"), new DatabaseAlias("numero_aviso")),
				new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg"))
		);

		Response response = null;
		response = lService.licitacoes();

		return response;
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@DocDescription("Lista de pessoas da tabela de testes")
	public Response teste(
			@DocParameterField(name = "idade", required = false, description = "Idade da pessoa") String testeInt,
			@DocParameterField(name = "nome", required = false, description = "Nome da pessoa") String testeString)
			throws SQLException, InvalidFilterValueTypeJapiException,
			InvalidOrderSQLParameterJapiException,
			ParserConfigurationException, SAXException, IOException,
			InvalidOffsetValueJapiException,
			InvalidArgToAddFilterJapiException,
			InvalidOrderByValueJapiException {
		Session currentSession = Session.getCurrentSession();

		// currentSession.addFilter(EqualFilter.class, Integer.class, new
		// DatabaseAlias("teste_int"));
		// currentSession.addFilter(LikeFilter.class, new
		// DatabaseAlias("teste_string", "nome"));

		return tService.teste();
	}
}