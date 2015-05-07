package br.gov.planejamento.api.docs.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.responses.DocumentationResponse;
import br.gov.planejamento.api.core.responses.HTMLResponse;
import br.gov.planejamento.api.core.serializers.SwaggerParser;

@ApplicationPath("/")
@Path("/")
public class Docs extends Application {
	
	@GET
	@Path("/")
	public HTMLResponse docsHome() throws ApiException{	
		HTMLResponse home = new HTMLResponse("<h2 id='titulo' class='titulo-pagina'>Documentação</h2><hr class='barra' style='margin: 0px;'/><p class='met_descricao'>Seja bem vindo ao módulo de Documentação</p>");
		
		return home;
	}
	
	@GET
	@Path("/{modulo}")
	public DocumentationResponse docsModulo(@PathParam("modulo") String modulo) throws ApiException{
		modulo = RequestContext.getContext().getValue("modulo");
		DocumentationResponse documentation = SwaggerParser.parse(docUrl(modulo));
		documentation.setModulo(modulo);
		documentation.setTemplate(RequestContext.getContext().getDocsModuloTemplate());
		return documentation;
		
	}
	
	@GET
	@Path("/doc/{modulo}/{consulta}")
	public DocumentationResponse docsMetodo(
		@PathParam("consulta") String method,
		@PathParam("modulo") String modulo) throws ApiException{
			modulo = RequestContext.getContext().getValue("modulo");
			method = RequestContext.getContext().getValue("consulta");
			if(modulo == null || method == null) throw new RequestException("A Url está incorreta. São esperados os parâmetros modulo e consulta.");
			DocumentationResponse documentation = SwaggerParser.parse(docUrl(modulo));
			documentation.setModulo(modulo);
			for (DocumentationResponse.Request request : documentation.getRequests()) {
				if(request.getMethod_name() != null && request.getMethod_name().equals(method)){
					documentation.setTemplate(RequestContext.getContext().getDocsMetodoTemplate());
					return documentation;
				}
			}
			throw new RequestException("Documentação inexistente. O módulo ou a consulta passados estão incorretos.");
		
	}
	
	private static String docUrl(String modulo){
		return  CommonConstants.URIConstants.BASE_URL + modulo + "/" + CommonConstants.DOCS + ".json";
	}
}	
