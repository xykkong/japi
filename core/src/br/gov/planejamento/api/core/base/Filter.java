package br.gov.planejamento.api.core.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;

public abstract class Filter {

	protected Class<? extends Object> valueType = String.class;
	protected List<String> parameters = null;

	public abstract String getStatement();
	protected abstract String getProcessedValue(String value);

	public List<String> getValues() {
		ArrayList<String> values = new ArrayList<String>();
		Session currentSession = Session.getCurrentSession();
		for (String parameter : parameters) {
			values.addAll(currentSession.getValues(parameter));
		}
		return values;
	}

	public Filter() {
	}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public int setPreparedStatementValues(PreparedStatement pst, int index)
			throws InvalidFilterValueTypeException {
		Session currentSession = Session.getCurrentSession();
		for (String parameter : parameters) {
			List<String> values = currentSession.getValues(parameter);

			for (String value : values) {

				// TODO filtros de data
				try {
					if (valueType.equals(Integer.class)) {
						pst.setInt(index++, Integer.parseInt(getProcessedValue(value)));
					} else if (valueType.equals(Double.class)) {
						pst.setDouble(index++, Double.parseDouble(getProcessedValue(value)));
					}
					else {
						pst.setString(index++, getProcessedValue(value));
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
		if(parameters == null){
			parameters = new ArrayList<String>();
		}
		parameters.add(parameter);
	}

	public void setValueType(Class<? extends Object> valueType) {
		this.valueType = valueType;
	}
}
