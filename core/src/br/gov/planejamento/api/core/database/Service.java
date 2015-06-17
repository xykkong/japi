package br.gov.planejamento.api.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.constants.Errors;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.filters.BasicEqualFilter;
import br.gov.planejamento.api.core.interfaces.IServiceConfigurationAndFiltersContainer;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service implements IServiceConfigurationAndFiltersContainer{

	public abstract ServiceConfiguration getServiceConfiguration();	
	protected ServiceConfiguration configs = getServiceConfiguration();
	
	/**
	 * Filtros inseridos pela camada de Request
	 */
	private ArrayList<Filter> filters = new ArrayList<Filter>();

	public DataRow getOne() throws ApiException{
		BasicEqualFilter[] equalFilters = configs.getPrimaryKeyEqualFilters();
		if(equalFilters==null){
			throw new CoreException(Errors.SERVICE_CHAVE_PRIMARIA_NAO_ENCONTRADA, "Nenhuma chave primária encontrada na configuração deste service "
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
			throw new CoreException(Errors.SERVICE_ERRO_STATEMENTS, "Houve um erro durante a preparação dos statements da query.", e);
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
					throw new CoreException(Errors.SERVICE_GET_ONE_RETORNANDO_VARIOS, "Mais de duas ocorrências no banco de dados para o service.getOne(). Verifique sua chave primária.");
			}
			pstQuery.close();
			
		} catch(SQLException e) {
			throw new CoreException(Errors.SERVICE_ERRO_QUERY, "Houve um erro durante a execução das queries.", e);
		}
		return row;
	}
	
	/**
	 * Retorna os filtros de consulta ao banco adicionados na camada de Request
	 * @return
	 */
	public ArrayList<Filter> getFilters() {
		return filters;
	}
	
	/**
	 * Adiciona à filtragem da consulta os filtros que tenham seus respectivos query parameters
	 * presentes na query string da requisição 
	 * @param filters Filtros a serem inseridos
	 */
	public void addFilter(Filter...filters) {
		for(Filter filter : filters){
			Boolean addThisFilter = false;
			for(String parameter : filter.getUriParameters()){
				if (RequestContext.getContext().hasParameter(parameter)) {
					addThisFilter = true;
					filter.putValues(parameter, RequestContext.getContext().getValues(parameter));
					System.out.println("\n\tFilter added: "+ parameter+" with "+
							RequestContext.getContext().getValues(parameter).size()+" values.");
				}
			}
			if(addThisFilter)
				this.filters.add(filter);
		}
	}
	
	public DatabaseData getAllFiltered() throws ApiException {

		RequestContext context = RequestContext.getContext();
		context.addAvailableOrderByValues(configs.getValidOrderByStringValues());

		String orderValue = context.getOrderValue();
		
		configValidation();
		
		// SETUP
		ArrayList<Filter> filtersFromRequest = getFilters();
		
		// QUERYS
		
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));
		
		StringBuilder sbCountQuery = new StringBuilder("SELECT COUNT(*) AS quantity ");

		StringBuilder sbQueryGeneric = generateGenericQuery(filtersFromRequest);

		sbQuery.append(sbQueryGeneric);
		sbCountQuery.append(sbQueryGeneric);
		
		endPageQuery(orderValue, sbQuery, getServiceConfiguration());

		return executeQuery(getFilters(), sbQuery.toString(), sbCountQuery.toString(), null, getServiceConfiguration());
	}
	
	public static DatabaseData executeQuery(List<Filter> filters, String query,
			String countQuery,
			Map<String, ServiceConfiguration> mapConfigsAlias,
			ServiceConfiguration...serviceConfigurations) throws CoreException, ApiException {
		
		// SETUP
		RequestContext context = RequestContext.getContext();
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement pstQuery;
		PreparedStatement pstCount;
		
		try {
			pstQuery = connection.prepareStatement(query);
			pstCount = connection.prepareStatement(countQuery);
		} catch (SQLException e) {
			throw new CoreException(Errors.SERVICE_ERRO_STATEMENTS, "Houve um erro durante a preparação dos statements da query.", e);
		}
		

		ArrayList<String> whereValues = getWhereValues(filters);
		int index = 1;
		for (Filter filter : filters) {
			int previousIndex = index;
			index = filter.setPreparedStatementValues(pstQuery, index);
			filter.setPreparedStatementValues(pstCount, previousIndex);
		}

		int offsetValue = context.getOffsetValue();
		
		try {
			pstQuery.setInt(index++, offsetValue);
		} catch (SQLException e) {
			throw new CoreException(Errors.SERVICE_ERRO_OFFSET, "Houve um erro ao definir o valor de offset na construção da query.", e);
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
			throw new CoreException(Errors.SERVICE_ERRO_QUERY, "Houve um erro durante a execução das queries.", e);
		}
		return data;
	}
	
	public static void endPageQuery(String orderValue,
			StringBuilder sbQuery, ServiceConfiguration serviceConfiguration) throws ApiException{
		List<ServiceConfiguration> serviceConfigurations = new ArrayList<ServiceConfiguration>();
		serviceConfigurations.add(serviceConfiguration);
		endPageQuery(orderValue, sbQuery, serviceConfigurations);
	}
	
	public static void endPageQuery(String orderValue,
			StringBuilder sbQuery, Collection<ServiceConfiguration> serviceConfigurations) throws ApiException {
		String orderByValue = RequestContext.getContext().getOrderByValue();
		for(ServiceConfiguration config : serviceConfigurations){
			for(DatabaseAlias alias : config.getValidOrderByValues()){
				if(alias.getUriName().equalsIgnoreCase(orderByValue)){
					orderByValue = alias.getDbName();
					break;
				}
			}
		}
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
			throw new CoreException(Errors.SERVICE_NULL_SERVICE_CONFIGURATION, "O método getServiceConfiguration retornou null. Verifique sua implementação na classe de Service utilizada.");
		}
		if(configs.getResponseFields() == null || configs.getResponseFields().size() == 0) {
			throw new CoreException(Errors.SERVICE_RESPONSE_FIELDS_INVALIDO, "Nenhum campo de retorno foi configurado para este Service. Verifique se o método getServiceConfiguration está implementado corretamente no Service em questão.");
		}
		if(configs.getSchema() == null || configs.getSchema() == "") {
			throw new CoreException(Errors.SERVICE_SCHEMA_INVALIDO, "O schema informado é inválido. Verifique a implementação do método getServiceConfiguration na classe Service utilizada.");
		}
		if(configs.getTable() == null || configs.getTable() == "") {
			throw new CoreException(Errors.SERVICE_TABELA_INVALIDA, "A table informada é inválida. Verifique a implementação do método getServiceConfiguration na classe Service utilizada.");
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
	
	public static String getWhereStatement(Map<String, IServiceConfigurationAndFiltersContainer> mapContainerAlias) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		
		for(Entry<String, IServiceConfigurationAndFiltersContainer> entry : 
			mapContainerAlias.entrySet()){
			IServiceConfigurationAndFiltersContainer container = entry.getValue();
			String tableAlias = entry.getKey();
			for(Filter filter : container.getFilters()){
				for(String dbName : filter.getDbParameters()){
					if(container.getServiceConfiguration().getResponseFields()
							.contains(dbName.toLowerCase())) {
						
						filtersQuery.append(" AND ");
						filtersQuery.append(filter.getStatement(tableAlias));
					}
				}
			}
		}
		return filtersQuery.toString();
	}

	private static ArrayList<String> getWhereValues(
			List<Filter> filters) {
		ArrayList<String> wheres = new ArrayList<String>();
		for (Filter filter : filters) {
			for(DatabaseAlias dbAlias : filter.getParametersAliases()){
				if(RequestContext.getContext().hasParameter(dbAlias.getUriName()))
					wheres.addAll(filter.getValues(dbAlias));
			}
		}
		return wheres;
	}
}
