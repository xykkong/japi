package br.gov.planejamento.api.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service implements ServiceConfigurationContainer{

	protected ServiceConfiguration configs = getServiceConfiguration();

	public DataRow getOne() throws ApiException{
		BasicEqualFilter[] equalFilters = configs.getPrimaryKeyEqualFilters();
		if(equalFilters==null){
			throw new CoreException("Nenhuma chave primária encontrada na configuração deste service "
					+this.getClass().getCanonicalName());
		}
		return getOne(equalFilters);
	}
	/**
	 * 
	 * @param equalFilters
	 * @return a dataRow encontrada, nulo caso não encontre nenhuma
	 * @throws ApiException
	 */
	public DataRow getOne(BasicEqualFilter...equalFilters) throws ApiException{
		configValidation();
		ArrayList<Filter> filtersList = new ArrayList<Filter>(Arrays.asList(equalFilters));
		for (Filter filter : equalFilters) {
			RequestContext.getContext().addFilter(filter);
		}
		// SETUP
		Connection connection = ConnectionManager.getConnection();
		
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));
		StringBuilder sbQueryGeneric = generateGenericQuery(filtersList);
		sbQuery.append(sbQueryGeneric);
		sbQuery.append("LIMIT 2");
		
		PreparedStatement pstQuery;
		
		try {
			pstQuery = connection.prepareStatement(sbQuery.toString());
		} catch (SQLException e) {
			throw new CoreException("Houve um erro durante a preparação dos statements da query.", e);
		}
		

		ArrayList<String> whereValues = getWhereValues(filtersList);
		int index = 1;
		for (Filter filter : equalFilters) {
			index = filter.setPreparedStatementValues(pstQuery, index);
		}

		// DEBUG
		System.out.println("Query executada:");
		System.out.println("\t" + sbQuery.toString());
		if (whereValues.size() > 0) {
			System.out.println("\t valores where:");
			System.out.print("\t");
			System.out.println(StringUtils.join(", ", whereValues));
		} else
			System.out.println("\tNenhum valor no where (considerado 1=1)");

		// EXECUTE
		DataRow row = null;
		try {
			ResultSet rs = pstQuery.executeQuery();
			DatabaseData data = new DatabaseData(rs, configs);
			Iterator<DataRow> iterator = data.iterator();
			if(iterator.hasNext()){
				row = iterator.next();
				if(iterator.hasNext())
					throw new CoreException("Mais de duas ocorrências no banco de dados para o service.getOne(). Verifique sua chave primária.");
			}
			pstQuery.close();
			
		} catch(SQLException e) {
			throw new CoreException("Houve um erro durante a execução das queries.", e);
		}
		return row;
	}
	
	public DatabaseData getAllFiltered() throws ApiException {

		RequestContext context = RequestContext.getContext();
		context.addAvailableOrderByValues(configs.getValidOrderByValues());

		String orderByValue = context.getOrderByValue();
		String orderValue = context.getOrderValue();
		
		configValidation();
		
		// QUERYS
		
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));
		
		StringBuilder sbCountQuery = new StringBuilder("SELECT COUNT(*) AS quantity ");

		StringBuilder sbQueryGeneric = generateGenericQuery(context.getFilters());

		sbQuery.append(sbQueryGeneric);
		sbCountQuery.append(sbQueryGeneric);
		
		endPageQuery(orderByValue, orderValue, sbQuery);

		return executeQuery(sbQuery.toString(), sbCountQuery.toString(), null, getServiceConfiguration());
	}
	
	public static DatabaseData executeQuery(String query,
			String countQuery,
			Map<ServiceConfiguration, String> mapConfigsAlias,
			ServiceConfiguration...serviceConfigurations) throws CoreException, ApiException {
		
		// SETUP
		RequestContext context = RequestContext.getContext();
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Filter> filtersFromRequest = context.getFilters();
		PreparedStatement pstQuery;
		PreparedStatement pstCount;
		
		try {
			pstQuery = connection.prepareStatement(query);
			pstCount = connection.prepareStatement(countQuery);
		} catch (SQLException e) {
			throw new CoreException("Houve um erro durante a prepara��o dos statements da query.", e);
		}
		

		ArrayList<String> whereValues = getWhereValues(filtersFromRequest);
		int index = 1;
		for (Filter filter : filtersFromRequest) {
			int previousIndex = index;
			index = filter.setPreparedStatementValues(pstQuery, index);
			filter.setPreparedStatementValues(pstCount, previousIndex);
		}

		int offsetValue = context.getOffsetValue();
		
		try {
			pstQuery.setInt(index++, offsetValue);
		} catch (SQLException e) {
			throw new CoreException("Houve um erro ao definir o valor de offset na construção da query.", e);
		}

		String orderByValue = context.getOrderByValue();
		String orderValue = context.getOrderValue();
		// DEBUG
		System.out.println("Query executada:");
		System.out.println("\t" + query.toString());
		System.out.print("\tvalores ORDER BY: ");
		System.out.print(orderByValue + " ");
		System.out.println(orderValue);
		System.out.print("\tvalor OFFSET: ");
		System.out.println(offsetValue);
		if (whereValues.size() > 0) {
			System.out.println("\t valores where:");
			System.out.print("\t");
			System.out.println(StringUtils.join(", ", whereValues));
		} else
			System.out.println("\tNenhum valor no where (considerado 1=1)");

		// EXECUTE
		DatabaseData data;
		try {
			ResultSet rs = pstQuery.executeQuery();
	
			if(mapConfigsAlias==null){
				data = new DatabaseData(rs, serviceConfigurations);
			}else{
				data = new DatabaseData(rs, mapConfigsAlias);
			}
			
			pstQuery.close();
			
			rs = pstCount.executeQuery();
			
			int count = 0;
			if(rs.next()){
				count = rs.getInt("quantity");
				data.setCount(count);
				//DEBUG count
				System.out.println("quantity " + count);
			}
			data.setCount(count);
			pstCount.close();
		} catch(SQLException e) {
			throw new CoreException("Houve um erro durante a execução das queries.", e);
		}
		return data;
	}
	
	public static void endPageQuery(String orderByValue, String orderValue,
			StringBuilder sbQuery) {
		sbQuery.append(" ORDER BY ");
		sbQuery.append(orderByValue);
		sbQuery.append(" ");
		sbQuery.append(orderValue);
		
		sbQuery.append(" OFFSET ?");

		sbQuery.append(" LIMIT ");
		sbQuery.append(Constants.FixedParameters.VALUES_PER_PAGE);
	}

	private void configValidation() throws CoreException {
		if(configs == null) {
			throw new CoreException("O método getServiceConfiguration retornou null. Verifique sua implementação na classe de Service utilizada.");
		}
		if(configs.getResponseFields() == null || configs.getResponseFields().size() == 0) {
			throw new CoreException("Nenhum campo de retorno foi configurado para este Service. Verifique se o método getServiceConfiguration está implementado corretamente no Service em questão.");
		}
		if(configs.getSchema() == null || configs.getSchema() == "") {
			throw new CoreException("O schema informado é inválido. Verifique a implementação do método getServiceConfiguration na classe Service utilizada.");
		}
		if(configs.getTable() == null || configs.getTable() == "") {
			throw new CoreException("A table informada é inválida. Verifique a implementação do método getServiceConfiguration na classe Service utilizada.");
		}
	}

	private StringBuilder generateGenericQuery(ArrayList<Filter> filters) {
		StringBuilder sbQueryGeneric = new StringBuilder(" FROM ");
		configs.appendSchemaDotTable(sbQueryGeneric);

		sbQueryGeneric.append(" WHERE ");
		sbQueryGeneric.append(getWhereStatement(filters));
		return sbQueryGeneric;
	}

	public static String getWhereStatement(ArrayList<Filter> filtersFromRequest) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for (Filter filter : filtersFromRequest) {
			filtersQuery.append(" AND ");
			filtersQuery.append(filter.getStatement());
		}
		return filtersQuery.toString();
	}
	
	public static String getWhereStatement(ArrayList<Filter> filtersFromRequest, Map<ServiceConfiguration, String> mapConfigAlias) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		
		for (Filter filter : filtersFromRequest) {
			for(ServiceConfiguration config : mapConfigAlias.keySet()){
				for(String dbName : filter.getDbParameters()){
					if(config.getResponseFields().contains(dbName)) {
						filtersQuery.append(" AND ");
						filtersQuery.append(filter.getStatement(mapConfigAlias.get(config)));
					}
				}
			}
		}
		return filtersQuery.toString();
	}

	private static ArrayList<String> getWhereValues(
			ArrayList<Filter> filtersFromRequest) {
		ArrayList<String> wheres = new ArrayList<String>();
		for (Filter filter : filtersFromRequest) {
			for(DatabaseAlias dbAlias : filter.getParametersAliases()){
				if(RequestContext.getContext().hasParameter(dbAlias.getUriName()))
					wheres.addAll(filter.getValues(dbAlias));
			}
		}
		return wheres;
	}
}
