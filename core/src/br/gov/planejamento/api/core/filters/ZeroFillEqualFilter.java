package br.gov.planejamento.api.core.filters;

import br.gov.planejamento.api.core.base.Filter;
import br.gov.planejamento.api.core.base.Session;
import br.gov.planejamento.api.core.utils.StringUtils;

public class ZeroFillEqualFilter extends Filter {


	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		Session currentSession = Session.getCurrentSession();
		for(String parameter : parameters){
			int numberOfValues = currentSession.getValues(parameter).size();
			statement.append(parameter);
			statement.append(" SIMILAR TO ");
			for (int i=0; i<numberOfValues; i++) {
				statement.append("?");
			}
		}
		return statement.toString();
	}
	@Override
	protected String getProcessedValue(String value) {
		StringBuilder response = new StringBuilder("0*");
		response.append(StringUtils.removeLeftZero(value));
		return response.toString();
	}

}
