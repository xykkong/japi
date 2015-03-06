package br.gov.planejamento.api.core.base;

import br.gov.planejamento.api.core.base.JapiConfigLoader.JapiConfig;

public class ErrorResource extends Resource {

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
	
	public Property getMensagem() {
		return new Property("Mensagem", mensagem);
	}
	
	@Override
	public SelfLink getSelfLink() {
		return new SelfLink(RequestContext.getContext().getRootURL(), "Erro " + codigo);
	}

}
