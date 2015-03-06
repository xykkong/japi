package br.gov.planejamento.api.common.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;
import br.gov.planejamento.api.core.utils.StringUtils;

public class ZeroFillEqualFilter extends Filter {
	
	public ZeroFillEqualFilter(Class<? extends Object> type, DatabaseAlias...databaseAliases) {
		super(type, databaseAliases);
	}
	
	public ZeroFillEqualFilter(DatabaseAlias...databaseAliases) {
		super(databaseAliases);
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
