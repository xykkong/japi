package br.gov.planejamento.api.docs.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class LicitacoesShowDocs {
	@GET
	@Path("/")
	public String licitacoes(){
		return "oi oi oi";	
	}

}
