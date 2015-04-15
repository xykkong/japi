package br.gov.planejamento.api.exemplos.resource;

import br.gov.planejamento.api.core.base.Resource;
import br.gov.planejamento.api.core.base.SelfLink;
import br.gov.planejamento.api.core.database.DataRow;

public class BaseResource extends Resource {
	/*
	 * Essa classe contem métodos que são utilizados para exibir as informações do Resource para o usuário.
	 */
	
	private String retorno_do_banco_que_sera_passado_ao_usuário_1;
	private String retorno_do_banco_que_sera_passado_ao_usuário_2;
	private String retorno_do_banco_que_sera_passado_ao_usuário_x;

	
	
	/*
	 * Construtor utilizado para gerar um objeto do Resource a partir de uma linha do banco de dados. Automatiza o mapeamento
	 * dos parâmetros e facilita a adaptaçao dos campos pelo desenvolvedor, uma vez que ele não precisa modificar o service
	 * sempre que houver uma mudança no resource, precisando apenas modificar esse construtor.
	 */
	public BaseResource(DataRow exemplo) {
		super(exemplo);
		this.retorno_do_banco_que_sera_passado_ao_usuário_1 = exemplo.get("nome_do_campo_no_banco_1");
		this.retorno_do_banco_que_sera_passado_ao_usuário_2 = exemplo.get("nome_do_campo_no_banco_2");
		this.retorno_do_banco_que_sera_passado_ao_usuário_x = exemplo.get("nome_do_campo_no_banco_x");		
	}
	


	
	public String getRetorno_do_banco_que_sera_passado_ao_usuário_1() {
		return retorno_do_banco_que_sera_passado_ao_usuário_1;
	}




	public void setRetorno_do_banco_que_sera_passado_ao_usuário_1(
			String retorno_do_banco_que_sera_passado_ao_usuário_1) {
		this.retorno_do_banco_que_sera_passado_ao_usuário_1 = retorno_do_banco_que_sera_passado_ao_usuário_1;
	}




	public String getRetorno_do_banco_que_sera_passado_ao_usuário_2() {
		return retorno_do_banco_que_sera_passado_ao_usuário_2;
	}




	public void setRetorno_do_banco_que_sera_passado_ao_usuário_2(
			String retorno_do_banco_que_sera_passado_ao_usuário_2) {
		this.retorno_do_banco_que_sera_passado_ao_usuário_2 = retorno_do_banco_que_sera_passado_ao_usuário_2;
	}




	public String getRetorno_do_banco_que_sera_passado_ao_usuário_x() {
		return retorno_do_banco_que_sera_passado_ao_usuário_x;
	}




	public void setRetorno_do_banco_que_sera_passado_ao_usuário_x(
			String retorno_do_banco_que_sera_passado_ao_usuário_x) {
		this.retorno_do_banco_que_sera_passado_ao_usuário_x = retorno_do_banco_que_sera_passado_ao_usuário_x;
	}



	/*
     * SelfLink é o link que aponta para o seu Resource. 
     *
     * Ao realizar uma consulta, a resposta virá em formato de Response, que é uma lista de Resources.
     * 
     * Nessa lista é possível acessar a página exclusiva de um desses Resources clicando sobre o SelfLink do mesmo.
     *
     * O contrutor do SelfLink recebe dois parâmetros: Uma string "href", que representa a url para o mesmo e uma string 
     * "title" que define o nome de exibição do link.
     */
	@Override
	public SelfLink getSelfLink() {
		 return new SelfLink("url para o meu resource", "nome do meu resource");
	}
	

}