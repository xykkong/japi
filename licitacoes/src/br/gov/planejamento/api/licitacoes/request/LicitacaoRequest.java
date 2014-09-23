package br.gov.planejamento.api.licitacoes.request;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Request;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.licitacoes.controller.LicitacaoController;

@Path("/")
public class LicitacaoRequest extends Request {
	
	private LicitacaoController controller =  new LicitacaoController();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException {
		return controller.licitacoes(this).toString();
	}
}
