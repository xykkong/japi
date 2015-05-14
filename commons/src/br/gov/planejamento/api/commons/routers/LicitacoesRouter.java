package br.gov.planejamento.api.commons.routers;

import br.gov.planejamento.api.core.base.Router;

public class LicitacoesRouter extends Router {
	
	private static LicitacoesRouter instance = null;
	
	private LicitacoesRouter() {
		super("licitacoes");
	};
	
	public static LicitacoesRouter getRouter() {
		if(instance == null) {
			instance = new LicitacoesRouter();
		}
		
		return instance;
	}
	

	public static final class Prefixes {
		public static final String LIST = "v1/";
		public static final String RESOURCE = "doc/";
	}
	
	public static final String LICITACOES = Prefixes.LIST + "licitacoes";
	public static final String TESTE = Prefixes.LIST + "licitacoesteste";
	public static final String ORGAOS = Prefixes.LIST + "orgaos";
	
	public static final String TESTE_UNICO = Prefixes.RESOURCE + "licitacaoteste/{idade}";
	public static final String LICITACAO = Prefixes.RESOURCE + "licitacao/";
	
}
