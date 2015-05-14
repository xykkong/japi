package br.gov.planejamento.api.commons.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.utils.StringUtils;

/**
 * 
 * Esse filtro deve ser utilizado para comparação de Integers que devam ser preenchidos com zeros à esquerda.
 * 
 * O retorno é formado por todos os resources cujo valor desse campo é igual ao valor informado preenchido com 
 * uma quantidade qualquer de zeros à esquerda.
 *
 */
public class ZeroFillEqualFilter extends Filter {
	
	private ZeroFillEqualFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	
	
	private ZeroFillEqualFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}
	
	/**
	 * 
	 * @param databaseAliases DatabaseAliases que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula. 
	 * O padrão de escrita de DatabaseAlias é a seguinte: "nome_do_campo_no_banco as name_do_parametro_da_request". 
	 * 
	 */
	public static Filter factory(DatabaseAlias... databaseAliases){
		return new ZeroFillEqualFilter(Integer.class, databaseAliases);
	}
	
	/**
	 * 
	 * @param parameters Strings que contêm o nome dos parâmetros da Request cujo filtro deve ser aplicado, separados por vírgula.
	 * 
	 */
	public static Filter factory(String... parameters) {
		return new ZeroFillEqualFilter(Integer.class, parameters);
	}

	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		statement.append(" SIMILAR TO ?");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" SIMILAR TO ?");
		}
		return statement;
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		List<String> values = new ArrayList<String>();
		for(String value : this.values.get(parameterAlias.getUriName())){
			StringBuilder response = new StringBuilder("0*");
			response.append(StringUtils.removeLeftZero(value));
			values.add(response.toString());
		}
		return values;
	}

}
