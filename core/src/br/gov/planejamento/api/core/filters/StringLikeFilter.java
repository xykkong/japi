package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

/**
 * 
 * Esse filtro deve ser utilizado para comparação de Strings.
 * 
 * Ocorre uma comparação do valor da String informada no filtro com todas as ocorrências no banco de dados.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo CONTÉM o valor informado.
 * 
 * Esse filtro é case sensitive. Para comparações case insensitive, utilize CaseInsensitiveLikeFilter.
 *
 */
public class StringLikeFilter extends Filter {
	
	private StringLikeFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	
	private StringLikeFilter(String...parameters) {
		super(parameters);
	}
	
	/**
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 *
	 */
	public static Filter factory(String... parameters) {
		return new StringLikeFilter(parameters);
	}

	/**
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 *
	 */
	
	public static Filter factory(DatabaseAlias... databaseAliases) {
		return new StringLikeFilter(databaseAliases);
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		statement.append(" like ? ");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" like ? ");				
		}
		return statement;
	}
	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		List<String> values = new ArrayList<String>();
		for(Entry<String, List<String>> value : this.values.entrySet())
			if(value.getKey().equals(parameterAlias.getUriName()))
				values.add("%" + value.getValue().get(0) + "%");
		return values;
	}

}
