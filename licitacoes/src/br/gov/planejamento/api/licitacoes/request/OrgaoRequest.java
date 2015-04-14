package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.About;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.parameters.BooleanParam;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.licitacoes.resource.OrgaoResource;
import br.gov.planejamento.api.licitacoes.service.OrgaoService;


@Path("/")
public class OrgaoRequest {
		
	private OrgaoService oService = new OrgaoService();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.ORGAOS)
	@About(name="orgaos", description ="Lista de órgãos emissores de licitações", exampleQuery ="?tipo_adm=1")
	public ResourceListResponse<OrgaoResource> orgaos(
				@Parameter(name = "tipo_adm", required = false, description ="Código do tipo da administração do órgão") String tipo_adm,
				@Parameter(name = "ativo", required = false, description ="Se o órgão está ativo.") String ativo
			) throws ApiException {
		RequestContext context = RequestContext.getContext();
		context.addFilter(
				new EqualFilter(Integer.class, "codigo_tipo_adm as tipo_adm"),
				new EqualFilter(BooleanParam.class, "ativo")
				);

		ResourceListResponse<OrgaoResource> response = null;
		response = ResourceListResponse.factory(oService.getAllFiltered(), OrgaoResource.class);
		return response;
	}

}
