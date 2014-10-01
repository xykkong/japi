package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;

import br.gov.planejamento.api.core.base.Filter;

public class LikeFilter extends Filter {

	private String parameter, value;

	public LikeFilter(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}

	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		statement.append(parameter);
		statement.append(" like ? ");
		return statement.toString();
	}

	@Override
	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("%" + value + "%");
		return values;
	}

}
