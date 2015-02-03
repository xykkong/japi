package br.gov.planejamento.api.core.serializers;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.Session;

public abstract class HTMLSerializer {

	public static String fromResponse(Response response)
			throws ResourceNotFoundException, ParseErrorException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, MethodInvocationException, IOException,
			Exception {
		VelocityEngine ve = new VelocityEngine();
		try {
			ve.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Template template = null;
		template = ve
				.getTemplate(Session.getCurrentSession().getHTMLTemplate());
		VelocityContext context = new VelocityContext();
		ArrayList<Object> licitacaoList = new ArrayList<Object>();
		for (Resource resource : response) {
			
			for (Property p : resource.getProperties()) {
				p.getValue();
			}
			// writer.writeNext(linha);
		}
		context.put("licitacaoList", response);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}
}
