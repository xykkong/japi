package br.gov.planejamento.api.licitacoes.resource;

public class LicitacaoResource {
	public String nome;
	
	public String build() {
		return "<strong>Nome: </strong>" + nome;
	}
}
