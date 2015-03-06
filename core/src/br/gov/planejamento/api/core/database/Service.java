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
import br.gov.planejamento.api.core.exceptions.InvalidFilterValueTypeJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOffsetValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderByValueJapiException;
import br.gov.planejamento.api.core.exceptions.InvalidOrderSQLParameterJapiException;
import br.gov.planejamento.api.core.exceptions.JapiException;
import br.gov.planejamento.api.core.utils.StringUtils;

public abstract class Service {

	protected ServiceConfiguration configs = getServiceConfiguration();

	protected abstract ServiceConfiguration getServiceConfiguration();

	protected DatabaseData getAllFiltered() throws JapiException {

		try {
			Session currentSession = Session.getCurrentSession();
			currentSession.addAvailableOrderByValues(configs.getValidOrderByValues());
	
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
				int previousIndex = index;
				index = filter.setPreparedStatementValues(pstQuery, index);
				filter.setPreparedStatementValues(pstCount, previousIndex);
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
		} catch(Exception exception) {
			throw new JapiException(exception);
		}
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
				if(Session.getCurrentSession().hasParameter(dbAlias.getUriName()))
					wheres.addAll(filter.getValues(dbAlias));
			}
		}
		return wheres;
	}
}
