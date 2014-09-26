package br.gov.planejamento.api.core.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DatabaseData implements Iterable<HashMap<String, String>> {
	
	private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

	@Override
	public Iterator<HashMap<String, String>> iterator() {
		return data.iterator();
	}
	
	public DatabaseData(ResultSet resultSet, ServiceConfiguration configs) throws SQLException {
		while(resultSet.next()) {
			HashMap<String, String> row = new HashMap<String, String>();
			for(String column : configs.getResponseFields()) {
				row.put(column, resultSet.getString(column));
			}
			data.add(row);
		}
	}
	
}
