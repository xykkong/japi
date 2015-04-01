package br.gov.planejamento.api.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.gov.planejamento.api.core.base.RequestContext;
import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.ApiException;
import br.gov.planejamento.api.core.exceptions.CoreException;
import br.gov.planejamento.api.core.exceptions.RequestException;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service {

	protected abstract ServiceConfiguration getServiceConfiguration();	
	protected ServiceConfiguration configs = getServiceConfiguration();

	public DatabaseData getAllFiltered() throws ApiException {

		RequestContext context = RequestContext.getContext();
		context.addAvailableOrderByValues(configs.getValidOrderByValues());

		String orderByValue = context.getOrderByValue();
		String orderValue = context.getOrderValue();
		
		//Valida��o
		if(configs == null) {
			throw new CoreException("O m�todo getServiceConfiguration retornou null. Verifique sua implementa��o na classe de Service utilizada.");
		}
		if(configs.getResponseFields() == null || configs.getResponseFields().size() == 0) {
			throw new CoreException("Nenhum campo de retorno foi configurado para este Service. Verifique se o m�todo getServiceConfiguration est� implementado corretamente no Service em quest�o.");
		}
		if(configs.getSchema() == null || configs.getSchema() == "") {
			throw new CoreException("O schema informado � inv�lido. Verifique a implementa��o do m�todo getServiceConfiguration na classe Service utilizada.");
		}
		if(configs.getTable() == null || configs.getTable() == "") {
			throw new CoreException("A table informada � inv�lida. Verifique a implementa��o do m�todo getServiceConfiguration na classe Service utilizada.");
		}
		
		// SETUP
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Filter> filtersFromRequest = context.getFilters();

		
		// QUERYS
		
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));
		
		StringBuilder sbCountQuery = new StringBuilder("SELECT COUNT(*) AS quantity ");

		StringBuilder sbQueryGeneric = new StringBuilder(" FROM ");
		sbQueryGeneric.append(configs.getSchema());
		sbQueryGeneric.append(".");
		sbQueryGeneric.append(configs.getTable());

		sbQueryGeneric.append(" WHERE ");
		sbQueryGeneric.append(getWhereStatement(filtersFromRequest));

		sbQuery.append(sbQueryGeneric);
		sbCountQuery.append(sbQueryGeneric);
		
		sbQuery.append(" ORDER BY ");
		sbQuery.append(orderByValue);
		sbQuery.append(" ");
		sbQuery.append(orderValue);
		
		sbQuery.append(" OFFSET ?");

		sbQuery.append(" LIMIT ");
		sbQuery.append(Constants.FixedParameters.VALUES_PER_PAGE);

		PreparedStatement pstQuery;
		PreparedStatement pstCount;
		
		try {
			pstQuery = connection.prepareStatement(sbQuery.toString());
			pstCount = connection.prepareStatement(sbCountQuery.toString());
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
			throw new CoreException("Houve um erro ao definir o valor de offset na constru��o da query.", e);
		}

		// DEBUG
		System.out.println("Query executada:");
		System.out.println("\t" + sbQuery.toString());
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
	
			data = new DatabaseData(rs, configs);
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
			throw new CoreException("Houve um erro durante a execu��o das queries.", e);
		}
		return data;
	}

	private String getWhereStatement(ArrayList<Filter> filtersFromRequest) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for (Filter filter : filtersFromRequest) {
			filtersQuery.append(" AND ");
			filtersQuery.append(filter.getStatement());
		}
		return filtersQuery.toString();
	}

	private ArrayList<String> getWhereValues(
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
