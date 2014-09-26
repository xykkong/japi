package br.gov.planejamento.api.core.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import br.gov.planejamento.api.core.exceptions.ParametersAndValuesMismatchException;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service {
	
	protected ServiceConfiguration configs = getServiceConfiguration();
	protected Request request = Request.getCurrentRequest();
	
	protected abstract ServiceConfiguration getServiceConfiguration();
	
	public DatabaseData getData() throws SQLException {	
		
		//SETUP
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Filter> filtersFromRequest = request.getFilters();
		
		//QUERY
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		sbQuery.append(StringUtils.join(",", configs.getResponseParameters()));
		sbQuery.append(" FROM ");
		sbQuery.append(configs.getTable());
		sbQuery.append(" WHERE ");
		sbQuery.append(getWhereStatement(filtersFromRequest));
		
		PreparedStatement pst = connection.prepareStatement(sbQuery.toString());
		
		ArrayList<String> whereValues = getWhereValues(filtersFromRequest);
		for(int i = 0; i < whereValues.size(); i++) {
			pst.setString(i, whereValues.get(i));
		}
		
		//EXECUTE
		ResultSet rs = pst.executeQuery();
		DatabaseData data = new DatabaseData(rs, configs);
		pst.close();

		return data;
	}

	private String getWhereStatement(ArrayList<Filter> filtersFromRequest) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for (Filter filter : filtersFromRequest) {
			// TODO validar filtros usando available filters
			filtersQuery.append(filter.getStatement());
		}
		return filtersQuery.toString();
	}
	
	private ArrayList<String> getWhereValues(ArrayList<Filter> filtersFromRequest) {
		ArrayList<String> parameters = new ArrayList<String>();
		for (Filter filter : filtersFromRequest) {
			for(String value : filter.getValues()) {
				parameters.add(value);
			}
		}
		return parameters;
	}
}
