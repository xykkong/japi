package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

public class LikeFilter extends Filter {

	
	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		Session currentSession = Session.getCurrentSession();
		for(DatabaseAlias parameterAlias : parametersAliases){
			int numberOfValues = currentSession.getValues(parameterAlias.getUriName()).size();
			statement.append(parameterAlias.getDbName());
			for (int i=0; i<numberOfValues; i++) {
				statement.append(" like ? ");				
			}
		}
		return statement.toString();
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for(String value : this.values)
			values.add("%" + value + "%");
		return values;
	}

}
