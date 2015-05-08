package br.gov.planejamento.api.commons.routers;

import br.gov.planejamento.api.core.base.Router;

public class LicitacoesRouter extends Router {

	public static final class Prefixes {
		public static final String LIST = "v1/";
		public static final String RESOURCE = "doc/";
	}
	
	@Override
	public String getModulePath() {
		return "licitacoes";
	}		
	
	public static final String LICITACOES = Prefixes.LIST + "licitacoes";
	public static final String TESTE = Prefixes.LIST + "licitacoesteste";
	public static final String ORGAOS = Prefixes.LIST + "orgaos";
	
	public static final String TESTE_UNICO = Prefixes.RESOURCE + "licitacaoteste/{idade}";
	
}
