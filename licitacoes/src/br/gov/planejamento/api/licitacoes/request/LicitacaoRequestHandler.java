package br.gov.planejamento.api.licitacoes.request;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Request;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

@Path("/")
public class LicitacaoRequestHandler {

	private LicitacaoService service = new LicitacaoService();
	//TODO ver se esta constante vai ficar no requestHandler mesmo

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {
		

		
		
		Request currentRequest = Request.getCurrentRequest();
		currentRequest.addFilter(EqualFilter.class, "teste_string");
		//currentRequest.addFilter(LikeFilter.class, "teste_string");

		return service.licitacoes();
	}
}