package br.gov.planejamento.api.core.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.gov.planejamento.api.core.exceptions.ParametersAndValuesMismatchException;

public abstract class Service {

	protected final String table = "test_table";
	protected final String[] response_parameters = new String[] {
			"teste_string", "teste_int", "teste_numeric", "teste_date",
			"teste_time" };
	protected final String[] required_parameters = new String[] {};
	protected final String[] optional_parameters = new String[] {};
	protected final String[] available_filters = new String[] {};

	public Response getResponse(Request request) throws SQLException {

		ArrayList<Filter> filtersFromRequest = request.getFilters();
		String query = "SELECT " + enumUsingCommas(response_parameters)
				+ " FROM " + table + " WHERE "
				+ filtersQuery(filtersFromRequest);
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement pst = connection.prepareStatement(query);
		int i = 0;
		for (Filter filter : filtersFromRequest) {
			pst.setString(i++, filter.getValue());
		}
		ResultSet rs = pst.executeQuery();
		// TODO fazer direito
		ArrayList<GenericModel> models = new ArrayList<GenericModel>();
		while (rs.next()) {
			Value values[] = new Value[response_parameters.length];
			for (i = 1; i <= response_parameters.length; i++) {
				Value value = new Value(rs.getString(i));
				values[i - 1] = value;
			}
			try {
				models.add(new GenericModel(response_parameters, values, table));
			} catch (ParametersAndValuesMismatchException ex) {
				ex.printStackTrace();
				System.out.println("This shoudn't happen :( " + ex.getMessage()
						+ "\n I mean it, just look at the code.");
			}

		}
		Response response = new Response(models);
		pst.close();
		// connection.close();

		return response;
	}

	private String enumUsingCommas(String[] array) {
		if (array.length > 0) {
			StringBuilder enumComma = new StringBuilder();

			for (String n : array) {
				enumComma.append(n.trim() + ",");
			}
			enumComma.deleteCharAt(enumComma.length() - 1);

			return enumComma.toString();
		}
		return "";
	}

	private String filtersQuery(ArrayList<Filter> filtersFromRequest) {
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for (Filter filter : filtersFromRequest) {
			// TODO validar filtros usando available filterss
			filtersQuery.append(" AND ");
			filtersQuery.append(filter.getParameterName());
			// TODO trocar para like se o Type for string, etc...
			filtersQuery.append(" = ");
			//
			filtersQuery.append("?");
		}
		return filtersQuery.toString();
	}
}
