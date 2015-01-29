package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;

public class LicitacaoResource extends Resource {

	private String uasg;
	private String nomeUasg;
	private String modalidade;
	private String nomeModalidade;
	private String numeroAviso;

	public Property getUasg() {
		return new Property("UASG", uasg);
	}

	public void setUasg(String uasg) {
		this.uasg = uasg;
	}

	public Property getNomeUasg() {
		return new Property("Nome UASG", nomeUasg);
	}

	public void setNomeUasg(String nomeUasg) {
		this.nomeUasg = nomeUasg;
	}

	public Property getModalidade() {
		StringBuilder valueBuilder = new StringBuilder(this.modalidade);
		valueBuilder.append(" - ");
		valueBuilder.append(this.nomeModalidade);
		return new Property("Modalidade", valueBuilder.toString());
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public void setNomeModalidade(String nomeModalidade) {
		this.nomeModalidade = nomeModalidade;
	}

	public Property getNumeroAviso() {
		return new Property("N�mero Aviso", numeroAviso);
	}

	public void setNumeroAviso(String numeroAviso) {
		this.numeroAviso = numeroAviso;
	}

	@Override
	public Link getSelfLink() {
		return new SelfLink();
	}

}