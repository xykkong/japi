package br.gov.planejamento.api.commons.constants;


public abstract class LicitacaoConstants {

	public static final String LICITACOES_VERSION = "v1";
	
	public static final class Prefixes {
		public static final String LIST = "/"+LICITACOES_VERSION;
		public static final String DOCUMENT = CommonConstants.URIConstants.DOCUMENT;
		public static final String MIRROR = CommonConstants.URIConstants.MIRROR;
	}
	
	public static final class Requests {
		
		public static final class List {
			public static final String LICITACOES = Prefixes.LIST + "/licitacoes";
			public static final String ORGAOS = Prefixes.LIST + "/orgaos";
		}
		
		public static final class Document {
			public static final String LICITACAO = Prefixes.DOCUMENT + "/licitacao";
		}
		
		public static final class Mirror {
			public static final String LICITACAO = Prefixes.MIRROR + "/licitacao";
		}
	}
	
	public static final class URIConstants {
		
		public static final class List {
			public static final String LICITACOES = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.LIST + "/licitacoes";
			public static final String LICITACOESTESTE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.LIST + "/licitacoesteste";
			public static final String ORGAOS = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.LIST + "/orgaos";
		}
		
		public static final class Document {
			public static final String LICITACAO = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.DOCUMENT + "/licitacao/";
			public static final String LICITACAOTESTE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.DOCUMENT + "/licitacaoteste/";
		}
		
		public static final class Mirror {
			public static final String LICITACAO = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.MIRROR + "/licitacao/";
			public static final String LICITACAOTESTE = CommonConstants.URIConstants.BASE_URL + CommonConstants.Modules.LICITACOES + Prefixes.MIRROR + "/licitacaoteste/";
		}
	}
	
	public static final class Properties {
		public static final class Names {
			public static final String IDADE = "idade";
			public static final String  NOME = "nome";
			public static final String NASCIMENTO = "nascimento";
			public static final String  BOOLEAN = "boolean";
		}
		
		public static final class Description {
			public static final String IDADE = "Idade da Pessoa";
			public static final String  NOME = "Nome da Pessoa";
			public static final String NASCIMENTO = "Data de Nascimento";
			public static final String  BOOLEAN = "Se é uma pessoa legal";
		}
		
		public static final class Link {
			public static final String IDADE = URIConstants.List.LICITACOES+"?" + Properties.Names.IDADE + "=";
		}
	}
	
	public static String getURI(String mirror) {
		return CommonConstants.Modules.LICITACOES + mirror;
	}
	
}
