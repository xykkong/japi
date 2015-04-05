package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public abstract class HTMLSerializer {

	public static String fromResponse(Response response)
			throws ApiException {

		Velocity.setProperty("resource.loader", "classpath");
		Velocity.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		Velocity.setProperty("runtime.log.logsystem.class",
				"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		Velocity.setProperty("runtime.log.logsystem.log4j.category",
				"velocity");
		Velocity.setProperty("runtime.log.logsystem.log4j.logger",
				"velocity");

		try {
			Velocity.init();
		} catch (Exception e) {
			throw new CoreException("Houve um erro ao inicializar o Velocity.", e);
		}

		Template template;
		try {
			template = Velocity.getTemplate(RequestContext
					.getContext().getHTMLTemplate(), "UTF-8");
		} catch (Exception e) {
			throw new CoreException("Houve um erro ao definir o template do Velocity.", e);
		}
		VelocityContext context = new VelocityContext();
		context.put("session", RequestContext.getContext());
		context.put("response", response);
		StringWriter writer = new StringWriter();
		try {
			template.merge(context, writer);
		} catch (ResourceNotFoundException | ParseErrorException
				| MethodInvocationException | IOException e) {
			throw new CoreException("Houve um erro ao processar o template do Velocity.", e);
		}
		return writer.toString();
	}
}
