package br.gov.planejamento.api.licitacoes.resource;

import br.gov.planejamento.api.core.base.LinkProperty;
import br.gov.planejamento.api.core.base.Property;
import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;
import br.gov.planejamento.api.core.parameters.DateParam;

public class LicitacaoResource extends Resource {

	private String uasg;
	private String nomeUasg;
	private String modalidade;
	private String nomeModalidade;
	private String numeroAviso;
	private String dataAberturaProposta;
	
	
	/*
	 * Construtor utilizado para gerar um objeto do Resource a partir de uma linha do banco de dados. Automatiza o mapeamento
	 * dos parâmetros e facilita a adaptaçao dos campos pelo desenvolvedor, uma vez que ele não precisa modificar o service
	 * sempre que houver uma mudança no resource, precisando apenas modificar esse construtor.
	 */
	public LicitacaoResource(DataRow licitacao) {
		super(licitacao);
		this.modalidade = licitacao.get("modalidade");
		this.nomeModalidade = licitacao.get("nome_modalildade");
		this.nomeUasg = licitacao.get("nome_uasg");
		this.numeroAviso = licitacao.get("numero_aviso");
		this.uasg = licitacao.get("uasg");
		this.dataAberturaProposta = licitacao.get("data_abertura_proposta");
	}
	
	public Property<DateParam> getDataAberturaProposta(){
		return new Property<DateParam>("Data de Abertura da Proposta", dataAberturaProposta);
	}

	public Property<String> getUasg() {
		return new LinkProperty<String>("UASG","http://google.com",uasg, "uasg");
	}

	public Property<String> getNomeUasg() {
		return new Property<String>("Nome UASG", nomeUasg);
	}

	public void setNomeUasg(String nomeUasg) {
		this.nomeUasg = nomeUasg;
	}

	public Property<String> getModalidade() {
		StringBuilder valueBuilder = new StringBuilder(this.modalidade);
		valueBuilder.append(" - ");
		valueBuilder.append(this.nomeModalidade);
		return new Property<String>("Modalidade", valueBuilder.toString());
	}

	public Property<Integer> getNumeroAviso() {
		return new Property<Integer>("Número Aviso", numeroAviso);
	}

	
	@Override
	public SelfLink getSelfLink() {
		//return new SelfLink(LicitacaoConstants.URIConstants.Mirror.LICITACAO+this.uasg+this.modalidade+this.numeroAviso ,"Licitação de numero "+this.uasg+this.modalidade+this.numeroAviso);
		//Foi comentado pois, como não há nenhum método que use esse Resource, não há Path definido ainda.
		return null;
	}
	

}