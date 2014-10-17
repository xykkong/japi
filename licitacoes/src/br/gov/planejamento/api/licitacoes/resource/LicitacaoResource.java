package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.Resource;

public class LicitacaoResource extends Resource {

	private String uasg;
	private String nomeUasg;
	private String modalidade;
	private String nomeModalidade;
	private String numeroAviso;

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append("Uasg: " + getUasg() + "<br>");
		sb.append("Nome Uasg: " + getNomeUasg() + "<br>");
		sb.append("Modalidade: " + getModalidade() + "<br>");
		sb.append("Nome Modalidade: " + getNomeModalidade() + "<br>");
		sb.append("Numero Aviso: " + getNumeroAviso() + "<br>");
		return sb.toString();
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
