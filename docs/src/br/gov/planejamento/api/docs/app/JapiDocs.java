package br.gov.planejamento.api.docs.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.gov.planejamento.api.commons.constants.CommonConstants;
import br.gov.planejamento.api.core.annotations.ResponseNotRequired;
import br.gov.planejamento.api.core.base.DocumentationObject;
import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.serializers.DocumentObjectSerializer;
import br.gov.planejamento.api.core.serializers.SwaggerParser;

@Path("/")
public class JapiDocs {
	private final String PATH_INNER_DOCS = "br/gov/planejamento/api/docs/templates/innerDocs.vm";
	
	@GET
	@Path("/")
	@ResponseNotRequired
	public String docsHome() throws ApiException{
			return DocumentObjectSerializer.render(null, null);
	}
	
	@GET
	@Path("/{modulo}")
	@ResponseNotRequired
	public String docs(@PathParam("modulo") String modulo) throws ApiException{
		
		modulo = RequestContext.getContext().getValue("modulo");
		DocumentationObject documentation = SwaggerParser.parse(docUrl(modulo));
		documentation.setModulo(modulo);
		
		return DocumentObjectSerializer.render(documentation, null);
	}
	
	@GET
	@ResponseNotRequired
	@Path("/doc/{modulo}/{consulta}")
	public String innerDocs(
		@PathParam("consulta") String method,
		@PathParam("modulo") String modulo) throws ApiException{
			modulo = RequestContext.getContext().getValue("modulo");
			method = RequestContext.getContext().getValue("consulta");
			if(modulo == null || method == null) throw new RequestException("A Url está incorreta. São esperados os parâmetros modulo e consulta.");
			DocumentationObject documentation = SwaggerParser.parse(docUrl(modulo));
			documentation.setModulo(modulo);
			for (DocumentationObject.Request request : documentation.getRequests()) {
				if(request.getMethod_name() != null && request.getMethod_name().equals(method))
					return DocumentObjectSerializer.render(request, PATH_INNER_DOCS);
			}
			throw new RequestException("Documentação inexistente. O módulo ou a consulta passados estão incorretos.");
		
	}
	
	private static String docUrl(String modulo){
		return  CommonConstants.URIConstants.BASE_URL + modulo + "/" + modulo + "docs";
	}

}
