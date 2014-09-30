package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Filter;

public class EqualFilter extends Filter {
	
	private String parameter, value;
	
	public EqualFilter(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}
	
	public EqualFilter(String parameter, String value, int type) {
		this(parameter, value);
		this.type = type;
	}
	
	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		statement.append(parameter);
		statement.append(" = ? ");
		return statement.toString();
	}

	@Override
	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add(value);
		return values;
	}

}
