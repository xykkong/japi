package br.gov.planejamento.api.core.constants;

public abstract class Constants {

	/**
	 * Vers�es j� lan�adas do sistema
	 */
	public static final class ApiVersions {
		public static final String V1 = "/v1";
	}
	
	/**
	 * Primeira parte da URL de cada m�dulo
	 */
	public static final class Modules {
		public static final String LICITACOES = "/licitacoes";
	}
	
	/**
	 * Uma requisi��o do tipo MIRROR � uma URL espelho que ir� redirecionar para a URL final (DOCUMENT).
	 * A DOCUMENT � de fato a URI que identifica unicamente o documento em quest�o. 
	 */
	public static final class RequestTypes {
		public static final String MIRROR = "/id";
		public static final String DOCUMENT = "/doc";
	}
	
	/**
	 * Constantes de parâmetros fixos
	 */
	public static final class FixedParameters {
		public static final int VALUES_PER_PAGE = 500;
		public static final String PAGINATION = "page";
	}
}
