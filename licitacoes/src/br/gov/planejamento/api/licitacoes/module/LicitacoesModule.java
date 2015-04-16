package br.gov.planejamento.api.licitacoes.module;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.annotations.ResponseNotRequired;
import br.gov.planejamento.api.core.base.Module;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.responses.JsonObjectSwaggerResponse;

@Path("/")
@ApplicationPath("/")
public class LicitacoesModule extends Module {
	
	@GET
	@Path(CommonConstants.Docs.LICITACOES)
	@ResponseNotRequired
	public JsonObjectSwaggerResponse getDocs() throws ApiException {
		return extractDocumentation("br.gov.planejamento.api.licitacoes.request");
	}
}

