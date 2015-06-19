package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.CoreException;
/**
 * 
 * Esse filtro deve ser utilizado para comparação de ints, floats e doubles.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo está entre os valor informado.
 *
 */
public class BasicBetweenFilter extends Filter {
	private ArrayList<DatabaseAlias> minValues = new ArrayList<DatabaseAlias>();
	private ArrayList<DatabaseAlias> maxValues = new ArrayList<DatabaseAlias>();
	
	
	//TODO TODAS ASS FILHAS DE Filter DEVEM ter os construtores dessa maneira:
	protected BasicBetweenFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
		getMinAndMaxValues(databaseAliases);
	}
	protected BasicBetweenFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
		getMinAndMaxValues(databaseAliases);
	}
	protected BasicBetweenFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
		getMinAndMaxValues(parameters);
	}
	protected BasicBetweenFilter(String...parameters) {
		super(parameters);
		getMinAndMaxValues(parameters);
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.Como o filtro é de Between,
	 * é necessário um número par de databaseAlias, sendo sempre o primeiro representante do valor mínimo e o segundo representante do valor máximo.
	 * @throws CoreException 
	 * 
	 */
	public static Filter factory(String... parameters) throws CoreException {
		if(validate(parameters))
			return new BasicBetweenFilter(parameters);
		else throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". Como o filtro é de Between,
	 * é necessário um número par de databaseAlias, sendo sempre o primeiro representante do valor mínimo e o segundo representante do valor máximo.
	 * @throws CoreException 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases) throws CoreException {
		if(validate(databaseAliases)){
			return new BasicBetweenFilter(databaseAliases);
		}
		else throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float ou Double.
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". Como o filtro é de Between,
	 * é necessário um número par de databaseAlias, sendo sempre o primeiro representante do valor mínimo e o segundo representante do valor máximo.
	 * 
	 */
	public static Filter factory(Class<? extends Object> type,
			DatabaseAlias... databaseAliases) throws CoreException {
		if(!validate(databaseAliases)) throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
		if(type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class)){
			return new BasicBetweenFilter(type, databaseAliases);
		}
		else throw new CoreException(Errors.FILTER_TIPO_INVALIDO, "O tipo passado para o BasicEqualFilter não é compatível. Consulte a documentação para mais informações.");
	}
	
	/**
	 * 
	 * @param type Tipo do parâmetro a ser comparado. Devem ser Integer, Float, Double, String ou BooleanParam.
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. Como o filtro é de Between,
	 * é necessário um número par de databaseAlias, sendo sempre o primeiro representante do valor mínimo e o segundo representante do valor máximo.
	 * 
	 */
	public static Filter factory(Class<? extends Object> type, String... parameters) throws CoreException {
		if(!validate(parameters)) throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
		if(type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class)){
			return new BasicBetweenFilter(type, parameters);
		}
		else throw new CoreException(Errors.FILTER_TIPO_INVALIDO, "O tipo passado para o BasicEqualFilter não é compatível. Consulte a documentação para mais informações.");
	}
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		if(minValues.contains(parameterAlias))
			statement.append(" >= ?::float ");
		else
			statement.append(" <= ?::float ");
		for (int i = 1; i < numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			if(minValues.contains(parameterAlias))
				statement.append(" >= ?::float ");
			else
				statement.append(" <= ?::float ");
		}
		return statement;
	}

	private void getMinAndMaxValues(DatabaseAlias[] databaseAliases) {
		boolean impar = true;
		for(DatabaseAlias databaseAlias : databaseAliases){
			if(impar){
				this.minValues.add(databaseAlias);
				impar = false;
			}
			else{
				this.maxValues.add(databaseAlias);
				impar = true;
			}
		}
		
	}
	
	private void getMinAndMaxValues(String... parameters) {
		boolean impar = true;
		for(String parameter : parameters){
			DatabaseAlias databaseAlias = new DatabaseAlias(parameter);
			if(impar){
				this.minValues.add(databaseAlias);
				impar = false;
			}
			else{
				this.maxValues.add(databaseAlias);
				impar = true;
			}
		}
	}
	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		return this.values.get(parameterAlias.getUriName());
	}
	
	private static boolean validate(Object[] values) {
		if(values.length%2 == 0 && values.length > 0) return true;
		return false;
	}

}
