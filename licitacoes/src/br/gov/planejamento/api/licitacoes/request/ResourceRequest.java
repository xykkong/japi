package br.gov.planejamento.api.licitacoes.request;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

public class ResourceRequest {
	@GET
	@Path("whatever_path_might_be")
	public String root(@Context ServletContext ctx) {
	    String ctxPath = ctx.getContextPath();
	    String stylesFolder = ctxPath + "/Styles";
	    String imagesFolder = ctxPath + "/Images";

	    String head = "<head><link href=\"" + stylesFolder + "/style.css\" rel=\"stylesheet\"></head>";
	    String body = "<body><img src=\"" + imagesFolder + "/image.jpg\"/></body>";
	    return "<html>"+ head + body + "<html>";
	}
}
