package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.parameters.DateParam;
/**
 * 
 * Esse filtro deve ser utilizado unicamente para comparação de datas.
 * 
 * A comparação do tipo DateParam é feita exclusivamente por este filtro.
 * 
 * O retorno é formado por todos os resources cuja data é exatamente igual (em dia, mês e ano) ao parâmetro recebido.
 *
 */
public class DateBetweenFilter extends Filter {

	private ArrayList<DatabaseAlias> minValues = new ArrayList<DatabaseAlias>();
	private ArrayList<DatabaseAlias> maxValues = new ArrayList<DatabaseAlias>();

	private DateBetweenFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
		getMinAndMaxValues(databaseAliases);
	}
	
	private DateBetweenFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
		getMinAndMaxValues(parameters);
	}

	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size()/2;
		statement.append(parameterAlias.getDbName());
		if(minValues.contains(parameterAlias))
			statement.append(" >= ?::date ");
		else
			statement.append(" <= ?::date ");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			if(minValues.contains(parameterAlias))
				statement.append(" >= ?::date ");
			else
				statement.append(" <= ?::date ");			
		}
		return statement;
	}
	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		List<String> values = new ArrayList<String>();
		for(String value : this.values.get(parameterAlias.getUriName())){
			values.add(value);
		}
		return values;
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * @throws CoreException 
	 * 
	 */
	public static Filter factory(String... parameters) throws CoreException {
		if(!validate(parameters)) throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
		return new DateBetweenFilter(DateParam.class, parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * @throws CoreException 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases) throws CoreException{
		if(!validate(databaseAliases)) throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "O Between filter espera um número par de elementos.");
		return new DateBetweenFilter(DateParam.class, databaseAliases);
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
	
	private static boolean validate(Object[] values) {
		if(values.length%2 == 0 && values.length > 0) return true;
		return false;
	}


}
