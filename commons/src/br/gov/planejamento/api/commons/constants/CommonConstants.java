package br.gov.planejamento.api.commons.constants;

import br.gov.planejamento.api.core.base.RequestContext;

public class CommonConstants {
	/**
	 * Primeira parte da URL de cada módulo
	 */
	public static final class Modules {
		public static final String LICITACOES = "licitacoes";
	}
	
	public static final class URIConstants{
		
		public static final String BASE_URL = RequestContext.getContext().getRootURL();
		public static final String VERSION = "/v1";
		
		public static final class Licitacoes{
			
			public static final String LICITACOES = URIConstants.BASE_URL + Modules.LICITACOES + URIConstants.VERSION + "/licitacoes";
			public static final String LICITACOESTESTE = URIConstants.BASE_URL + Modules.LICITACOES + URIConstants.VERSION + "/licitacoesteste";
			
			public static final class Doc{
				public static final String LICITACAO = URIConstants.BASE_URL+ Modules.LICITACOES + "/doc/licitacao/";
				public static final String LICITACAOTESTE = URIConstants.BASE_URL+ Modules.LICITACOES + "/doc/licitacaoteste/";
			}
		}
	}
}
