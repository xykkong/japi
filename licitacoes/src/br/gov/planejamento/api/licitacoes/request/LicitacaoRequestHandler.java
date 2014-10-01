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
	public final String URI_INT = "int";
	public final String DB_INT = "teste_int";

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {
		Request currentRequest = Request.getCurrentRequest();
		if(currentRequest.hasParameter(URI_INT)){
			currentRequest.addFilter(new EqualFilter(DB_INT, 
					currentRequest.getParameter(URI_INT), 
					EqualFilter.TYPE_FILTER_INTEGER));			
		}

		return service.licitacoes();
	}
}