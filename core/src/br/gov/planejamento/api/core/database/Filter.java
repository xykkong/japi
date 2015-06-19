package br.gov.planejamento.api.core.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeRequestException;
import br.gov.planejamento.api.core.parameters.BooleanParam;
import br.gov.planejamento.api.core.parameters.NullableParam;
import br.gov.planejamento.api.core.parameters.Param;

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
			aliases[i] = DatabaseAlias.fromSpecialString(parameter);
		}
		return aliases;
	}
	
	protected Filter(Class<? extends Object> valueType, DatabaseAlias...databaseAliases) {
		for(DatabaseAlias databaseAlias : databaseAliases ){
			parametersAliases.add(databaseAlias);
		}
		this.valueType = valueType;
	}
	protected Filter(DatabaseAlias...databaseAliases){
		this(String.class, databaseAliases);
	}
	
	protected Filter(String...parameters) {
		this(String.class, parameters);
	}
	
	protected Filter(Class<? extends Object> type, String...parameters) {
		this(type, stringsToAliases(parameters));
	}

	public Class<? extends Object> getValueType() {
		return valueType;
	}

	public int setPreparedStatementValues(PreparedStatement pst, int index)
			throws ApiException {
		int i=index;
		for(DatabaseAlias parameter : parametersAliases){
			List<String> values = getValues(parameter);
			if(values!=null){
				for (String value : values) {
					try {
						if (valueType.equals(Integer.class)) {
							pst.setInt(i++, Integer.parseInt(value));
						} else if (valueType.equals(Double.class)) {
							pst.setDouble(i++, Double.parseDouble(value));
						} else if (valueType.equals(Float.class)) {
							pst.setFloat(i++, Float.parseFloat(value));
						}else if(Param.class.isAssignableFrom(valueType)){
							Param param;
							try {
								param = (Param) valueType.getDeclaredConstructor(new Class[]{String.class}).newInstance(value);
							} catch (InstantiationException
									| IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException
									| NoSuchMethodException | SecurityException e) {
								throw new CoreException(Errors.FILTER_ERRO_INSTANCIACAO, "Houve um erro na instanciação do Filtro", e);
							}
							if(param instanceof NullableParam){
								//if(param.getValue().equals("true")) pst.setObject(i++, "IS NULL");
								//else pst.setObject(i++, "IS NOT NULL");
							}
							else param.setPreparedStatementValue(i++, pst);
						}
						else if(valueType.equals(Boolean.class)){
							//este if foi feito pelo bem da retrocompatibilidade
							BooleanParam bParam = new BooleanParam(value);
							bParam.setPreparedStatementValue(i++, pst);
						}
						else {
							pst.setString(i++, value);
						}
					} catch (SQLException | NumberFormatException ex) {
						ex.printStackTrace();
						throw new InvalidFilterValueTypeRequestException(value, i,
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
	
	public String getStatement(String tableAlias){
		StringBuilder statement = new StringBuilder();
		RequestContext context = RequestContext.getContext();
		Boolean first = true;
		for (DatabaseAlias parameterAlias : parametersAliases) {
			if(tableAlias!=null && tableAlias.length()>0){
				String dbName = parameterAlias.isEscaped()? parameterAlias.getEscapedDbName() : 
					parameterAlias.getDbName();
				parameterAlias.setDbName(tableAlias+"."+dbName);
				parameterAlias.setHasTableAlias(true); 
			}
			
			if(context.hasParameter(parameterAlias.getUriName())){
				if(first)
					first = false;
				else
					statement.append(" AND ");
				if(parameterAlias.isEscaped() && !parameterAlias.hasTableAlias()){
					parameterAlias.setDbName(parameterAlias.getEscapedDbName());
				}
				statement.append(subStatement(parameterAlias));
			}	
		}
		return statement.toString();
	}
	
	public String getStatement(){
		return getStatement(null);
	}
	
	public List<DatabaseAlias> getParametersAliases() {
		return parametersAliases;
	}
}
