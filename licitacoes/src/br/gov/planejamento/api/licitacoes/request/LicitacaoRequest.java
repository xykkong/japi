package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.common.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.annotations.MethodName;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.Returns;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
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
	@MethodName("licitacoes")
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	@Returns(LicitacaoResource.class)
	@Description("Lista de licitacoes")
	public Response licitacoes(
				@Parameter(name = "uasg", required = false, description = "número UASG da Licitação") String uasg,
				@Parameter(name = "modalidade", required = false, description = "Modalidade") String modalidade,
				@Parameter(name = "numero_aviso", required = false, description = "Número aviso") String numeroAviso,
				@Parameter(name = "nome_uasg", required = false, description = "nome da Uasg") String nomeUasg				
			) throws JapiException {
		
		try {
			Session currentSession = Session.getCurrentSession();
			currentSession.addFilter(
					new EqualFilter(Integer.class, new DatabaseAlias("uasg"), new DatabaseAlias("modalidade"), new DatabaseAlias("numero_aviso")),
					new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg"))
			);
	
			Response response = lService.licitacoes();
			return response;
		} catch (Exception exception) {
			throw new JapiException(exception);
		}
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@MethodName("licitacoesteste")
	@ResourceType(TesteResource.class)
	@Description("Lista de pessoas da tabela de testes")	
	public Response teste(	@Parameter(name = "idade", required=true, description = "Idade da pessoa") String testeInt,
							@Parameter(name = "nome", required=true, description = "Nome da pessoa") String testeString)
							throws JapiException {		
		try {
			Session.getCurrentSession().addFilter(new EqualFilter(Integer.class, new DatabaseAlias("teste_int")));
			return tService.teste();
		} catch (Exception e) {
			throw new JapiException(e);
		}
	}
}