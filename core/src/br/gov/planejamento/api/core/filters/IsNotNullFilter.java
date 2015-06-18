package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
/**
 * 
 * Esse filtro deve ser utilizado para comparação.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo NÃO é NULO.
 *
 */
public class IsNotNullFilter extends Filter {
	
	//TODO TODAS ASS FILHAS DE Filter DEVEM ter os construtores dessa maneira:
	protected IsNotNullFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	protected IsNotNullFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	protected IsNotNullFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}
	protected IsNotNullFilter(String...parameters) {
		super(parameters);
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * 
	 */
	public static Filter factory(String... parameters) {
		return new IsNotNullFilter(parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases) {
		return new IsNotNullFilter(databaseAliases);	
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		statement.append(" IS NOT NULL ?");
		for (int i = 1; i < numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" IS NOT NULL ?");
		}
		return statement;
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		ArrayList<String> values = new ArrayList<String>();
		values.add("");
		return values;
	}
	

}
