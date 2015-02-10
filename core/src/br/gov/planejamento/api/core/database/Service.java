package br.gov.planejamento.api.core.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.gov.planejamento.api.core.base.Session;
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

		PreparedStatement pstQuery = connection.prepareStatement(sbQuery.toString());
		PreparedStatement pstCount = connection.prepareStatement(sbCountQuery.toString());

		ArrayList<String> whereValues = getWhereValues(filtersFromRequest);
		int index = 1;
		for (Filter filter : filtersFromRequest) {
			index = filter.setPreparedStatementValues(pstQuery, index);
			index = filter.setPreparedStatementValues(pstCount, 1);
		}

		int offsetValue = currentSession.getOffsetValue();
		pstQuery.setInt(index++, offsetValue);

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

		ResultSet rs = pstQuery.executeQuery();

		DatabaseData data = new DatabaseData(rs, configs);
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
