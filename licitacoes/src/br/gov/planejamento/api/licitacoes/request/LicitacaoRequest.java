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
	public Response licitacoes() throws JapiException {
		
		try {
			Session.getCurrentSession().addFilter(
					new EqualFilter(Integer.class, new DatabaseAlias("uasg")),
					new ZeroFillEqualFilter(new DatabaseAlias("modalidade"), new DatabaseAlias("numero_aviso")),
					new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg"))
			);
			
			return lService.licitacoes();
		} catch(Exception e) {
			throw new JapiException(e);
		}
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
	}
}