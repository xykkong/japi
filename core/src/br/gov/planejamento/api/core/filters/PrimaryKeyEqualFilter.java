package br.gov.planejamento.api.core.filters;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.exceptions.CoreException;

/**
 * 
 * Esse filtro deve ser utilizado para comparação de ints, floats, doubles, strings e booleans em PRIMARY KEYS.
 * 
 * O retorno é formado PELO ÚNICO RESOURCE cujo valor desse campo é IGUAL ao valor informado.
 *
 */
public class PrimaryKeyEqualFilter extends BasicEqualFilter{

	private PrimaryKeyEqualFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	private PrimaryKeyEqualFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	private PrimaryKeyEqualFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}
	private PrimaryKeyEqualFilter(String...parameters) {
		super(parameters);
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * 
	 */
	public static PrimaryKeyEqualFilter factory(String... parameters) {
		return new PrimaryKeyEqualFilter(parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static PrimaryKeyEqualFilter factory(DatabaseAlias... databaseAliases) {
		return new PrimaryKeyEqualFilter(databaseAliases);	
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float, Double, String ou BooleanParam.
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static PrimaryKeyEqualFilter factory(Class<? extends Object> type,
			DatabaseAlias... databaseAliases) {
		return new PrimaryKeyEqualFilter(type, databaseAliases);
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float, Double, String ou BooleanParam.
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * @throws CoreException 
	 * 
	 */
	public static PrimaryKeyEqualFilter factory(Class<? extends Object> type, String... parameters) {
		return new PrimaryKeyEqualFilter(type, parameters);
	}
}
