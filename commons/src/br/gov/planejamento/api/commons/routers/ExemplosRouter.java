package br.gov.planejamento.api.commons.routers;

import br.gov.planejamento.api.core.base.Router;

public class ExemplosRouter extends Router {

	private static ExemplosRouter instance = null;
	
	private ExemplosRouter() {
		super("licitacoes");
	};
	
	public static ExemplosRouter getRouter() {
		if(instance == null) {
			instance = new ExemplosRouter();
		}
		
		return instance;
	}
	
	public static final class Prefixes {
		public static final String LIST = "v1/";
		public static final String RESOURCE = "doc/";
	}
	
	public static final String LISTA_BASE = Prefixes.LIST + "listabase";
	public static final String CONTRATOS = Prefixes.LIST + "contratos";
	public static final String CONTRATO = Prefixes.RESOURCE + "contrato";
	public static final String CONTRATOS_JOIN_EMPRESAS = Prefixes.LIST + "contratosjoin";
	public static final String EMPRESAS = Prefixes.LIST + "empresas";
}
