package br.gov.planejamento.api.exemplos.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.BaseConstants;
import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.annotations.ApiRequest;
import br.gov.planejamento.api.core.annotations.ApiModule;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.database.DatabaseData;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.exemplos.resource.BaseResource;
import br.gov.planejamento.api.exemplos.resource.EmpresaResource;
import br.gov.planejamento.api.exemplos.service.EmpresaService;

@Path("/")
@ApiModule(CommonConstants.Modules.EXEMPLOS)
public class EmpresaRequest {	
	
	private EmpresaService eService = new EmpresaService();
	
	@GET
	@ApiRequest(name = "empresas", description = "Este é um módulo de exemplo sem real utilidade", exampleQuery = "?representante_legal=Afrânio")
	@Path(BaseConstants.Requests.List.BASE)
	
	public ResourceListResponse<BaseResource> contrato(
			@Parameter(name = "descricao", description = "Descrição breve da empresa") String descricao,
			@Parameter(name = "id_empresa", description = "Numero identificador da empresa") String id_contrato,
			@Parameter(name = "nome", description = "Nome maravilha da empresa") String status,
			@Parameter(name = "cnpj", description = "cnpj da empresa") String dataTermino,
			@Parameter(name = "representante_legal", description = " nome do representante legal da empresa") String representanteLegal
			)throws ApiException {
			
		DatabaseData dados = eService.getAllFiltered();
		return ResourceListResponse.factory(dados, EmpresaResource.class);
	
	}

	
}