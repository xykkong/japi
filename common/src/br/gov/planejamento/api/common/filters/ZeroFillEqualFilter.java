package br.gov.planejamento.api.common.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.Session;
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
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		Session currentSession = Session.getCurrentSession();
		for(DatabaseAlias parameterAlias : parametersAliases){
			int numberOfValues = currentSession.getValues(parameterAlias.getUriName()).size();
			statement.append(parameterAlias.getDbName());
			statement.append(" SIMILAR TO ");
			for (int i=0; i<numberOfValues; i++) {
				statement.append("?");
			}
		}
		return statement.toString();
	}
	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(String value : this.values){
			StringBuilder response = new StringBuilder("0*");
			response.append(StringUtils.removeLeftZero(value));
			values.add(response.toString());
		}
		return values;
		
	}

}
