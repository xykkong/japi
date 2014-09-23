package br.gov.planejamento.api.licitacoes.controller;

import java.sql.SQLException;

import br.gov.planejamento.api.core.base.Controller;
import br.gov.planejamento.api.core.base.Request;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

public class LicitacaoController extends Controller {
	
	public Response licitacoes(Request request) throws SQLException {
		LicitacaoService service = new LicitacaoService();
		return service.getResponse(request);
	}
}