package br.gov.planejamento.api.core.filters;

import br.gov.planejamento.api.core.base.Filter;
import br.gov.planejamento.api.core.base.Session;

public class EqualFilter extends Filter {


	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		Session currentSession = Session.getCurrentSession();
		for(String parameter : parameters){
			int numberOfValues = currentSession.getValues(parameter).size();
			statement.append(parameter);
			for (int i=0; i<numberOfValues; i++) {
				statement.append(" = ? ");				
			}
		}
		return statement.toString();
	}
	@Override
	protected String getProcessedValue(String value) {
		return value;
	}

}
