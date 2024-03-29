package br.gov.planejamento.api.docs.app;

import java.util.ArrayList;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.base.HtmlResourceLoader;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.responses.DocumentationResponse;
import br.gov.planejamento.api.core.responses.DocumentationResponse.Request;
import br.gov.planejamento.api.core.responses.HTMLResponse;
import br.gov.planejamento.api.core.serializers.SwaggerParser;

@ApplicationPath("/")
@Path("/")
public class Docs extends Application {
	
	@GET
	@Path("/")
	public HTMLResponse docsHome() throws ApiException{	
		//HTMLResponse home = new HTMLResponse("");
		HTMLResponse home = new HTMLResponse(new HtmlResourceLoader("docs-home.html"));
		
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
	@Path("/{modulo}/{consulta}")
	public DocumentationResponse docsMetodo(
		@PathParam("consulta") String method,
		@PathParam("modulo") String modulo) throws ApiException{
			modulo = RequestContext.getContext().getValue("modulo");
			method = RequestContext.getContext().getValue("consulta");
			if(modulo == null || method == null) throw new RequestException(Errors.DOCS_URL_INCORRETA, "A Url está incorreta. São esperados os parâmetros modulo e consulta.");
			DocumentationResponse documentation = SwaggerParser.parse(docUrl(modulo));
			documentation.setModulo(modulo);
			for (DocumentationResponse.Request request : documentation.getRequests()) {
				if(request.getMethod_name() != null && request.getMethod_name().equals(method)){
					documentation.setTemplate(RequestContext.getContext().getDocsMetodoTemplate());
					ArrayList<Request> requests = new ArrayList<Request>();
					requests.add(request);
					documentation.setRequests(requests);
					return documentation;
				}
			}
			throw new RequestException(Errors.DOCS_DOCUMENTACAO_INEXISTENTE, "Documentação inexistente. O módulo ou a consulta passados estão incorretos.");
		
	}
	
	private static String docUrl(String modulo){
		return  RequestContext.getContext().getRootURL() + modulo + "/" + CommonConstants.Modules.DOCS + ".json";
	}
}	
