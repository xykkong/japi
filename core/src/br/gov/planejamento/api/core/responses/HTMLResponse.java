package br.gov.planejamento.api.core.responses;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Response;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;

public class HTMLResponse extends Response {
	
	private String htmlCode;
	
	//TODO: No futuro, implementar para parsear um arquivo HTML
	//private String htmlUrl;

	public HTMLResponse(String htmlCode) {
		super();
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
		throw new CoreException("Não é possível converter para XML. Uma response do tipo SwaggerResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toCSV() throws ApiException {
		throw new CoreException("Não é possível converter para CSV. Uma response do tipo SwaggerResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toJSON() throws ApiException {
		throw new CoreException("Não é possível converter para JSON. Uma response do tipo SwaggerResponse só pode ser serializada no formato HTML.");
	}

	@Override
	public String toHTML() throws ApiException {
		return this.htmlCode;
	}

	@Override
	public int getHttpStatusCode() {
		return 200;
	}

}
