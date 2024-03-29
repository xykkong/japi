package br.gov.planejamento.api.exemplos.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.annotations.ApiModule;
import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.ServiceJoiner;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.DateEqualFilter;
import br.gov.planejamento.api.core.filters.FloatEqualFilter;
import br.gov.planejamento.api.core.parameters.BooleanParam;
import br.gov.planejamento.api.core.parameters.DateParam;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.exemplos.resource.ContratoJoinEmpresaResource;
import br.gov.planejamento.api.exemplos.resource.ContratoResource;
import br.gov.planejamento.api.exemplos.service.ContratoService;
import br.gov.planejamento.api.exemplos.service.EmpresaService;

@Path("/")
@ApiModule(CommonConstants.Modules.EXEMPLOS)
public class ContratoRequest {	
	
	private static ContratoService contratoService = new ContratoService();
		
	@GET
	@ApiRequest(name = "contratos", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?status=false")
	@Path(ExemplosRouter.CONTRATOS)
	public ResourceListResponse<ContratoResource> contrato(
			@Parameter(name = "status", description = "Determina se o contrato ainda está ativo") String status,
			@Parameter(name = "data_termino", description = "Dia em que o contrato expira") DateParam dataTermino,
			@Parameter(name = "valor_inicial", description = "Valor inicial do contrato.") String valorInicial,
			@Parameter(name = "d", description = "Breve descrição do contrato.") String descricao)
			throws ApiException {
		contratoService = new ContratoService();
		
		contratoService.addFilter(
				CaseInsensitiveLikeFilter.factory("\"descricao\" as d"),
                FloatEqualFilter.factory(0.3, "valor_inicial"),
				DateEqualFilter.factory( "data_termino"),
				BasicEqualFilter.factory(BooleanParam.class, "status")
				);
		DatabaseData dados = contratoService.getAllFiltered();
		return ResourceListResponse.factory(dados, ContratoResource.class);
	}
	
	@GET
	@ApiRequest(name = "contrato", description = "Pega um contrato pelo identificador único do mesmo.", exampleParams = {"1392"})
	@Path(ExemplosRouter.CONTRATO)
	public ResourceResponse<ContratoResource> contrato(
			@Parameter(name = "id_contrato", description = "Numero identificador do contrato") String id_contrato
		)
			throws ApiException {
		contratoService = new ContratoService();
		return ResourceResponse.factory(contratoService.getOne(), ContratoResource.class);
	}
	
	@GET
	@ApiRequest(name = "contratosjoin", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?status=false")
	@Path(ExemplosRouter.CONTRATOS_JOIN_EMPRESAS)
	public ResourceListResponse<ContratoJoinEmpresaResource> contratoJoinEmpresa(
			@Parameter(name = "descricao", description = "Descrição breve do contrato") String descricao,
			@Parameter(name = "status", description = "Determina se o contrato ainda está ativo") String status,
			@Parameter(name = "data_termino", description = "Dia em que o contrato expira") String dataTermino,
			@Parameter(name = "valor_inicial", description = "Valor inicial do contrato.") String valorInicial,
			/*JOIN*/
			@Parameter(name = "nome", description = "Nome da empresa que relacionada a este contrato") String nomeContratante
			) throws ApiException{
		
		contratoService = new ContratoService();
		
		contratoService.addFilter(
				CaseInsensitiveLikeFilter.factory("descricao"),
				DateEqualFilter.factory( "data_termino"),
				BasicEqualFilter.factory(BooleanParam.class, "status"),
				BasicEqualFilter.factory(Float.class, "valor_inicial")
				);
		EmpresaService eService = new EmpresaService();
		eService.addFilter(CaseInsensitiveLikeFilter.factory("nome"));
		contratoService.setServiceJoin(eService);
		ServiceJoiner serviceJoinner = new ServiceJoiner(contratoService);
		
		return ResourceListResponse.factory(serviceJoinner.getAllFiltered(), ContratoJoinEmpresaResource.class);
	}

	
}