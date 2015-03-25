package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

public class DateEqualFilter extends Filter {

	public DateEqualFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	public DateEqualFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
	}

	
	@Override
	public StringBuilder subStatement(DatabaseAlias parameterAlias) {
		StringBuilder statement = new StringBuilder();
		int numberOfValues = getValues(parameterAlias).size()/2;
		statement.append(parameterAlias.getDbName());
		statement.append(" >= ?::date AND "+parameterAlias.getDbName()+" <(?::date + '1 day'::interval)");
		for (int i=1; i<numberOfValues; i++) {
			statement.append(" AND ");
			statement.append(parameterAlias.getDbName());
			statement.append(" >= ?::date AND "+parameterAlias.getDbName()+" <(?::date + '1 day'::interval)");				
		}
		return statement;
	}
	@Override
	public List<String> getValues(DatabaseAlias parameterAlias) {
		List<String> values = new ArrayList<String>();
		for(String value : this.values.get(parameterAlias.getUriName())){
			values.add(value);
			values.add(value);
		}
		return values;
	}

}
