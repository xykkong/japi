package br.gov.planejamento.api.licitacoes.request;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Request;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

@Path("/")
public class LicitacaoRequest extends Request {
	
	private LicitacaoService service =  new LicitacaoService();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {
		return service.getResponse().toString();
	}
}
