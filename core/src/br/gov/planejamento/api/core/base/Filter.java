package br.gov.planejamento.api.core.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;

public abstract class Filter {

	protected Class<? extends Object> valueType = String.class;
	protected List<String> parameters = new ArrayList<String>();
	protected List<String> values = new ArrayList<String>();

	public abstract String getStatement();

	public abstract List<String> getValues();

	public Filter() {
	}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public int setPreparedStatementValues(PreparedStatement pst, int index)
			throws InvalidFilterValueTypeException {
		for (String parameter : parameters) {
			for (String value : getValues()) {

				// TODO filtros de data
				try {
					if (valueType.equals(Integer.class)) {
						pst.setInt(index++, Integer.parseInt(value));
					} else if (valueType.equals(Double.class)) {
						pst.setDouble(index++, Double.parseDouble(value));
					} else {
						pst.setString(index++, value);
					}
				} catch (SQLException | NumberFormatException ex) {
					ex.printStackTrace();
					throw new InvalidFilterValueTypeException(value, index,
							pst, valueType);
				}
			}
		}
		return index;

	}

	public void addParameter(String parameter) {
		parameters.add(parameter);
	}

	public void setValueType(Class<? extends Object> valueType) {
		this.valueType = valueType;
	}

	public void addValues(List<String> values) {
		this.values.addAll(values);
	}
	
	public List<String> getParameters() {
		return parameters;
	}
}
