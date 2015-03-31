package br.gov.planejamento.api.core.constants;

public abstract class Constants {

	/**
	 * Versões já lançadas do sistema
	 */
	public static final class ApiVersions {
		public static final String V1 = "/v1";
	}
	
	/**
	 * Uma requisição do tipo MIRROR é uma URL espelho que irá redirecionar para a URL final (DOCUMENT).
	 * A DOCUMENT é de fato a URI que identifica unicamente o documento em questão. 
	 */
	public static final class RequestTypes {
		public static final String MIRROR = "/id";
		public static final String DOCUMENT = "/doc";
	}
	
	/**
	 * Constantes de parâmetros fixos como número de valores por página, order by, etc
	 */
	public static final class FixedParameters {
		public static final int VALUES_PER_PAGE = 500;
		public static final String ORDER_BY = "order_by";
		public static final String ORDER = "order";
		public static final String[] VALID_ORDERS = new String[]{"ASC", "DESC"};
		public static final String OFFSET = "offset";
		public static final String[] DEFAULT_URI_PARAMETERS = new String[]{
			ORDER_BY,
			ORDER,
			OFFSET
		};
	}
	
	public static final class RequestFormats {
		public static final String HTML = "html";
		public static final String JSON = "json";
		public static final String XML = "xml";
		public static final String CSV = "csv";
	}
	
	public static final class DateFormats {
		public static final String AMERICAN = "yyyy-MM-dd";
		public static final String BRAZILIAN = "dd/MM/yyyy";
	}
	
	public static final class Configuration {
		public static final String CONFIG_FILE_NAME = "japi_config.json";
	}
	
	public static final class HttpStatusCodes {
		public static final String NOT_FOUND = "404";
		public static final String BAD_REQUEST = "400";
		public static final String INTERNAL_ERROR = "500";
	}
}
