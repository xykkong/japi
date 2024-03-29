package br.gov.planejamento.api.exemplos.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.commons.routers.ExemplosRouter;
import br.gov.planejamento.api.core.annotations.ApiModule;
import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.BasicBetweenFilter;
import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.filters.CaseInsensitiveLikeFilter;
import br.gov.planejamento.api.core.filters.IsNullFilter;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.exemplos.resource.EmpresaResource;
import br.gov.planejamento.api.exemplos.service.EmpresaService;

@Path("/")
@ApiModule(CommonConstants.Modules.EXEMPLOS)
public class EmpresaRequest {	
	
	private EmpresaService eService = new EmpresaService();
	
	@GET
	@ApiRequest(name = "empresas", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?representante_legal=Afrânio")
	@Path(ExemplosRouter.EMPRESAS)
	
	public ResourceListResponse<EmpresaResource> contrato(
			@Parameter(name = "descricao", description = "Descrição breve da empresa") String descricao,
			@Parameter(name = "max_id_empresa", description = "Numero identificador da empresa") String max_id_contrato,
			@Parameter(name = "min_id_empresa", description = "Numero identificador da empresa") String min_id_contrato,
			@Parameter(name = "id_empresa", description = "Numero identificador da empresa") String id_contrato,
			@Parameter(name = "nome", description = "Nome maravilha da empresa") String status,
			@Parameter(name = "cnpj", description = "cnpj da empresa") String dataTermino,
			@Parameter(name = "representante_legal", description = " nome do representante legal da empresa") String representanteLegal,
			@Parameter(name = "sem_representante", description = "Não há representante legal cadastrado?") String semRepresentanteLegal
			)throws ApiException {
			
		eService.addFilter(
				BasicEqualFilter.factory(new DatabaseAlias("id_empresa", "id_empresa")),
				BasicBetweenFilter.factory(new DatabaseAlias("id_empresa", "min_id_empresa"), new DatabaseAlias("id_empresa", "max_id_empresa")),
				CaseInsensitiveLikeFilter.factory("cnpj", "representante_legal", "nome"),
                IsNullFilter.factory(new DatabaseAlias("representante_legal", "sem_representante"))
				);
		DatabaseData dados = eService.getAllFiltered();
		return ResourceListResponse.factory(dados, EmpresaResource.class);
	
	}

	
}