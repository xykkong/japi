package br.gov.planejamento.api.core.filters;

import java.util.List;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

public class EqualFilter extends Filter {
	
	//TODO TODAS ASS FILHAS DE Filter DEVEM ter os construtores dessa maneira:
	public EqualFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	public EqualFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}
	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size();
		statement.append(parameterAlias.getDbName());
		statement.append(" = ?");
		for (int i = 1; i < numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" = ?");
		}
		return statement;
	}

	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		return this.values.get(parameterAlias.getUriName());
	}

}
