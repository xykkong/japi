package br.gov.planejamento.api.licitacoes.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.common.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.Description;
import br.gov.planejamento.api.core.annotations.MethodName;
import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.annotations.ResourceType;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.licitacoes.resource.LicitacaoResource;
import br.gov.planejamento.api.licitacoes.resource.OrgaoResource;
import br.gov.planejamento.api.licitacoes.service.OrgaoService;


@Path("/")
public class OrgaoRequest {
		
	private OrgaoService oService = new OrgaoService();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.ORGAOS)
	@MethodName("orgaos")
	@ResourceType(OrgaoResource.class)
	@Description("Lista de orgãos")
	public Response orgaos(
				@Parameter(name = "tipo_adm", required = false, description ="Código do tipo da administração do órgão") String tipo_adm,
				@Parameter(name = "ativo", required = false, description ="Se o órgão está ativo.") String ativo
			) throws JapiException {
		
		try {
			Session currentSession = Session.getCurrentSession();
			currentSession.addFilter(
					new EqualFilter(Integer.class, new DatabaseAlias("codigo_tipo_adm", "tipo_adm")),
					new EqualFilter(Boolean.class, new DatabaseAlias("ativo"))
					);
	
			Response response = null;
			response = oService.orgaos();
			return response;
		} catch (Exception exception) {
			throw new JapiException(exception);
		}
	}
		
	

}
