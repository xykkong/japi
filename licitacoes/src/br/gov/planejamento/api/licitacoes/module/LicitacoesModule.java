package br.gov.planejamento.api.licitacoes.module;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.base.Module;

@Path("/")
@ApplicationPath("/")
public class LicitacoesModule extends Module {
	
	@GET
	@Path(CommonConstants.Docs.LICITACOES)
	public String getDocs() {
		return extractDocumentation("br.gov.planejamento.api.licitacoes.request");
	}
}

