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
import br.gov.planejamento.api.docs.utils.SwaggerParser;

@Path("/")
public class JapiDocs {
	@GET
	@Path("/")
	public String docs(@Parameter(name = "modulo") String modulo){
		modulo = Session.getCurrentSession().getValue("modulo");
		System.out.println("Documentação do Modulo "+ modulo);
		SwaggerParser.parse(modulo);
		
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

			Template template = Velocity.getTemplate(Session
					.getCurrentSession().getDocsTemplate(), "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("session", Session.getCurrentSession());
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Problema na Renderização";
	}
	
	public class HTMLDocs {

	}

}
