package br.gov.planejamento.api.core.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public abstract class Service {
	
	protected final String table = "test_table";
	protected final String[] response_parameters = new String[]{"teste_string", "teste_int"};
	protected final String[] required_parameters = new String[]{};
	protected final String[] optional_parameters = new String[]{};
	protected final String[] available_filters = new String[]{};
	
	
	public Response getResponse(Request request) throws SQLException{
		ArrayList<Filter> filtersFromRequest = request.getFilters();
		String query = "SELECT "+enumUsingCommas(response_parameters)+
				" FROM "+ table +
				" WHERE "+ filtersQuery(filtersFromRequest);
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement pst = connection.prepareStatement(query);
		int i=0;
		for(Filter filter : filtersFromRequest){
			pst.setString(i++, filter.getValue());
		}
		ResultSet rs = pst.executeQuery();
		//TODO fazer direitos
		StringBuilder result = new StringBuilder();
		while(rs.next()){
			for(i=1; i<=response_parameters.length; i++){
				result.append(rs.getString(i));
			}
		}
		pst.close();
		//connection.close();
		String temp = result.toString();
		
		
		System.out.println("SQL:" + temp);
		
		return new Response(result.toString());
	}
	
	private String enumUsingCommas(String[] array){
		if (array.length > 0) {
		    StringBuilder enumComma = new StringBuilder();

		    for (String n : array) {
		        enumComma.append(n.trim()+",");
		    }
		    enumComma.deleteCharAt(enumComma.length() - 1);

		    return enumComma.toString();
		}
		return "";
	}
	
	private String filtersQuery(ArrayList<Filter> filtersFromRequest){
		StringBuilder filtersQuery = new StringBuilder("1 = 1");
		for(Filter filter : filtersFromRequest){
			//TODO validar filtros usando available filterss
			filtersQuery.append(" AND "+filter.getValue());
			//TODO trocar para like se o Type for string, etc...
			filtersQuery.append(" = ");
			//
			filtersQuery.append("?");
		}
		return filtersQuery.toString();
	}
}
