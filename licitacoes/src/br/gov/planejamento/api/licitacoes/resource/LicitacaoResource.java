package br.gov.planejamento.api.licitacoes.resource;

public class LicitacaoResource {

	private String uasg;
	private String nomeUasg;
	private String modalidade;
	private String nomeModalidade;
	private String numeroAviso;

	public String build() {
		return "<strong>Uasg: </strong>" + this.getUasg();
	}

	public String getUasg() {
		return uasg;
	}

	public void setUasg(String uasg) {
		this.uasg = uasg;
	}

	public String getNomeUasg() {
		return nomeUasg;
	}

	public void setNomeUasg(String nomeUasg) {
		this.nomeUasg = nomeUasg;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getNomeModalidade() {
		return nomeModalidade;
	}

	public void setNomeModalidade(String nomeModalidade) {
		this.nomeModalidade = nomeModalidade;
	}

	public String getNumeroAviso() {
		return numeroAviso;
	}

	public void setNumeroAviso(String numeroAviso) {
		this.numeroAviso = numeroAviso;
	}

}
