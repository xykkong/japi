package br.gov.planejamento.api.licitacoes.module;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Module;

@Path("/")
@ApplicationPath("/")
public class LicitacoesModule extends Module {
	
	@GET
	@Path("/v1/licitacoesdocs")
	public String getDocs() {
		return extractDocumentation("br.gov.planejamento.api.licitacoes.request");
	}
}

