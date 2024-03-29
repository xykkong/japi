package br.gov.planejamento.api.core.responses;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import br.gov.planejamento.api.core.base.HtmlResourceLoader;
import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.serializers.HTMLSerializer;

public class HTMLResponse extends Response {
	
	private String htmlCode;

	public HTMLResponse(String htmlCode) {
		super();
		this.htmlCode = htmlCode;
	}
	
	public HTMLResponse(HtmlResourceLoader resourceLoarder) throws CoreException{
		try {
			String fileName = resourceLoarder.getPath();
			
			InputStream is = HTMLResponse.class.getResourceAsStream(fileName);
			
			StringWriter writer = new StringWriter();
			
			IOUtils.copy(is, writer, "UTF-8");

			this.htmlCode = writer.toString();
		} catch (Exception e) {
			throw new CoreException(Errors.HTML_RESPONSE_FALHA_CARREGAMENTO_ARQUIVO, "Não foi possível carregar as informações do arquivo "+resourceLoarder.getPath(), 404, e);
		}
	}
	
	
	public String getHtmlCode() {
		return htmlCode;
	}


	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}


	@Override
	public SelfLink getSelfLink() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Link> getLinks() throws ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXML() throws ApiException {
		throw new CoreException(Errors.HTML_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para XML. Uma response do tipo HTMLResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toCSV() throws ApiException {
		throw new CoreException(Errors.HTML_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para CSV. Uma response do tipo HTMLResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toJSON() throws ApiException {
		throw new CoreException(Errors.HTML_RESPONSE_SERIALIZER_INVALIDO, "Não é possível converter para JSON. Uma response do tipo HTMLResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toHTML() throws ApiException {
		return HTMLSerializer.fromHtmlResponse(this);
	}

	@Override
	public int getHttpStatusCode() {
		return 200;
	}

}
