package br.gov.planejamento.api.core.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Filter {

	protected Class<? extends Object> valueType = String.class;
	protected List<String> parameters;

	public abstract String getStatement();

	public abstract ArrayList<String> getValues();
	
	public Filter() {}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public void setPreparedStatementValue(PreparedStatement pst, String value,
			int index) throws SQLException, NumberFormatException {
		//TODO filtros de data
		if(valueType.equals(Integer.class)) {
			pst.setInt(index, Integer.parseInt(value));
		} else if(valueType.equals(Double.class)) {
			pst.setDouble(index, Double.parseDouble(value));
		} else {
			pst.setString(index, value);
		}
	}
	
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public abstract void setValues(List<List<String>> values);
}
