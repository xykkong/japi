package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.Filter;

public class LikeFilter extends Filter {

	private String value;

	@Override
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		//statement.append(parameter);
		statement.append(" like ? ");
		return statement.toString();
	}

	@Override
	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("%" + value + "%");
		return values;
	}

	@Override
	public void setValues(List<List<String>> values) {
		
	}


}
