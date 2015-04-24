package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.commons.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Module;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.DateEqualFilter;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.parameters.BooleanParam;
import br.gov.planejamento.api.core.parameters.DateParam;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;
import br.gov.planejamento.api.licitacoes.resource.TesteResource;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;
import br.gov.planejamento.api.licitacoes.service.TesteService;

@Path("/")
@Module(CommonConstants.Modules.LICITACOES)
public class LicitacaoRequest {

	private TesteService tService = new TesteService();

	private LicitacaoService lService = new LicitacaoService();

	@GET
	@About(name = "licitacoes", description = "Lista de licitações", exampleQuery = "?uasg=70024")
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public ResourceListResponse<LicitacaoResource> licitacoes(
			@Parameter(name = "uasg", description = "número UASG da Licitação") String uasg,
			@Parameter(name = "modalidade", description = "Modalidade") String modalidade,
			@Parameter(name = "numero_aviso", description = "Número aviso") String numeroAviso,
			@Parameter(name = "nome_uasg", description = "nome da Uasg") String nomeUasg,
			@Parameter(name = "data_abertura", required = false, description = "Data de abertuda da proposta") String dataAbertura)
			throws ApiException {

		RequestContext.getContext().addFilter(
				new EqualFilter(Integer.class, "uasg as uasg", "modalidade",
						"numero_aviso"),
				new CaseInsensitiveLikeFilter(new DatabaseAlias("nome_uasg")),
				new DateEqualFilter(DateParam.class, new DatabaseAlias(
						"data_abertura_proposta", "data_abertura")));

		DatabaseData dados = lService.getAllFiltered();
		return ResourceListResponse.factory(dados, LicitacaoResource.class);
	}

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES + "teste")
	@About(name = "licitacoesteste", description = "Lista de pessoas da tabela de testes", exampleQuery = "")
	public ResourceListResponse<TesteResource> teste(
			@Parameter(name = LicitacaoConstants.Properties.Names.IDADE, description = LicitacaoConstants.Properties.Description.IDADE) String testeInt,
			@Parameter(name = LicitacaoConstants.Properties.Names.NOME, description = LicitacaoConstants.Properties.Description.NOME) String testeString,
			@Parameter(name = LicitacaoConstants.Properties.Names.NASCIMENTO, description = LicitacaoConstants.Properties.Description.NASCIMENTO) DateParam testeDate,
			@Parameter(name = LicitacaoConstants.Properties.Names.BOOLEAN, description = LicitacaoConstants.Properties.Description.BOOLEAN) BooleanParam testeBoolean)
			throws ApiException {

		// tanto faz usar "dbName as uriName" e new DatabaseAlias("dbName",
		// "uriName")
		RequestContext.getContext().addFilter(
				new EqualFilter(Integer.class, "teste_int as idade"));
		RequestContext.getContext().addFilter(
				new DateEqualFilter(DateParam.class, new DatabaseAlias(
						"teste_date", "nascimento")));
		RequestContext.getContext().addFilter(
				new EqualFilter(Boolean.class, new DatabaseAlias(
						"teste_boolean", "boolean")));

		DatabaseData dados = tService.getAllFiltered();
		return ResourceListResponse.factory(dados, TesteResource.class);
	}

	@GET
	@Path(LicitacaoConstants.Requests.Document.LICITACAO + "teste/{idade}")
	@About(name = "licitacaoteste", description = "Uma pessoa da tabela de testes", exampleId = "666")
	public ResourceResponse<TesteResource> testeUnico(
			@Parameter(name = "idade", required = true, description = "Idade da pessoa") String testeInt)
			throws ApiException {

		DataRow row = tService.getOne();
		return ResourceResponse.factory(row, TesteResource.class);
	}
}