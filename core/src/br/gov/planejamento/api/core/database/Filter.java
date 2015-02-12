package br.gov.planejamento.api.core.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;

public abstract class Filter {

	protected Class<? extends Object> valueType = String.class;
	protected List<DatabaseAlias> parametersAliases = new ArrayList<DatabaseAlias>();
	protected List<String> values = new ArrayList<String>();

	public abstract String getStatement();

	public abstract List<String> getValues();

	public Filter(Class<? extends Object> valueType, DatabaseAlias...databaseAliases) {
		for(DatabaseAlias databaseAlias : databaseAliases ){
			parametersAliases.add(databaseAlias);
		}
		this.valueType = valueType;
	}
	public Filter(DatabaseAlias...databaseAliases){
		this(String.class, databaseAliases);
	}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public int setPreparedStatementValues(PreparedStatement pst, int index)
			throws InvalidFilterValueTypeJapiException {
		for (int i = 0; i < parametersAliases.size(); i++) {
			for (String value : getValues()) {

				// TODO filtros de data
				try {
					if (valueType.equals(Integer.class)) {
						pst.setInt(index++, Integer.parseInt(value));
					} else if (valueType.equals(Double.class)) {
						pst.setDouble(index++, Double.parseDouble(value));
					} else if (valueType.equals(Float.class)) {
						pst.setFloat(index++, Float.parseFloat(value));
					}
					else {
						pst.setString(index++, value);
					}
				} catch (SQLException | NumberFormatException ex) {
					throw new InvalidFilterValueTypeJapiException(value, index,
							pst, valueType);
				}
			}
		}
		return index;

	}

	public void addValues(List<String> values) {
		this.values.addAll(values);
	}
	
	public List<String> getDbParameters() {
		List<String> parameters = new ArrayList<String>();
		for(DatabaseAlias p : this.parametersAliases){
			parameters.add(p.getDbName());
		}
		return parameters;
	}
	
	public List<String> getUriParameters(){
		List<String> parameters = new ArrayList<String>();
		for(DatabaseAlias p : this.parametersAliases){
			parameters.add(p.getUriName());
		}
		return parameters;
	}
}
