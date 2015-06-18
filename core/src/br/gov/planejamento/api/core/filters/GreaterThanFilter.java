package br.gov.planejamento.api.core.filters;

import java.util.List;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.CoreException;
/**
 * 
 * Esse filtro deve ser utilizado para comparação de ints, floats e doubles.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo é MAIOR QUE o valor informado.
 *
 */
public class GreaterThanFilter extends Filter {
	
	//TODO TODAS ASS FILHAS DE Filter DEVEM ter os construtores dessa maneira:
	protected GreaterThanFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	protected GreaterThanFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	protected GreaterThanFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}
	protected GreaterThanFilter(String...parameters) {
		super(parameters);
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * 
	 */
	public static Filter factory(String... parameters) {
		return new GreaterThanFilter(parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases) {
		return new GreaterThanFilter(databaseAliases);	
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float ou Double.
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(Class<? extends Object> type,
			DatabaseAlias... databaseAliases) throws CoreException {
		if(type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class))
			return new GreaterThanFilter(type, databaseAliases);
		else throw new CoreException(Errors.FILTER_TIPO_INVALIDO, "O tipo passado para o GreaterThanFilter não é compatível. Consulte a documentação para mais informações.");
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float ou Double.
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * 
	 */
	public static Filter factory(Class<? extends Object> type, String... parameters) throws CoreException {
		if(type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class))
			return new GreaterThanFilter(type, parameters);
		else throw new CoreException(Errors.FILTER_TIPO_INVALIDO, "O tipo passado para o GreaterThanFilter não é compatível. Consulte a documentação para mais informações.");
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		statement.append(" > ?");
		for (int i = 1; i < numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" > ?");
		}
		return statement;
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		return this.values.get(parameterAlias.getUriName());
	}
	
}
