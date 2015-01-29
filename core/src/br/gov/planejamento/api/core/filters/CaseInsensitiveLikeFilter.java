package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.database.DatabaseAlias;
import br.gov.planejamento.api.core.database.Filter;

public class CaseInsensitiveLikeFilter extends Filter {

	
	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		Session currentSession = Session.getCurrentSession();
		for(DatabaseAlias parameter : parametersAliases){
			int numberOfValues = currentSession.getValues(parameter.getUriName()).size();
			statement.append("LOWER( ");
			statement.append(parameter.getDbName());
			statement.append(" )");
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
			values.add("%" + value.toLowerCase().trim() + "%");
		return values;
	}

}
