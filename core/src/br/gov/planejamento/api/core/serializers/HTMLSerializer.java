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
import br.gov.planejamento.api.core.responses.DocumentationResponse;
import br.gov.planejamento.api.core.responses.ErrorResponse;
import br.gov.planejamento.api.core.responses.ResourceListResponse;
import br.gov.planejamento.api.core.responses.ResourceResponse;

public abstract class HTMLSerializer {

	public static String fromResourceListResponse(ResourceListResponse<?> response)
			throws ApiException {

		String template = RequestContext.getContext().getHTMLTemplate();
		
		return render(response, template);
		
	}
	
	public static String fromResourceResponse(ResourceResponse<?> response) throws ApiException {
			
		String template = RequestContext.getContext().getHTMLTemplate();
		
		return render(response, template);
		
	}
	
	public static String fromErrorResponse(ErrorResponse response) throws ApiException {
		String template = RequestContext.getContext().getErrorTemplate();
		return render(response, template);
	}
	
	public static String fromDocumentationResponse(DocumentationResponse docResponse) throws ApiException {
		return render(docResponse, docResponse.getTemplate());
	}
	
	public static String render(Response response, String templateString) throws ApiException{
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
		
		VelocityContext context = new VelocityContext();
		context.put("session", RequestContext.getContext());
		context.put("response", response);
		StringWriter writer = new StringWriter();
		
		Template template;
		try {
			template = Velocity.getTemplate(templateString, "UTF-8");	
		} catch (Exception e) {
			throw new CoreException("Houve um erro ao definir o template do Velocity.", e);
		}
		try {
			template.merge(context, writer);
		} catch (ResourceNotFoundException | ParseErrorException
				| MethodInvocationException | IOException e) {

			e.printStackTrace();
			throw new CoreException("Houve um erro ao processar o template do Velocity.", e);
		}
		return writer.toString();

	}
}
