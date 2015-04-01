package br.gov.planejamento.api.commons.constants;

import br.gov.planejamento.api.core.base.RequestContext;

public class CommonConstants {
	/**
	 * Primeira parte da URL de cada módulo
	 */
	public static final class Modules {
		public static final String LICITACOES = "licitacoes";
	}
	
	public static final class Docs {
		public static final String LICITACOES = "licitacoesdocs";
	}
	
	public static final class URIConstants{
		
		public static final String BASE_URL = RequestContext.getContext().getRootURL();
		public static final String DOCUMENT = "/doc";
		public static final String MIRROR = "/id";
	}
}
