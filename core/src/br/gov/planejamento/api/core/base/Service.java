package br.gov.planejamento.api.core.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service {

	protected ServiceConfiguration configs = getServiceConfiguration();
	protected Session currentRequest = Session.getCurrentSession();

	protected abstract ServiceConfiguration getServiceConfiguration();

	protected DatabaseData getData() throws SQLException, InvalidFilterValueTypeException {

		// SETUP
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Filter> filtersFromRequest = currentRequest.getFilters();

		// QUERY
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));
		sbQuery.append(" FROM ");
		sbQuery.append(configs.getSchema() + "." + configs.getTable());
		sbQuery.append(" WHERE ");
		sbQuery.append(getWhereStatement(filtersFromRequest));
		sbQuery.append(" LIMIT 10 ");

		PreparedStatement pst = connection.prepareStatement(sbQuery.toString());

		ArrayList<String> whereValues = getWhereValues(filtersFromRequest);
		int index=1;
		for (Filter filter : filtersFromRequest) {
			index = filter.setPreparedStatementValues(pst, index);
		}

		// DEBUG
		System.out.println("Query executada:");
		System.out.println("\t" + sbQuery.toString());
		if (whereValues.size() > 0) {
			System.out.println("\t valores where:");
			System.out.print("\t");
			System.out.println(StringUtils.join(",", whereValues));
		} else
			System.out.println("\tnenhum valor no where (considerado 1=1)");

		// EXECUTE

		ResultSet rs = pst.executeQuery();
		DatabaseData data = new DatabaseData(rs, configs);
		pst.close();

		return data;
	}

	private String getWhereStatement(ArrayList<Filter> filtersFromRequest) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for (Filter filter : filtersFromRequest) {
			// TODO validar filtros usando available filters
			filtersQuery.append(" AND ");
			filtersQuery.append(filter.getStatement());
		}
		return filtersQuery.toString();
	}

	private ArrayList<String> getWhereValues(
			ArrayList<Filter> filtersFromRequest) {
		ArrayList<String> parameters = new ArrayList<String>();
		for (Filter filter : filtersFromRequest) {
			for (String value : filter.getValues()) {
				parameters.add(value);
			}
		}
		return parameters;
	}
}
