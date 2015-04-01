package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

public class CaseInsensitiveLikeFilter extends Filter {

	public CaseInsensitiveLikeFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	public CaseInsensitiveLikeFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	
	public CaseInsensitiveLikeFilter(String...parameters) {
		super(parameters);
	}
	
	public CaseInsensitiveLikeFilter(Class<? extends Object> type, String...parameters) {
		super(type, parameters);
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		List<String> values = new ArrayList<String>();
		for(String value : this.values.get(parameterAlias.getUriName()))
			values.add("%" + value.toLowerCase().trim() + "%");
		return values;
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append("LOWER( ");
		statement.append(parameterAlias.getDbName());
		statement.append(" )");
		statement.append(" like ? ");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND LOWER( ");
			statement.append(parameterAlias.getDbName());
			statement.append(" ) like ? ");
		}
		return statement;
	}

}
