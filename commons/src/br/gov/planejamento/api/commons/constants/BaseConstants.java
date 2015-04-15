package br.gov.planejamento.api.commons.constants;


public abstract class BaseConstants {

	public static final String BASE_VERSION = "v1";
	
	public static final class Prefixes {
		public static final String LIST = "/"+BASE_VERSION;
		public static final String DOCUMENT = CommonConstants.URIConstants.DOCUMENT;
		public static final String MIRROR = CommonConstants.URIConstants.MIRROR;
	}
	
	public static final class Requests {
		
		public static final class List {
			public static final String BASE = Prefixes.LIST + "/exemplos/base";			
		}
		
		public static final class Document {
			public static final String BASE = Prefixes.DOCUMENT + "/exemplos/base";
		}
		
		public static final class Mirror {
			public static final String BASE = Prefixes.MIRROR + "/exemplos/base";
		}
	}
	
	public static final class URIConstants {
		
		/*É necessário criar um link para cada módulo dentro do seu projeto */
		
		public static final class List {
			public static final String BASE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.EXEMPLOS+ Prefixes.LIST + "/exemplos/base";
		}
		
		public static final class Document {
			public static final String BASE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.EXEMPLOS+ Prefixes.LIST + "/exemplos/base";			
		}
		
		public static final class Mirror {
			public static final String BASE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.EXEMPLOS+ Prefixes.LIST + "/exemplos/base";			
		}
	}
	
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
		
		public static final class Link {
			public static final String IDADE = URIConstants.List.BASE+"?" + Properties.Names.IDADE + "=";
		}
	}
	
}
