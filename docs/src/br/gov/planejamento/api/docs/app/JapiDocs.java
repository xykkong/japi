package br.gov.planejamento.api.docs.app;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import br.gov.planejamento.api.core.annotations.Parameter;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.docs.utils.DocumentationObject;
import br.gov.planejamento.api.docs.utils.SwaggerParser;

@Path("/")
public class JapiDocs {
	@GET
	@Path("/")
	public String docs(@Parameter(name = "modulo") String modulo){
		modulo = Session.getCurrentSession().getValue("modulo");
		DocumentationObject documentation = SwaggerParser.parse(modulo);
		documentation.setModulo(modulo);
		
		return render(documentation, null);
		
	}
	
	@GET
	@Path("/doc")
	public String innerDocs(
		@Parameter(name = "method") String method,
		@Parameter(name = "modulo") String modulo){
			modulo = Session.getCurrentSession().getValue("modulo");
			method = Session.getCurrentSession().getValue("method");
			DocumentationObject documentation = SwaggerParser.parse(modulo);
			documentation.setModulo(modulo);
			System.out.println("oioioi" +documentation.getRequests().get(0).getDescription());
			for (DocumentationObject.Request request : documentation.getRequests()) {
				if(request.getMethod_name() != null && request.getMethod_name().equals(method))
					return render(request, "br/gov/planejamento/api/docs/templates/templateMethod.vm");
				else System.out.println("Comparando "+request.getMethod_name()+" com "+ method);
			}
			return "Documentação não existe";
		
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
				template = Velocity.getTemplate(Session
						.getCurrentSession().getDocsTemplate(), "UTF-8");
			}
			else{
				template = Velocity.getTemplate(templateName, "UTF-8");
			}
			VelocityContext context = new VelocityContext();
			context.put("session", Session.getCurrentSession());
			context.put("documentation", documentation);
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
