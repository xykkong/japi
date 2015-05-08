package br.gov.planejamento.api.commons.routers;

import br.gov.planejamento.api.core.base.Router;

public class ExemplosRouter extends Router {

	public static final class Prefixes {
		public static final String LIST = "v1/";
		public static final String RESOURCE = "doc/";
	}
	
	@Override
	public String getModulePath() {
		return "exemplos";
	}		
	
	public static final String LISTA_BASE = Prefixes.LIST + "listabase";
	public static final String CONTRATOS = Prefixes.LIST + "contratos";
	public static final String CONTRATOS_JOIN_EMPRESAS = Prefixes.LIST + "contratos-join-empresas";
	public static final String CONTRATO = Prefixes.RESOURCE + "contrato";
	public static final String EMPRESAS = Prefixes.RESOURCE + "empresas";
}
