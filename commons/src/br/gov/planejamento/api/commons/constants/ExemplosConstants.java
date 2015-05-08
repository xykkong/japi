package br.gov.planejamento.api.commons.constants;


public abstract class ExemplosConstants {

	/* Property é um tipo primitivo da Framework que representa um campo que deve vir a ser exibido para o usuário.
    *
    * Uma Property possui dois argumentos constantes: Uma String que representa o nome da Property (que será visto pelo
    * usuário) e o valor da property, que nada mais é do que a variável.
    *
    * Além disso, a Property pode conter um link para alguma página externa, e para isso é usado o tipo LinkProperty
    *
    *A utilização de LinkProperties é fundamental para relacionar os módulos entre si e integrar a API.
    */
	
	public static final class Properties {
		public static final class Names {
			public static final String IDADE = "idade";
			public static final String NOME = "nome";
			public static final String NASCIMENTO = "nascimento";
			public static final String BOOLEAN = "boolean";
		}
		
		public static final class Description {
			public static final String IDADE = "Idade da Pessoa";
			public static final String NOME = "Nome da Pessoa";
			public static final String NASCIMENTO = "Data de Nascimento";
			public static final String BOOLEAN = "Se é uma pessoa legal";
		}
	}
	
}
