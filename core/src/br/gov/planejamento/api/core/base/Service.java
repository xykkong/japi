package br.gov.planejamento.api.core.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.constants.Constants;
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterException;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service {

	protected ServiceConfiguration configs = getServiceConfiguration();

	protected abstract ServiceConfiguration getServiceConfiguration();

	public abstract List<String> availableOrderByValues();

	protected DatabaseData getData() throws SQLException,
			InvalidFilterValueTypeException, InvalidOrderSQLParameterException,
			ParserConfigurationException, SAXException, IOException,
			InvalidOrderByValueException, InvalidOffsetValueException {

		Session currentSession = Session.getCurrentSession();
		currentSession.addAvailableOrderByValues(availableOrderByValues());

		String orderByValue = currentSession.getOrderByValue();
		String orderValue = currentSession.getOrderValue();

		// SETUP
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Filter> filtersFromRequest = currentSession.getFilters();

		// QUERY
		StringBuilder sbQuery = new StringBuilder("SELECT ");
		sbQuery.append(StringUtils.join(",", configs.getResponseFields()));

		sbQuery.append(" FROM ");
		sbQuery.append(configs.getSchema());
		sbQuery.append(".");
		sbQuery.append(configs.getTable());

		sbQuery.append(" WHERE ");
		sbQuery.append(getWhereStatement(filtersFromRequest));

		sbQuery.append(" ORDER BY ");
		sbQuery.append(orderByValue);
		sbQuery.append(" ");
		sbQuery.append(orderValue);

		sbQuery.append(" OFFSET ?");

		sbQuery.append(" LIMIT ");
		sbQuery.append(Constants.FixedParameters.VALUES_PER_PAGE);

		PreparedStatement pst = connection.prepareStatement(sbQuery.toString());

		ArrayList<String> whereValues = getWhereValues(filtersFromRequest);
		int index = 1;
		for (Filter filter : filtersFromRequest) {
			index = filter.setPreparedStatementValues(pst, index);
		}

		int offsetValue = currentSession.getOffsetValue();
		pst.setInt(index++, offsetValue);

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
			filtersQuery.append(" AND ");
			filtersQuery.append(filter.getStatement());
		}
		return filtersQuery.toString();
	}

	private ArrayList<String> getWhereValues(
			ArrayList<Filter> filtersFromRequest) {
		ArrayList<String> wheres = new ArrayList<String>();
		for (Filter filter : filtersFromRequest) {
			wheres.addAll(filter.getValues());
		}
		return wheres;
	}
}
