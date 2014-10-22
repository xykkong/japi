package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.annotations.JSONProperties;
import br.gov.planejamento.api.core.annotations.Properties;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;

@Properties({"uasg", "nomeUasg", "modalidade", "nomeModalidade", "numeroAviso"})
@JSONProperties({"uasg","nomeUasg"})
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
		sb.append("Numero Aviso: " + getNumeroAviso() + "<br>");
		return sb.toString();
	}

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
		return new Property("Modalidade", this.modalidade+" : "+this.nomeModalidade);
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public void setNomeModalidade(String nomeModalidade) {
		this.nomeModalidade = nomeModalidade;
	}

	public Property getNumeroAviso() {
		return new Property("Número Aviso", numeroAviso);
	}

	public void setNumeroAviso(String numeroAviso) {
		this.numeroAviso = numeroAviso;
	}

}
