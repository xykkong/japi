package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;

public abstract class HTMLSerializer {

	public static String fromResponse(Response response)
			throws ResourceNotFoundException, ParseErrorException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, MethodInvocationException, IOException,
			Exception {

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
					.getCurrentSession().getHTMLTemplate(), "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("session", Session.getCurrentSession());
			context.put("response", response);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "dey ruim";
	}
}
