package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.CoreException;
/**
 * 
 * Esse filtro deve ser utilizado para comparação de ints, floats, doubles, strings e booleans.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo é IGUAL ao valor informado 
 * APROXIMADO para mais e para menos por uma precisão definida.
 *
 */
public class FloatEqualFilter extends Filter {
	private Double precision = (double) 0;
	
	//TODO TODAS ASS FILHAS DE Filter DEVEM ter os construtores dessa maneira:
	protected FloatEqualFilter(Class<? extends Object> type, Double precision, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
		this.precision = precision;
	}
	protected FloatEqualFilter(Class<? extends Object> type, Double precision, String...parameters) {
		super(type, parameters);
		this.precision = precision;
	}
	
	/**
	 * 
	 * @param precision Valor de aproximação definido para a comparação.
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * @throws CoreException 
	 * 
	 */
	public static Filter factory(Double precision, String... parameters){
		return new FloatEqualFilter(Float.class, precision, parameters);
	}
	
	/**
	 * 
	 * @param precision Valor de aproximação definido para a comparação.
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(Double precision, DatabaseAlias... databaseAliases) {
		return new FloatEqualFilter(Float.class, precision, databaseAliases);	
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size()/2;
		statement.append(parameterAlias.getDbName());
		statement.append(" > ? AND ");
		statement.append(parameterAlias.getDbName());
		statement.append(" < ? ");
		for (int i = 1; i < numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" > ? AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" < ? ");
		}
		return statement;
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		ArrayList<String> values = (ArrayList<String>) this.values.get(parameterAlias.getUriName());
		ArrayList<String> approximateValues = new ArrayList<String>();
		for(String value : values){
			Double minValue = Double.parseDouble(value) - precision;
			Double maxValue = Double.parseDouble(value) + precision;
			
			approximateValues.add(""+minValue);
			approximateValues.add(""+maxValue);
		}

		return approximateValues;
	}
	

}
