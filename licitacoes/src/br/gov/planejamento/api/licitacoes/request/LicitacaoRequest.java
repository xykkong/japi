package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.common.constants.LicitacaoConstants;
import br.gov.planejamento.api.common.filters.ZeroFillEqualFilter;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.ResourceType;
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
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;
import br.gov.planejamento.api.licitacoes.resource.TesteResource;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;
import br.gov.planejamento.api.licitacoes.service.TesteService;

@Path("/")
public class LicitacaoRequest {

	private TesteService tService = new TesteService();
	private LicitacaoService lService = new LicitacaoService();

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	@ResourceType(LicitacaoResource.class)
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
				new EqualFilter(Integer.class, new DatabaseAlias("uasg"), new DatabaseAlias("modalidade"), new DatabaseAlias("numero_aviso")),
				new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg"))
		);

		Response response = lService.licitacoes();
		return response;
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@ResourceType(TesteResource.class)
	@Description("Lista de pessoas da tabela de testes")	
	public Response teste(	@Parameter(name = "idade", description = "Idade da pessoa") String testeInt,
							@Parameter(name = "nome", description = "Nome da pessoa") String testeString)
							throws Exception {		
		try {
			return tService.teste();
		} catch (Exception e) {
			throw new JapiException(e);
		}
		// currentSession.addFilter(EqualFilter.class, Integer.class, new
		// DatabaseAlias("teste_int"));
		// currentSession.addFilter(LikeFilter.class, new
		// DatabaseAlias("teste_string", "nome"));

		return tService.teste();
	}
}