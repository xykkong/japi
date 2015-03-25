package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.GZIP;

import br.gov.planejamento.api.commons.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.Returns;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.DateEqualFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.parameters.DateParam;
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;
import br.gov.planejamento.api.licitacoes.resource.TesteResource;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;
import br.gov.planejamento.api.licitacoes.service.TesteService;


@GZIP
@Path("/")
public class LicitacaoRequest {

	private TesteService tService = new TesteService();
	
	private LicitacaoService lService = new LicitacaoService();

	@GET
	@About(name="licitacoes", description ="Lista de licitações", exampleQuery ="?uasg=1000")
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	@Returns(resource=LicitacaoResource.class)
	public Response licitacoes(
				@Parameter(name = "uasg", required = false, description = "número UASG da Licitação") String uasg,
				@Parameter(name = "modalidade", required = false, description = "Modalidade") String modalidade,
				@Parameter(name = "numero_aviso", required = false, description = "Número aviso") String numeroAviso,
				@Parameter(name = "nome_uasg", required = false, description = "nome da Uasg") String nomeUasg,
				@Parameter(name = "data_abertura", required = false, description = "Data de abertuda da proposta") String dataAbertura
			) throws JapiException {
		
		try {
			RequestContext.getContext().addFilter(
					new EqualFilter(Integer.class, "uasg as uasg", "modalidade", "numero_aviso"),
					new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg")),
					new DateEqualFilter(DateParam.class, new DatabaseAlias("data_abertura_proposta","data_abertura"))
			);
	
			Response response = lService.licitacoes();
			return response;
		} catch (Exception exception) {
			throw new JapiException(exception);
		}
	}

	@GET
	@GZIP
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@About(name="licitacoesteste",description="Lista de pessoas da tabela de testes", exampleQuery="?uasg=1000")
	@Returns(resource=TesteResource.class, isList=true)		
	public Response teste(	@Parameter(name = "idade", required=true, description = "Idade da pessoa") String testeInt,
							@Parameter(name = "nome", required=true, description = "Nome da pessoa") String testeString,
							@Parameter(name = "nascimento", required=true, description = "Nascimento") DateParam testeDate)
							throws JapiException {		
		try {
			RequestContext.getContext().addFilter(new EqualFilter(Integer.class, new DatabaseAlias("teste_int","idade")));
			RequestContext.getContext().addFilter(new DateEqualFilter(DateParam.class, new DatabaseAlias("teste_date","nascimento")));

			return tService.teste();
		} catch (Exception e) {
			throw new JapiException(e);
		}
	}
}