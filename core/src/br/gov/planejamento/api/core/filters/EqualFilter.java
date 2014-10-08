package br.gov.planejamento.api.core.filters;

import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.base.Filter;

public class EqualFilter extends Filter {


	@Override
	public String getStatement() {
		/*StringBuilder statement = new StringBuilder();
		statement.append(parameter);
		statement.append(" = ? ");
		return statement.toString();*/
		return "'2' = ?";
	}

	@Override
	public ArrayList<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		//values.add(value);
		values.add("2");
		return values;
		
	}

	@Override
	public void setValues(List<List<String>> values) {
		//this.value = values[0];
	}

}
