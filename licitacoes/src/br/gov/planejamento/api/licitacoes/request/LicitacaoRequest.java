package br.gov.planejamento.api.licitacoes.request;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.core.filters.LikeFilter;
import br.gov.planejamento.api.licitacoes.service.LicitacaoService;

@Path("/")
public class LicitacaoRequest {

	private LicitacaoService service = new LicitacaoService();
	//TODO ver se esta constante vai ficar no requestHandler mesmo

	@GET
	@Path(LicitacaoConstants.Requests.List.LICITACOES)
	public String licitacoes() throws SQLException, InvalidFilterValueTypeException {	
		
		Session currentSession = Session.getCurrentSession();
		
		currentSession.addFilter(EqualFilter.class, Integer.class, "uasg", "modalidade");
		currentSession.addFilter(LikeFilter.class, "nome_uasg");

		return service.licitacoes();
	}
}
