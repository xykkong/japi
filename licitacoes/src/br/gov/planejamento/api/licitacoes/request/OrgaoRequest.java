package br.gov.planejamento.api.licitacoes.request;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.common.constants.LicitacaoConstants;
import br.gov.planejamento.api.core.annotations.DocParameterField;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterJapiException;
import br.gov.planejamento.api.core.filters.EqualFilter;
import br.gov.planejamento.api.licitacoes.service.OrgaoService;


@Path("/")
public class OrgaoRequest {
		
	private OrgaoService oService = new OrgaoService();
	
	@GET
	@Path(LicitacaoConstants.Requests.List.ORGAOS)
	public Response orgaos(
				@DocParameterField(name = "tipo_adm", required = false, description ="Código do tipo da administração do órgão") String tipo_adm,
				@DocParameterField(name = "ativo", required = false, description ="Se o órgão está ativo.") String ativo
			) throws InvalidFilterValueTypeJapiException,
			InvalidOrderSQLParameterJapiException, InvalidOrderByValueJapiException,
			InvalidOffsetValueJapiException, SQLException, ParserConfigurationException,
			SAXException, IOException{
		Session currentSession = Session.getCurrentSession();
		
		currentSession.addFilter(
				new EqualFilter(Integer.class, new DatabaseAlias("tipo_adm"))
				// new BooleanFilter()
				);

		Response response = null;
		response = oService.orgaos();
		return response;
	}
		
	

}
