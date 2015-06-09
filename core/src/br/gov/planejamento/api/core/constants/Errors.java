package br.gov.planejamento.api.core.constants;

public abstract class Errors {

	/* Core Errors - Starting in 500 */
	
	public static final int ERRO_CORE_DESCONHECIDO = 500;
	public static final int CONFIG_LOADER_ERRO_LEITURA = 501;
	public static final int ROUTER_PARAMS_NAO_INFORMADOS = 502;
	public static final int ROUTER_PATH_NAME_INCORRETO = 503;
	public static final int DATABASE_ERRO_CONEXAO = 504;
	public static final int DATABASE_ERRO_QUERY = 505;
	public static final int FILTER_ERRO_INSTANCIACAO = 506;
	public static final int SERVICE_CHAVE_PRIMARIA_NAO_ENCONTRADA = 507;
	public static final int SERVICE_ERRO_STATEMENTS = 508;
	public static final int SERVICE_GET_ONE_RETORNANDO_VARIOS = 509;
	public static final int SERVICE_ERRO_QUERY = 510;
	public static final int SERVICE_ERRO_OFFSET = 511;
	public static final int SERVICE_NULL_SERVICE_CONFIGURATION= 512;
	public static final int SERVICE_RESPONSE_FIELDS_INVALIDO = 513;
	public static final int SERVICE_SCHEMA_INVALIDO = 514;
	public static final int SERVICE_TABELA_INVALIDA = 515;
	public static final int EXCEPTION_RESOLVER_ERRO_DESCONHECIDO = 516;
	public static final int POST_PROCESS_ERRO_DESCONHECIDO = 517;
	public static final int CONFIG_LOADER_DATABASE_NAO_ENCONTRADO = 518;
	public static final int CONFIG_LOADER_TEMPLATE_RESOURCE_NAO_ENCONTRADO = 519;
	public static final int CONFIG_LOADER_TEMPLATE_MODULO_DOCS_NAO_ENCONTRADO = 520;
	public static final int CONFIG_LOADER_TEMPLATE_METODO_DOCS_NAO_ENCONTRADO = 521;
	public static final int CONFIG_LOADER_TEMPLATE_ERRO_NAO_ENCONTRADO = 522;
	public static final int CONFIG_LOADER_TEMPLATE_PAGINA_ESTATICA_NAO_ENCONTRADO = 523;
	public static final int PRE_PROCESS_REQUEST_NAO_ANOTADA_APIMODULE = 524;
	public static final int BOOLEAN_PARAM_ERRO_PROCESSAMENTO = 525;
	public static final int PARAM_ERRO_PROCESSAMENTO = 526;
	public static final int SWAGGER_RESPONSE_SERIALIZER_INVALIDO = 527;
	public static final int RESOURCE_LIST_RESPONSE_ERRO_CONSTRUCAO_RESOURCES = 528;
	public static final int RESOURCE_RESPONSE_RESOURCE_INEXISTENTE = 529;
	public static final int RESOURCE_RESPONSE_ERRO_CONSTRUCAO_RESOURCE = 530;
	public static final int HTML_RESPONSE_SERIALIZER_INVALIDO = 531;
	public static final int CSV_SERIALIZER_ERRO_SERIALIZAR = 532;
	public static final int XML_SERIALIZER_ERRO_CONSTRUCAO_DOCUMENT_BUILDER = 533;
	public static final int XML_SERIALIZER_ERRO_CONSTRUCAO_TRANSFORMER = 534;
	public static final int XML_SERIALIZER_ERRO_PROCESSAR_TRANSFORMER = 535;
	public static final int HTML_SERIALIZER_ERRO_INICIALIZAR_VELOCITY = 536;
	public static final int HTML_SERIALIZER_ERRO_DEFINIR_TEMPLATE = 537;
	public static final int HTML_SERIALIZER_ERRO_PROCESSAR_TEMPLATE = 538;
	public static final int DOCUMENT_RESPONSE_SERIALIZER_ERRO_INICIALIZAR_VELOCITY = 539;
	public static final int DOCUMENT_RESPONSE_SERIALIZER_ERRO_DEFINIR_TEMPLATE = 540;
	public static final int DOCUMENT_RESPONSE_SERIALIZER_ERRO_PROCESSAR_TEMPLATE = 541;
	public static final int SWAGGER_PARSER_ERRO_CRIACAO_JSON = 542;
	public static final int REFLECTION_ERRO_OBTENCAO_PROPRIEDADES_RESOURCE = 543;
	public static final int REFLECTION_ERRO_OBTENCAO_LINKS_RESOURCE = 544;
	public static final int STRING_UTILS_ERRO_CONVERSAO_HASHMAP_JSON = 545;
	public static final int DOCS_URL_INCORRETA = 546;
	public static final int DOCS_DOCUMENTACAO_INEXISTENTE = 547;

	
	/* Request Errors - Starting in 1000 */
	
	public static final int ERRO_REQUEST_DESCONHECIDO = 1000;
	public static final int DATABASE_ALIAS_JA_DEFINIDO = 1001;
	public static final int DATABASE_ALIAS_TIPO_INVALIDO = 1002;
	public static final int DATABASE_ALIAS_LISTA_INVALIDA = 1003;
	public static final int FILTER_TIPO_INVALIDO = 1004;
	public static final int OFFSET_INVALIDO = 1005;
	public static final int ORDER_BY_INVALIDO = 1006;
	public static final int ORDER_INVALIDO = 1007;
	public static final int PARAMETRO_INVALIDO = 1008;
}
