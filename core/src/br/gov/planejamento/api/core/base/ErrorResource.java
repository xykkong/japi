package br.gov.planejamento.api.core.base;

import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.exceptions.ApiException;


public class ErrorResource extends Resource {

	public ErrorResource(DataRow dRow) {
		super(dRow);
		// TODO Auto-generated constructor stub
	}

	private String mensagem;
	private String codigo;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Property<String> getMensagem() {
		return new Property<String>("Mensagem", mensagem);
	}
	
	@Override
	public SelfLink getSelfLink() throws ApiException {
		return new SelfLink(RequestContext.getContext().getRootURL(), "Erro " + codigo);
	}

}
