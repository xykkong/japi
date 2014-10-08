package br.gov.planejamento.api.licitacoes.request;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

@Path("/")
public class LicitacaoRequest {

	private LicitacaoService service = new LicitacaoService();
	//TODO ver se esta constante vai ficar no requestHandler mesmo

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {	
		
		Session currentSession = Session.getCurrentSession();
		
		currentSession.addFilter(EqualFilter.class, "teste_string", "test", "teste");
		//currentRequest.addFilter(LikeFilter.class, "teste_string");

		return service.licitacoes();
	}
	
	public String teste(List<String>... p) {
		return p.getClass().toString();
	}
}
