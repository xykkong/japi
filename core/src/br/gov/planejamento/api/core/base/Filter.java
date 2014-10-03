package br.gov.planejamento.api.core.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Filter {

	public static final int TYPE_FILTER_STRING = 0;
	public static final int TYPE_FILTER_INTEGER = 1;
	public static final int TYPE_FILTER_DATE = 2;
	public static final int TYPE_FILTER_TIME = 3;
	public static final int TYPE_FILTER_DATETIME = 4;
	public static final int TYPE_FILTER_DOUBLE = 5;

	protected int type = TYPE_FILTER_STRING;
	protected String parameter;

	public abstract String getStatement();

	public abstract ArrayList<String> getValues();
	
	public Filter() {}

	public int getType() {
		return type;
	}

	public void setPreparedStatementValue(PreparedStatement pst, String value,
			int index) throws SQLException, NumberFormatException {
		//TODO filtros de data
		switch (type) {
		case TYPE_FILTER_INTEGER:
			pst.setInt(index, Integer.parseInt(value));
			break;
		case TYPE_FILTER_DOUBLE:
			pst.setDouble(index, Double.parseDouble(value));
			break;
		default:
			pst.setString(index, value);
			break;
		}
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public abstract void setValues(String...values);
}
