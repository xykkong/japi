package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.Link;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class LicitacaoResource extends Resource {

	private String uasg;
	private String nomeUasg;
	private String modalidade;
	private String nomeModalidade;
	private String numeroAviso;
	private String dataAberturaProposta;
	
	public LicitacaoResource() {
		super();
	}
	
	
	/*
	 * Construtor utilizado para gerar um objeto do Resource a partir de uma linha do banco de dados. Automatiza o mapeamento
	 * dos par�metros e facilita a adapta��o dos campos pelo desenvolvedor, uma vez que ele n�o precisa modificar o service
	 * sempre que houver uma mudan�a no resource, precisando apenas modificar esse construtor.
	 */
	public LicitacaoResource(DataRow licitacao) {
		super();
		this.modalidade = licitacao.get("modalidade");
		this.nomeModalidade = licitacao.get("nome_modalildade");
		this.nomeUasg = licitacao.get("nome_uasg");
		this.numeroAviso = licitacao.get("numero_aviso");
		this.uasg = licitacao.get("uasg");
		this.dataAberturaProposta = licitacao.get("data_abertura_proposta");
	}
	
	public Property getDataAberturaProposta(){
		return new Property("Data de Abertura da Proposta", dataAberturaProposta);
	}
	
	public void setDataAberturaProposta(String dtAbertura){
		this.dataAberturaProposta = dtAbertura;
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