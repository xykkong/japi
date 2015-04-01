package br.gov.planejamento.api.docs.app;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.docs.utils.DocumentationObject;
import br.gov.planejamento.api.docs.utils.SwaggerParser;

@Path("/")
public class JapiDocs {
	
	public static class DocsConfig {
		private static String[] modulos = {"licitacoes"};
	}
	
	@GET
	@Path("/")
	public String docsHome(){
		
			return render(null, null);
	}
	
	@GET
	@Path("/{modulo}")
	public String docs(@PathParam("modulo") String modulo){
		
		modulo = RequestContext.getContext().getValue("modulo");
		DocumentationObject documentation = SwaggerParser.parse(modulo);
		documentation.setModulo(modulo);
		
		return render(documentation, null);
	}
	
	@GET
	@Path("/doc/{modulo}/{consulta}")
	public String innerDocs(
		@PathParam("consulta") String method,
		@PathParam("modulo") String modulo) throws ApiException{
			modulo = RequestContext.getContext().getValue("modulo");
			method = RequestContext.getContext().getValue("consulta");
			if(modulo == null || method == null) throw new RequestException("A Url está incorreta. São esperados os parâmetros modulo e consulta.");
			DocumentationObject documentation = SwaggerParser.parse(modulo);
			documentation.setModulo(modulo);
			for (DocumentationObject.Request request : documentation.getRequests()) {
				if(request.getMethod_name() != null && request.getMethod_name().equals(method))
					return render(request, "br/gov/planejamento/api/docs/templates/innerDocs.vm");
			}
			throw new RequestException("Documentação inexistente. O módulo ou a consulta passados estão incorretos.");
		
	}

	public String render(Object documentation, String templateName){
		try {
			Velocity.setProperty("resource.loader", "classpath");
			Velocity.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());
			Velocity.setProperty("runtime.log.logsystem.class",
					"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
			Velocity.setProperty("runtime.log.logsystem.log4j.category",
					"velocity");
			Velocity.setProperty("runtime.log.logsystem.log4j.logger",
					"velocity");

			Velocity.init();
			
			Template template = new Template();

			if(templateName == null){
				template = Velocity.getTemplate(RequestContext
						.getContext().getDocsTemplate(), "UTF-8");
			}
			else{
				template = Velocity.getTemplate(templateName, "UTF-8");
			}
			VelocityContext context = new VelocityContext();
			context.put("session", RequestContext.getContext());
			if(documentation!=null){
				context.put("documentation", documentation);
			}
			context.put("modulos", DocsConfig.modulos);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Falha na Renderização do Template";
		}
	}

}
