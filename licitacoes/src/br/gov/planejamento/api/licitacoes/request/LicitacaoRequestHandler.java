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
	
	private LicitacaoService service =  new LicitacaoService();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {
		Request current = Request.getCurrentRequest();
		current.addFilter(new EqualFilter("teste_int", "34", EqualFilter.TYPE_FILTER_INTEGER));
		//TODO pegar o parametro de uma constante e o valor do GET
		
		return service.licitacoes();
	}	
}