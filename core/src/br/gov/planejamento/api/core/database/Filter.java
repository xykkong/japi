package br.gov.planejamento.api.core.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;
import br.gov.planejamento.api.core.parameters.DateParam;

public abstract class Filter {

	protected Class<? extends Object> valueType = String.class;
	protected List<DatabaseAlias> parametersAliases = new ArrayList<DatabaseAlias>();
	protected Map<String, List<String>> values = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);

	public abstract StringBuilder subStatement(DatabaseAlias parameterAlias);

	public abstract List<String> getValues(DatabaseAlias parameterAlias);

	private static DatabaseAlias[] stringsToAliases(String...parameters) {
		DatabaseAlias aliases[] = new DatabaseAlias[parameters.length];
		for(int i = 0; i < parameters.length; i++) {
			String parameter = parameters[i];
			String[] explodedString = parameter.toUpperCase().split(" AS ");
			if(explodedString.length==1)
				aliases[i] = new DatabaseAlias(parameter);
			else if(explodedString.length==2)
				aliases[i] = new DatabaseAlias(explodedString[0], explodedString[1]);
			else
				throw new IllegalArgumentException("Para criar um filtro é esperado um parâmetro ou uma string 'dbName as uriName', encontrado "+parameter);
		}
		return aliases;
	}
	
	public Filter(Class<? extends Object> valueType, DatabaseAlias...databaseAliases) {
		for(DatabaseAlias databaseAlias : databaseAliases ){
			parametersAliases.add(databaseAlias);
		}
		this.valueType = valueType;
	}
	public Filter(DatabaseAlias...databaseAliases){
		this(String.class, databaseAliases);
	}
	
	public Filter(String...parameters) {
		this(String.class, parameters);
	}
	
	public Filter(Class<? extends Object> type, String...parameters) {
		this(type, stringsToAliases(parameters));
	}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public int setPreparedStatementValues(PreparedStatement pst, int index)
			throws InvalidFilterValueTypeJapiException {
		int i=index;
		for(DatabaseAlias parameter : parametersAliases){
			System.out.println(parameter.getUriName());
			List<String> values = getValues(parameter);
			if(values!=null){
				for (String value : values) {
					// TODO filtros de data
					try {
						if (valueType.equals(Integer.class)) {
							pst.setInt(i++, Integer.parseInt(value));
						} else if (valueType.equals(Double.class)) {
							pst.setDouble(i++, Double.parseDouble(value));
						} else if (valueType.equals(Float.class)) {
							pst.setFloat(i++, Float.parseFloat(value));
						}
						else if (valueType.equals(Boolean.class)) {
							pst.setBoolean(i++, Boolean.parseBoolean(value));
						}
						else if (valueType.equals(DateParam.class)){
							DateParam date = new DateParam(value);
							pst.setString(i++, date.getValue());
						}
						else {
							pst.setString(i++, value);
						}
					} catch (SQLException | NumberFormatException ex) {
						System.out.println("wut");
						ex.printStackTrace();
						System.out.println("wut");
						throw new InvalidFilterValueTypeJapiException(value, i,
								pst, valueType);
					}
				}
			}
		}
		return i;

	}

	public void putValues(String uriName, List<String> values) {
		this.values.put(uriName, values);
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
	
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		RequestContext context = RequestContext.getContext();
		Boolean first = true;
		for (DatabaseAlias parameterAlias : parametersAliases) {
			if(context.hasParameter(parameterAlias.getUriName())){
				if(first)
					first = false;
				else
					statement.append(" AND ");
				statement.append(subStatement(parameterAlias));
			}	
		}
		return statement.toString();
	}
	
	public List<DatabaseAlias> getParametersAliases() {
		return parametersAliases;
	}
}
