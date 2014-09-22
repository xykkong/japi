package br.gov.planejamento.api.licitacoes.controller;

import java.sql.SQLException;

import br.gov.planejamento.api.core.base.Controller;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

public class LicitacaoController extends Controller {
	
	public Response licitacoes() throws SQLException {
		LicitacaoService service = new LicitacaoService();
		return new Response(service.licitacoes());
	}
}
