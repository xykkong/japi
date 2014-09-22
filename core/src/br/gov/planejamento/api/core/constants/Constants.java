package br.gov.planejamento.api.core.constants;

public abstract class Constants {

	/**
	 * Versões já lançadas do sistema
	 */
	public static final class ApiVersions {
		public static final String V1 = "/v1";
	}
	
	/**
	 * Primeira parte da URL de cada módulo
	 */
	public static final class Modules {
		public static final String LICITACOES = "/licitacoes";
	}
	
	/**
	 * Uma requisição do tipo MIRROR é uma URL espelho que irá redirecionar para a URL final (DOCUMENT).
	 * A DOCUMENT é de fato a URI que identifica unicamente o documento em questão. 
	 */
	public static final class RequestTypes {
		public static final String MIRROR = "/id";
		public static final String DOCUMENT = "/doc";
	}
	
}
