package br.gov.planejamento.api.exemplos.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.annotations.ApiModule;
import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.database.ServiceJoinner;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.DateEqualFilter;
import br.gov.planejamento.api.core.parameters.BooleanParam;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;
import br.gov.planejamento.api.exemplos.resource.ContratoJoinEmpresaResource;
import br.gov.planejamento.api.exemplos.resource.ContratoResource;
import br.gov.planejamento.api.exemplos.service.ContratoService;

@Path("/")
@ApiModule(CommonConstants.Modules.EXEMPLOS)
public class ContratoRequest {	
	
	private static ContratoService contratoService = new ContratoService();
		
	@GET
	@ApiRequest(name = "contratos", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?status=false")
	@Path(ExemplosRouter.CONTRATOS)
	
	public ResourceListResponse<ContratoResource> contrato(
			@Parameter(name = "status", description = "Determina se o contrato ainda está ativo") String status,
			@Parameter(name = "data_termino", description = "Dia em que o contrato expira") String dataTermino,
			@Parameter(name = "valor_inicial", description = "Valor inicial do contrato.") String valorInicial)
			throws ApiException {
	
		contratoService.addFilter(
				BasicEqualFilter.factory(Integer.class, "numero","id_contrato"),
				CaseInsensitiveLikeFilter.factory(
						"cnpj_contratante", "cnpj_contratada","valor_inicial"
						),
				DateEqualFilter.factory( "data_termino"),
				BasicEqualFilter.factory(BooleanParam.class, "status")
				);
		DatabaseData dados = contratoService.getAllFiltered();
		return ResourceListResponse.factory(dados, ContratoResource.class);
	}
	
	@GET
	@ApiRequest(name = "contrato", description = "Pega um contrato pelo identificador único do mesmo.", exampleId = "1392")
	@Path(ExemplosRouter.CONTRATO)
	public ResourceResponse<ContratoResource> contrato(
			@Parameter(name = "id_contrato", description = "Numero identificador do contrato") String id_contrato
		)
			throws ApiException {
		return ResourceResponse.factory(contratoService.getOne(), ContratoResource.class);
	}
	
	@GET
	@ApiRequest(name = "contratos", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?status=false")
	@Path(ExemplosRouter.CONTRATOS_JOIN_EMPRESAS)
	public ResourceListResponse<ContratoJoinEmpresaResource> contratoJoinEmpresa(
			@Parameter(name = "descricao", description = "Descrição breve do contrato") String descricao,
			@Parameter(name = "id_contrato", description = "Numero identificador do contrato") String id_contrato,
			@Parameter(name = "status", description = "Determina se o contrato ainda está ativo") String status,
			@Parameter(name = "data_termino", description = "Dia em que o contrato expira") String dataTermino,
			@Parameter(name = "valor_inicial", description = "Valor inicial do contrato.") String valorInicial,
			/*JOIN*/
			@Parameter(name = "cnpj_contratante", description = "Numero do CNPJ da empresa contratante") String cnpjContratante,
			@Parameter(name = "nome_contratante", description = "Nome da empresa que relacionada a este contrato") String nomeContratante
			) throws ApiException{
		
		
		ServiceJoinner serviceJoinner = new ServiceJoinner(contratoService);
		
		return ResourceListResponse.factory(serviceJoinner.getAllFiltered(), ContratoJoinEmpresaResource.class);
	}

	
}