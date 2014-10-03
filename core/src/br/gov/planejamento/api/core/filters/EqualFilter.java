package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Filter;

public abstract class EqualFilter extends Filter {
	
	private String value;
	protected int type;
	
	//public abstract String getType();
	
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

	@Override
	public void setValues(String...values) {
		this.value = values[0];
	}

}
