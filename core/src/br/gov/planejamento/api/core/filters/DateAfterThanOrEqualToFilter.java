package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
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
public class DateAfterThanOrEqualToFilter extends Filter {

	private DateAfterThanOrEqualToFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	
	private DateAfterThanOrEqualToFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}

	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size()/2;
		statement.append(parameterAlias.getDbName());
		statement.append(" >= ?::date");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" >= ?::date");				
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
	 * 
	 */
	public static Filter factory(String... parameters) {
		return new DateAfterThanOrEqualToFilter(DateParam.class, parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases){
		return new DateAfterThanOrEqualToFilter(DateParam.class, databaseAliases);
	}

}
