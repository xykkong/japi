package br.gov.planejamento.api.commons.constants;

import br.gov.planejamento.api.core.constants.Constants;

public abstract class LicitacaoConstants {

	public static final String LICITACOES_VERSION = Constants.ApiVersions.V1;
	
	public static final class Prefixes {
		public static final String LIST = LICITACOES_VERSION;
		public static final String DOCUMENT = Constants.RequestTypes.DOCUMENT;
		public static final String MIRROR = Constants.RequestTypes.MIRROR;
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
	
	public static String getURI(String mirror) {
		return CommonConstants.Modules.LICITACOES + mirror;
	}
	
}
